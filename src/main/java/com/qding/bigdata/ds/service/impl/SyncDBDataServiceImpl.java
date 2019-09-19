package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.dao.SchemeDao;
import com.qding.bigdata.ds.enums.ResponseStatus;
import com.qding.bigdata.ds.enums.TaskType;
import com.qding.bigdata.ds.enums.UpdateDataStatus;
import com.qding.bigdata.ds.model.SchemeInfo;
import com.qding.bigdata.ds.model.TaskExecuteLog;
import com.qding.bigdata.ds.service.SyncDataService;
import com.qding.bigdata.ds.service.TaskExecuteLogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * Created by QDHL on 2017/9/11.
 */
@Service
public class SyncDBDataServiceImpl  implements SyncDataService {
    public static Logger logger = LoggerFactory.getLogger(SyncDBDataServiceImpl.class);
    @Autowired
    private SchemeDao schemeDao;
    @Autowired
    private TaskExecuteLogService taskExecuteLogService;
    private static String ERROR="ERROR";
    private static String INPUT_LOG="input_log";
    @Override
    public boolean executeTask(SchemeInfo info){
        boolean isSuccess=true;
        try {
            //启动任务
            List<String> scriptCommand = buidScriptCommand(info);
            ShellTread thread = new ShellTread(scriptCommand,info.getId());
            new Thread(thread).start();
            //更新任务状态
            schemeDao.updateStatus(info.getId(), UpdateDataStatus.running.getValue());
        }catch (Exception e){
            String error="开启同步数据到gp任务失败";
            info.setStatus(ResponseStatus.ERROR);
            info.setMessage(error);
            logger.error(error,e);
            isSuccess=false;
        }
        return isSuccess;
    }
    @Override
    public  SearchResult<TaskExecuteLog> queryList(TaskExecuteLog info){
        return  taskExecuteLogService.queryList(info);
    }
    private List<String>  buidScriptCommand(SchemeInfo info) {
        String ip=info.getIp().split(":")[0];
        String port=info.getIp().split(":")[1];
        String tablename=info.getTableName();
        String target_tablename="ods.ods_"+tablename;

        List<String> commands=new ArrayList<String>();
        commands.add(Constant.SHELL_COMMAND);
        commands.add(info.getDbName());
        commands.add(info.getUser());
        commands.add(info.getPassword());
        commands.add(ip);
        commands.add(port);
        commands.add(target_tablename);
        commands.add(tablename);
        return commands;
    }


    private class  ShellTread implements Runnable{
        private List<String> shellCommand;
        private String tableId;
        public ShellTread(List<String> shellCommand,String tableId){
            this.shellCommand=shellCommand;
            this.tableId=tableId;
        }
        public void run(){
            try {
                ProcessBuilder pb = new ProcessBuilder();
                pb.command(shellCommand);

                logger.info("======================="+shellCommand.get(0));
                int inddex=shellCommand.get(0).lastIndexOf("/");
                logger.info("========================shellCommand:"+inddex);
                String filePath=shellCommand.get(0).substring(0,inddex);
                pb.directory(new File(filePath));// 设置shell的当前目录。
                Date starDate=new Date();
                try {
                    logger.info("========================call shell start ");
                    Process proc = pb.start();
                    // 获取执行的log
                    Map<String,String> log=readExecuteLog(proc);
                    // 执行结果入库
                    saveExecuteResultToDB(starDate,log,tableId);
                    proc.destroy();
                    logger.info("========================execute shell script end");
                } catch (Exception e) {
                    String error = "========================call shell ERROR:" + e;
                    Map<String,String> map=new HashMap<String, String>();
                    map.put(INPUT_LOG,error);
                    saveExecuteResultToDB(starDate, map, tableId);
                    logger.error("========================call shell Exception:" ,e);
                }
            } catch (Throwable e) {
                logger.error("========================call shell failed. " ,e);
            }
        }

        private void saveExecuteResultToDB(Date starDate, Map<String, String> logMap, String tableId){
            String taskID=UUID.randomUUID().toString();
            String status=UpdateDataStatus.success.getValue();
            String log=logMap.get(INPUT_LOG);
            if(null!=log && log.contains(ERROR)){
                status=UpdateDataStatus.fail.getValue();
            }
            SchemeInfo info=buidSchmeInfo(taskID,status);
            schemeDao.updateById(info);
            TaskExecuteLog executeLog =new TaskExecuteLog(starDate,new Date(),taskID,tableId, TaskType.KETTLE.getValue(),log,status);
            taskExecuteLogService.save(executeLog);
        }

        private SchemeInfo buidSchmeInfo(String taskId, String status) {

            SchemeInfo info=new SchemeInfo();
            info.setId(tableId);
            info.setSyncDataStatus(status);
            info.setTaskId(taskId);
            return info;
        }

        private Map<String,String> readExecuteLog(Process proc) throws IOException {
//            BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String inLogStr = doReadLog(proc.getInputStream());
            // String errLogStr = doReadLog(proc.getErrorStream());
            Map map=new HashMap<String,String>();
            map.put(INPUT_LOG,inLogStr);
            return map;
        }

        private String doReadLog(InputStream stream ){
            String retStr = null;
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader in = new BufferedReader(inputStreamReader);
            try {
                String line;
                String lineSpilt=  System.getProperty("line.separator");
                StringBuffer logStr=new StringBuffer();
                while ((line = in.readLine()) != null) {
                    logStr.append(line).append(lineSpilt).append("<br/>"); // 打印输出结果
                }
                if(null != logStr){
                    retStr = logStr.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != in ) {
                        in.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (null != stream) {
                        stream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (null != inputStreamReader) {
                        inputStreamReader.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return retStr;
        }
    }
}


