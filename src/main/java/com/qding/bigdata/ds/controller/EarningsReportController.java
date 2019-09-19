package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.EarningsReport;
import com.qding.bigdata.ds.service.EarningsReportService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class EarningsReportController extends BasicController{

    Logger logger = Logger.getLogger(getClass());

    @Autowired
    private EarningsReportService earningsReportService;


    /**
     * 跳转JSP页面
     * @return
     */
    @RequestMapping("uploadUtil")
    public ModelAndView collectEventInfo(){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.月报图片上传);
        return modelAndView;
    }

    /**
     * 新增信息
     * @return
     */
    @RequestMapping(value = "insertEarningsReportInfo")
    @ResponseBody
    public Map<String, Object> insertEarningsReportInfo(EarningsReport earningsReport,HttpServletRequest request){
        return earningsReportService.insertEarningsReportInfo(earningsReport,request);
    }

    /**
     * 图片上传
     * @return
     */
    @RequestMapping(value = "upImgInfo")
    @ResponseBody
    public Map<String, Object> upImgInfo(HttpServletRequest request, MultipartFile file,Integer width, Integer height, Double imgSize) throws IllegalStateException, IOException {
        return earningsReportService.upImgInfo(request, file, width, height, imgSize);
    }



    /**
     * 查询
     * @return
     */
    @RequestMapping(value = "queryImgInfo")
    @ResponseBody
    public Map<String,Object> queryImgInfo(@RequestParam Map<String,String> params){
        logger.info("查询列表,入参[{}]" + params);
        return earningsReportService.queryImgInfo(params);
    }

    /**
     * 删除单个数据
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteImgInfoById")
    @ResponseBody
    public Map<String,Object> deleteImgInfoById(String id){
        logger.info("删除单个数据,入参[{}]" + id);
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            earningsReportService.deleteImgInfoById(id);
            map.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","系统异常!请稍后再试!");
            map.put("errMsg",e.getMessage());
            logger.error("任务执行失败[{}]" + e.getMessage());
        }
        return map;
    }

    /**
     * 查询月报图片信息
     * @return
     */
    @RequestMapping(value = "queryMonthlyImgInfo")
    @ResponseBody
    public Map<String, Object> queryMonthlyImgInfo(String date,Integer type){

        return earningsReportService.queryMonthlyImgInfo(date, type);
    }

    /**
     * 查询回显信息
     * @param id
     * @return
     */
    @RequestMapping(value = "queryThisRowInfoById")
    @ResponseBody
    public Map<String, Object> queryThisRowInfoById(String id){
        return earningsReportService.queryThisRowInfoById(id);
    }

}
