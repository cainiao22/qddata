package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.service.GuancetaiEventService;
import com.qding.bigdata.ds.util.DateUtil;
import com.qding.bigdata.ds.util.ExportExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanpf
 * @date 2018/12/27 18:27
 * @description
 */

@RestController
//@Api(value = "guancetai-page-api", description = "观测台事件分析相关查询接口", hidden = true)
public class GuancetaiEventController extends GuancetaiController {

    @Autowired
    GuancetaiEventService guancetaiEventService;

    @PostMapping("getEventStartupTimesInfo")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "事件发生次数", notes = "getEventStartupTimesInfo", httpMethod = "POST")
    public GuancetaiQuotaInfo getEventStartupTimesInfo(@RequestBody @ApiParam GuancetaiQuotaParam param){
        return guancetaiEventService.getEventStartupTimesInfo(param);
    }


    @PostMapping("getEventStartupUsersInfo")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "事件发生人数", notes = "getEventStartupUsersInfo", httpMethod = "POST")
    public GuancetaiQuotaInfo getEventStartupUsersInfo(@RequestBody @ApiParam GuancetaiQuotaParam param){
        return guancetaiEventService.getEventStartupUsersInfo(param);
    }

    @PostMapping("getEventStartupTimesInfoTrend")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "事件发生次数趋势图", notes = "getEventStartupTimesInfoTrend", httpMethod = "POST")
    public List<GuancetaiQuotaTrendItem> getEventStartupTimesInfoTrend(@RequestBody @ApiParam GuancetaiEventQuotaParam param){
        return guancetaiEventService.getEventStartupTimesInfoTrend(param);
    }


    @PostMapping("getEventStartupUsersInfoTrend")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "事件发生人数趋势图", notes = "getEventStartupUsersInfoTrend", httpMethod = "POST")
    public  List<GuancetaiQuotaTrendItem> getEventStartupUsersInfoTrend(@RequestBody @ApiParam GuancetaiEventQuotaParam param){
        return guancetaiEventService.getEventStartupUsersInfoTrend(param);
    }

    @PostMapping("getGuancetaiEventDetailHuiZongList")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiEventDetail.class)
    @ApiOperation(value = "观测台事件汇总统计", notes = "getGuancetaiEventDetailHuiZongList", httpMethod = "POST")
    public Result<List<GuancetaiEventDetail>> getGuancetaiEventDetailHuiZongList(@RequestBody @ApiParam GuancetaiEventQuotaParam param) throws Exception {
        List<GuancetaiEventDetail> list = guancetaiEventService.getGuancetaiEventDetailHuiZongList(param);
        return Result.success(list);
    }

    @PostMapping("getGuancetaiEventDetailList")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiEventDetail.class)
    @ApiOperation(value = "观测台事件详情统计", notes = "getGuancetaiEventDetailList", httpMethod = "POST")
    public Result<List<GuancetaiEventDetail>> getGuancetaiEventDetailList(@RequestBody @ApiParam GuancetaiEventQuotaParam param) throws Exception {
        List<GuancetaiEventDetail> guancetaiEventDetailList = guancetaiEventService.getGuancetaiEventDetailList(param);
        return Result.success(guancetaiEventDetailList);
    }

    @PostMapping("exportGuancetaiEventDetailHuiZongList")
    @ApiResponse(code = 200, message = "successs")
    @ApiOperation(value = "观测台事件汇总统计导出", notes = "exportGuancetaiEventDetailHuiZongList", httpMethod = "POST")
    public void exportGuancetaiEventDetailHuiZongList(@ApiParam MostCommonDataExportParam param, HttpServletResponse response) throws Exception {
        List<GuancetaiEventDetail> detailHuiZongList = guancetaiEventService.getGuancetaiEventDetailHuiZongList(param.getObjParams(GuancetaiEventQuotaParam.class));
        ExportParam exportParam = new ExportParam();
        Map<String, ExportTitle> exportTitleMap = new HashMap<String, ExportTitle>(7);
        /*exportTitleMap.put("eventId", new ExportTitle("事件ID", 0));
        exportTitleMap.put("eventName", new ExportTitle("事件名称", 1));
        exportTitleMap.put("pageName", new ExportTitle("页面名称", 2));
        exportTitleMap.put("eventType", new ExportTitle("类型", 3));
        exportTitleMap.put("source", new ExportTitle("端(IOS/Android)", 4));
        exportTitleMap.put("visitCount", new ExportTitle("事件发生次数", 5));
        exportTitleMap.put("visitUsers", new ExportTitle("事件发生人数", 6));*/

        exportParam.setTitles(param.getTitles());

        Workbook workbook = ExportExcelUtil.getWorkbook(exportParam, "观测台事件汇总统计", detailHuiZongList);
        response.setContentType("application/xls;charset=utf-8");
        response.setHeader("Content-disposition",
                "attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".xls");
        workbook.write(response.getOutputStream());

    }

    @PostMapping("exportGuancetaiEventDetailList")
    @ApiResponse(code = 200, message = "successs")
    @ApiOperation(value = "观测台事件详情统计导出", notes = "exportGuancetaiEventDetailList", httpMethod = "POST")
    public void exportGuancetaiEventDetailList(@ApiParam MostCommonDataExportParam param, HttpServletResponse response) throws Exception {
        List<GuancetaiEventDetail> eventDetailList = guancetaiEventService.getGuancetaiEventDetailList(param.getObjParams(GuancetaiEventQuotaParam.class));
        ExportParam exportParam = new ExportParam();
        Map<String, ExportTitle> exportTitleMap = new HashMap<String, ExportTitle>(7);
       /* exportTitleMap.put("time", new ExportTitle("日期", 0));
        exportTitleMap.put("eventId", new ExportTitle("事件ID", 1));
        exportTitleMap.put("eventName", new ExportTitle("事件名称", 2));
        exportTitleMap.put("pageName", new ExportTitle("页面名称", 3));
        exportTitleMap.put("eventType", new ExportTitle("类型", 4));
        exportTitleMap.put("source", new ExportTitle("端(IOS/Android)", 5));
        exportTitleMap.put("visitCount", new ExportTitle("事件发生次数", 6));
        exportTitleMap.put("visitUsers", new ExportTitle("事件发生人数", 7));*/

        exportParam.setTitles(param.getTitles());

        Workbook workbook = ExportExcelUtil.getWorkbook(exportParam, "观测台事件汇总统计", eventDetailList);
        response.setContentType("application/xls;charset=utf-8");
        response.setHeader("Content-disposition",
                "attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".xls");
        workbook.write(response.getOutputStream());
    }

}
