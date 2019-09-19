package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.enums.UserSource;
import com.qding.bigdata.ds.model.User;
import com.qding.bigdata.ds.ps.MisUserDao;
import com.qding.bigdata.ds.service.MisUserService;
import com.qding.bigdata.ds.service.UserService;
import io.swagger.models.auth.In;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MisUserServiceImpl implements MisUserService {
    @Autowired
    private MisUserDao misUserDao;
    @Autowired
    private UserService userService;

   /* public MisUserServiceImpl(MisUserDao misUserDao) {
        this.misUserDao = misUserDao;
    }*/

    protected Log log = LogFactory.getLog(this.getClass());

    @Override
    public List<Map<String, Object>> getMisUserList() {
        return misUserDao.listUser(new HashMap<String, Object>());
    }

    @Override
    public Map<String, Object> addDsUserByMis() {
        //定义返回结果
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("exits",0);
        result.put("add",0);
        List<String> addName = new LinkedList<String>();
        List<String> exitsName = new LinkedList<String>();
        List<Map<String, Object>>  userList = misUserDao.listUser(new HashMap<String, Object>());
        for (Map<String, Object> userMis:userList) {
            String userName =  userMis.get("HPS_OA_ACC").toString();
            User user = new User();
            user.setUserName(userName);
            //老用户
            User olduser = userService.getUserByUserName(userName);
            //如果用户不存在，则可以执行插入操作
            if(null==olduser){
                user.setRealName(userMis.get("NAME").toString());
                user.setMobile(userMis.get("PHONE2").toString());
                //设置数据来源为mis
                user.setUserSource(UserSource.MIS.getValue());
                if(null==userMis.get("HR_STATUS")){
                    user.setStatus(1);
                }else{
                    if("I".equals(userMis.get("HR_STATUS").toString())){
                        user.setStatus(1);
                    }else{
                        user.setStatus(0);
                    }
                }
                userService.save(user);
                result.put("add",Integer.parseInt(result.get("add").toString())+1);
                addName.add(userName);
            }else{
                //不更新用户密码
                olduser.setPassword(null);
                //如果用户状态变为已离职，还需将用户状态改为不可用状态
                if(null==userMis.get("HR_STATUS")){
                    olduser.setStatus(1);
                }else{
                    String actionType = userMis.get("HR_STATUS").toString();
                    if("I".equals(actionType)){
                        olduser.setStatus(1);
                    }else{
                        olduser.setStatus(0);
                    }
                }
                olduser.setMobile(userMis.get("PHONE2").toString());
                //更改老用户状态
                userService.update(olduser);
                result.put("exits",Integer.parseInt(result.get("exits").toString())+1);
                exitsName.add(userName);
            }
        }
        result.put("addName",addName);
        result.put("exitsName",exitsName);
        return result;
    }

    @Override
    public Map<String, Object> addOrUpdateDsUserByMisScheduled(String start,String end) {
        //定义返回结果
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("exits",0);
        result.put("add",0);
        result.put("leave",0);
        List<String> addName = new LinkedList<String>();
        List<String> exitsName = new LinkedList<String>();
        List<String> liveName = new LinkedList<String>();
        Map<String, Object> param = new HashMap<String, Object>();
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        if(null==start){
            param.put("startdate",new SimpleDateFormat("yyyy-MM-dd").format(new Date(today.getTime() - 86400000L)));
        }else{
            try{
                param.put("startdate",new SimpleDateFormat("yyyy-MM-dd").format(format1.parse(start)));
            }catch (ParseException e) {
                e.printStackTrace();
                param.put("startdate",new SimpleDateFormat("yyyy-MM-dd").format(new Date(today.getTime() - 86400000L)));
            }
        }
        if(null== end){
            param.put("enddate",new SimpleDateFormat("yyyy-MM-dd").format(today));
        }else{
            try{
                param.put("enddate",new SimpleDateFormat("yyyy-MM-dd").format(format1.parse(end)));
            }catch (ParseException e) {
                e.printStackTrace();
                param.put("enddate",new SimpleDateFormat("yyyy-MM-dd").format(today));
            }
        }
        //先取用户增量表用户修改信息
        List<Map<String, Object>>  userList = misUserDao.listUserByParam(param);
        for (Map<String, Object> userMis:userList) {
            String userName =  userMis.get("HPS_OA_ACC").toString();
            User user = new User();
            user.setUserName(userName);
            //老用户
            User olduser = userService.getUserByUserName(userName);
            //如果用户不存在，则可以执行插入操作
            if(null==olduser){
                    user.setRealName(userMis.get("NAME").toString());
                user.setMobile(userMis.get("PHONE2").toString());
                //设置数据来源为mis
                user.setUserSource(UserSource.MIS.getValue());
                if(null==userMis.get("HR_STATUS")){
                    user.setStatus(1);
                }else{
                    if("I".equals(userMis.get("HR_STATUS").toString())){
                        user.setStatus(1);
                    }else{
                        user.setStatus(0);
                    }
                }
                userService.save(user);
                result.put("add",Integer.parseInt(result.get("add").toString())+1);
                addName.add(userName);
            }else{
                //不更新用户密码
                olduser.setPassword(null);
                //如果用户状态变为已离职，还需将用户状态改为不可用状态
                if(null==userMis.get("HR_STATUS")){
                    olduser.setStatus(1);
                }else{
                    String actionType = userMis.get("HR_STATUS").toString();
                    if("I".equals(actionType)){
                        olduser.setStatus(1);
                    }else{
                        olduser.setStatus(0);
                    }
                }
                olduser.setMobile(userMis.get("PHONE2").toString());
                //更改老用户状态
                userService.update(olduser);
                Integer exit = -1;
                if(null!=result.get("exits")){
                    exit = Integer.valueOf(result.get("exits").toString());
                }
                result.put("exits",exit+1);
                exitsName.add(userName);
            }
        }
        //取job表离职信息更改用户状态为离职状态
        List<Map<String, Object>>  liveuserList = misUserDao.selectMisUserLeaveByJob(param);
        User userlive = null;
        for (Map<String, Object>liveuser:liveuserList) {
            //这些用户肯定存在，直接依据用户名查找，查询到的，状态不为1的，将其改为1，其它不做处理
            String liveuserName =  liveuser.get("HPS_OA_ACC").toString();
            userlive = userService.getUserByUserName(liveuserName);
            if(null==userlive){
                log.error("同步mis离职账户信息至北斗星，依据用户账号在北斗星查询 不到信息，mis账户信息："+liveuser);
            }else{
                //如果已经是禁用状态，则不做修改
                if(1==userlive.getStatus()){
                    continue;
                }
                userlive.setStatus(1);
                //不更新用户密码
                userlive.setPassword(null);
                userService.update(userlive);
                result.put("leave",Integer.parseInt(result.get("leave").toString())+1);
                liveName.add(liveuserName);
            }
        }
        result.put("leave",Integer.parseInt(result.get("leave").toString())+",昨日离职总人数:"+ liveuserList.size());
        result.put("addName",addName);
        result.put("exitsName",exitsName);
        result.put("liveName",liveName);
        return result;
    }
}
