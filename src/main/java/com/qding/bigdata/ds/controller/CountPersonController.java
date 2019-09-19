package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.common.ResultJson;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.enums.SourceEnum;
import com.qding.bigdata.ds.model.CommonDataExportParam;
import com.qding.bigdata.ds.model.CountData;
import com.qding.bigdata.ds.model.SystemWebAccess;
import com.qding.bigdata.ds.service.SystemWebAccessService;
import com.qding.bigdata.ds.util.DateUtil;
import com.qding.bigdata.ds.util.DownLoadPOIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by lzs on 2018/7/5.
 */
@Controller
public class CountPersonController extends BasicController {

    @Autowired
    SystemWebAccessService systemWebAccessService;

    @RequestMapping("logonPersonStatistics")
    public ModelAndView logonPersonStatistics(){
        ModelAndView modelAndView =initModelAndView();

        Map<String,String>  sources=new HashMap<String, String>();
        SourceEnum[] values = SourceEnum.values();
        for (SourceEnum em:values) {
            sources.put(em.getSource(),em.getSourceCN());
        }
        modelAndView.addObject("sources",sources);

        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.人次统计);
        return modelAndView;
    }
    @RequestMapping("logonPersonStatistics_old")
    public ModelAndView logonPersonStatistics_old(){
        ModelAndView modelAndView =initModelAndView();

        Map<String,String>  sources=new HashMap<String, String>();
        SourceEnum[] values = SourceEnum.values();
        for (SourceEnum em:values) {
            sources.put(em.getSource(),em.getSourceCN());
        }
        modelAndView.addObject("sources",sources);

        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.人次统计旧版);
        return modelAndView;
    }
    //查询
    @RequestMapping("/queryPersonCount")
    @ResponseBody
    public Object queryPersonCount(CountData data) {
        List<SystemWebAccess> list = new ArrayList<SystemWebAccess>();
        if (data != null) {
            list = systemWebAccessService.listBySource(data);
            sortListByDayNumber(list);
        }
        return getResultJson(list);
    }


    //查询所有
    @RequestMapping("/queryPersonCountAll")
    @ResponseBody
    public Object queryPersonCountAll(CountData data) {

        List<SystemWebAccess> list = new ArrayList<SystemWebAccess>();
        if (data != null) {
            list = systemWebAccessService.listAll(data);
            sortListByDate(list);
        }
        return getResultJson(list);
    }

    private Object getResultJson(List<SystemWebAccess> list) {
        ResultJson resultJson = new ResultJson();
        try {
            if (list == null || list.size() == 0) {
                resultJson.setCode(1);
                resultJson.setMsg("查询失败");
                resultJson.setList(null);
            } else {
                resultJson.setCode(0);
                resultJson.setMsg("查询成功");
                resultJson.setList(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(1);
            resultJson.setMsg("查询失败");
            resultJson.setList(null);
        }
        return resultJson;
    }

    private void sortListByDate(List<SystemWebAccess> list) {
        Collections.sort(list, new Comparator<SystemWebAccess>() {
            @Override
            public int compare(SystemWebAccess o1, SystemWebAccess o2) {
                int i = Integer.parseInt(o2.getDate()) - Integer.parseInt(o1.getDate());
                return i;
            }
        });
    }


    private void sortListByPv(List<SystemWebAccess> list) {
        Collections.sort(list, new Comparator<SystemWebAccess>() {
            @Override
            public int compare(SystemWebAccess o1, SystemWebAccess o2) {
                int i = o2.getPv() - o1.getPv();
                return i;
            }
        });
    }

    private void sortListByDayNumber(List<SystemWebAccess> list) {
        Collections.sort(list, new Comparator<SystemWebAccess>() {
            @Override
            public int compare(SystemWebAccess o1, SystemWebAccess o2) {
                int i = o2.getDayNumber() - o1.getDayNumber();
                return i;
            }
        });
    }


    @RequestMapping("/exportPersonCount")
    public void exportPersonCount(CommonDataExportParam exportParam, HttpServletResponse response) {
        Map<String, String> params = exportParam.getParams();
        CountData data = new CountData();
        data.setStartTime(params.get("startTime"));
        data.setEndTime(params.get("endTime"));
        data.setSource(params.get("source"));
        List<SystemWebAccess> list = systemWebAccessService.listBySource(data);
        sortListByDayNumber(list);
        export(exportParam, response, list);

    }


    @RequestMapping("/exportAllPersonCount")
    public void exportAllPersonCount(CommonDataExportParam exportParam, HttpServletResponse response) {
        Map<String, String> params = exportParam.getParams();
        CountData data = new CountData();
        data.setStartTime(params.get("startTime"));
        data.setEndTime(params.get("endTime"));
        data.setSource(params.get("source"));
        List<SystemWebAccess> list = systemWebAccessService.listAll(data);
        sortListByDate(list);
        export(exportParam, response, list);

        /*List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();

        for (SystemWebAccess sys: list) {
            Map<String, Object> map= new HashMap<String, Object>();
            map.put("date",sys.getDate());
            map.put("userName",sys.getUserName());
            map.put("pv",sys.getPv());
            if(StringUtils.equals(sys.getSource(), SourceEnum.JSC.getSource())){
                map.put("source",SourceEnum.JSC.getSourceCN());
            }
            if(StringUtils.equals(sys.getSource(), SourceEnum.LP.getSource())){
                map.put("source",SourceEnum.LP.getSourceCN());
            }

            list1.add(map);
        }
        response.setContentType("application/csv;charset=utf-8");
        response.setHeader("Content-disposition",
                "attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".xls");

        try {
            DownLoadPOIUtil.createFixationSheet(response.getOutputStream(), exportParam.getTitles(), list1);
        }catch (Exception e){
            e.printStackTrace();
        }*/


    }


    private void export(CommonDataExportParam exportParam, HttpServletResponse response, List<SystemWebAccess> list) {

        //List<SystemWebAccess> list = systemWebAccessService.listBySource(data);
        //sortList(list);
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();

        for (SystemWebAccess sys : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("date", sys.getDate());
            map.put("userName", sys.getUserName());
            map.put("pv", sys.getPv());
            //String source = SourceEnum.getSource(sys.getSource());
            map.put("source", sys.getSource());
            map.put("dayNumber", sys.getDayNumber());
          /*  if (StringUtils.equals(sys.getSource(), SourceEnum.JSC.getSource())) {
                map.put("source", SourceEnum.JSC.getSourceCN());
            }*/
           /* if (StringUtils.equals(sys.getSource(), SourceEnum.LP.getSource())) {
                map.put("source", SourceEnum.LP.getSourceCN());
            }*/

            list1.add(map);
        }

        response.setContentType("application/csv;charset=utf-8");
        response.setHeader("Content-disposition",
                "attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".xls");

        try {
            DownLoadPOIUtil.createFixationSheet(response.getOutputStream(), exportParam.getTitles(), list1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
