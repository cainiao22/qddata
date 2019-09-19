package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.service.CollectEventInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class CollectEventInfoController extends BasicController{

    @Autowired
    private CollectEventInfoService collectEventInfoService;


    /**
     * 查询事件ID
     * @return
     * event_id
     */
    @RequestMapping(value = "getCollectEventInfo")
    @ResponseBody
    public Map<String,Object> eventIdInfo(){
        return collectEventInfoService.getCollectEventInfo();
    }

    /**
     * 条件查询sourceEvent信息
     * @return
     */
    @RequestMapping(value = "collectEventInfoBySource")
    @ResponseBody
    public Map<String,Object> collectEventInfoBySource(@RequestParam Map<String, String> params) throws Exception {
        return collectEventInfoService.collectEventInfoBySource(params);
    }

    /**
     * 条件查询EventInfo信息
     * @param params
     * @return
     */
    @RequestMapping(value = "collectEventInfoByEvent")
    @ResponseBody
    public Map<String,Object> collectEventInfoByEvent(@RequestParam Map<String,String> params) throws Exception {
       return collectEventInfoService.collectEventInfoByEvent(params);
    }

    /**
     * 跳转JSP页面
     * @return
     */
    @RequestMapping("collectEventInfo")
    public ModelAndView collectEventInfo(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.流量分析);
        return modelAndView;
    }

}
