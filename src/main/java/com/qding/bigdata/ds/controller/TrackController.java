package com.qding.bigdata.ds.controller;

import com.alibaba.fastjson.JSON;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.model.ExportParam;
import com.qding.bigdata.ds.model.TrackEventMap;
import com.qding.bigdata.ds.model.TrackMsg;
import com.qding.bigdata.ds.model.TrackParam;
import com.qding.bigdata.ds.service.TrackEventMapService;
import com.qding.bigdata.ds.service.TrackService;
import com.qding.bigdata.ds.util.DateUtil;
import com.qding.bigdata.ds.util.ExportExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.*;

/**
 * @author yanpf
 * @date 2018/7/13 14:45
 * @description
 */

@Controller
public class TrackController {

    @Autowired
    TrackService trackService;
    @Autowired
    private TrackEventMapService trackEventMapService;

    @ResponseBody
    @RequestMapping("queryTrackDetails")
    public Result<List<TrackMsg>> queryTrackDetails(TrackParam param){
        try {
            List<TrackMsg> trackMsgList = trackService.queryTrackDetails(param);
            for (TrackMsg trackMsg : trackMsgList) {
                if(trackMsg.getOnlineTime() != null) {
                    Long onlineTime = Long.valueOf(trackMsg.getOnlineTime());
                    onlineTime = onlineTime / 1000;

                    long hour = onlineTime / (60 * 60);
                    String onLineTimeStr = "";
                    if (hour != 0){
                        onLineTimeStr += hour + "小时";
                        onlineTime %= (60 * 60);
                    }
                    if(onlineTime / 60 != 0) {
                        onLineTimeStr += onlineTime / 60 + "分钟";
                    }
                    if(onlineTime % 60 != 0){
                        onLineTimeStr += onlineTime % 60 + "秒";
                    }
                    if(StringUtils.isEmpty(onLineTimeStr)){
                        onLineTimeStr = onlineTime + "秒";
                    }
                    trackMsg.setOnlineTime(onLineTimeStr);
                }
            }
            return Result.success(trackMsgList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return Result.failed(1, "系统错误");
        }

    }

    @ResponseBody
    @RequestMapping("queryTrackSummary")
    public Result<List<TrackMsg>> queryTrackSummary(TrackParam param){
        try {
            List<TrackMsg> trackMsgList = trackService.queryTrackSummary(param);
            return Result.success(trackMsgList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return Result.failed(1, "系统错误");
        }

    }

    @ResponseBody
    @RequestMapping("exportTrackSummary")
    public void exportTrackSummary(ExportParam param, HttpServletResponse response) throws IOException {
        try {
            List<TrackMsg> trackMsgList = trackService.queryTrackSummary(param.getParams());

            Workbook workbook = ExportExcelUtil.getWorkbook(param, "产品访问汇总", trackMsgList);
            response.setContentType("application/xls;charset=utf-8");
            response.setHeader("Content-disposition",
                    "attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".xls");

            workbook.write(response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(Result.failed(1, "系统异常")));
        }

    }

    @ResponseBody
    @RequestMapping("exportTrackDetails")
    public void exportTrackDetails(ExportParam param, HttpServletResponse response) throws IOException {
        try {
            List<TrackMsg> trackMsgList = trackService.queryTrackDetails(param.getParams());

            Workbook workbook = ExportExcelUtil.getWorkbook(param, "用户使用统计明细", trackMsgList);
            response.setContentType("application/xls;charset=utf-8");
            response.setHeader("Content-disposition",
                    "attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".xls");
            workbook.write(response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(Result.failed(1, "系统异常")));
        }

    }

    /**
     * 查询产品名称的列表
     * @return
     */
    @ResponseBody
    @RequestMapping("getByEventType")
    public List<TrackEventMap> getByEventType(){

            List<TrackEventMap> event = trackEventMapService.getTrackEventMapListByType("event");
            return event;
    }
}
