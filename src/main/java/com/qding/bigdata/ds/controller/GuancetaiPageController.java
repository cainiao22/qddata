package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.service.GuancetaiPageService;
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
import java.util.*;

/**
 * @author yanpf
 * @date 2018/12/27 15:06
 * @description
 */

@RestController
//@Api(value = "guancetai-page-api", description = "观测台页面分析相关查询接口", hidden = true)
public class GuancetaiPageController extends GuancetaiController {

    @Autowired
    GuancetaiPageService guancetaiPageService;


    @PostMapping("getPageStartupTimesInfo")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "启动次数", notes = "getPageStartupTimesInfo", httpMethod = "POST")
    public GuancetaiQuotaInfo getPageStartupTimesInfo(@RequestBody @ApiParam GuancetaiQuotaParam param){
        return guancetaiPageService.getPageStartupTimesInfo(param);
    }

    @PostMapping("getPageStartupUsersInfo")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "访问用户数", notes = "getPageStartupUsersInfo", httpMethod = "POST")
    public GuancetaiQuotaInfo getPageStartupUsersInfo(@RequestBody @ApiParam GuancetaiQuotaParam param){
        return guancetaiPageService.getPageStartupUsersInfo(param);
    }

    @PostMapping("getPageAvgOnlineTimeInfo")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "次均停留时长(s)", notes = "getPageAvgOnlineTimeInfo", httpMethod = "POST")
    public GuancetaiQuotaInfo getPageAvgOnlineTimeInfo(@RequestBody @ApiParam GuancetaiQuotaParam param){
        GuancetaiQuotaInfo timesInfo = guancetaiPageService.getPageStartupTimesInfo(param);
        GuancetaiQuotaInfo onlineTimeInfo = guancetaiPageService.getBusinessOnlineTime(param);
        GuancetaiQuotaInfo result = new GuancetaiQuotaInfo();
        result.setCurrent(Math.round(onlineTimeInfo.getCurrent() / timesInfo.getCurrent() * 10) / 10000.0);
        result.setLast(Math.round(onlineTimeInfo.getLast() / timesInfo.getLast() * 10) / 10000.0);
        result.setIncreasement();

        return result;
    }

    @PostMapping("getPageAvgUserOnlineTimeInfo")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "人均停留时长(s)", notes = "getPageAvgUserOnlineTimeInfo", httpMethod = "POST")
    public GuancetaiQuotaInfo getPageAvgUserOnlineTimeInfo(@RequestBody @ApiParam GuancetaiQuotaParam param){
        GuancetaiQuotaInfo timesInfo = guancetaiPageService.getPageStartupUsersInfo(param);
        GuancetaiQuotaInfo onlineTimeInfo = guancetaiPageService.getBusinessOnlineTime(param);
        GuancetaiQuotaInfo result = new GuancetaiQuotaInfo();
        result.setCurrent(Math.round(onlineTimeInfo.getCurrent() / timesInfo.getCurrent() * 10) / 10000.0);
        result.setLast(Math.round(onlineTimeInfo.getLast() / timesInfo.getLast() * 10) / 10000.0);
        result.setIncreasement();

        return result;
    }

    @PostMapping("getPageStartupUsersInfoTrend")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "访问用户数趋势图", notes = "getPageStartupUsersInfoTrend", httpMethod = "POST")
    public List<GuancetaiQuotaTrendItem> getPageStartupUsersInfoTrend(@RequestBody @ApiParam GuancetaiPageQuotaParam param){
        return guancetaiPageService.getPageStartupUsersInfoTrend(param);
    }

    @PostMapping("getPageStartupTimesInfoTrend")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "访问次数趋势图", notes = "getPageStartupTimesInfoTrend", httpMethod = "POST")
    public List<GuancetaiQuotaTrendItem> getPageStartupTimesInfoTrend(@RequestBody @ApiParam GuancetaiPageQuotaParam param){
        return guancetaiPageService.getPageStartupTimesInfoTrend(param);
    }

    @PostMapping("getPageAvgUserOnlineTimeInfoTrend")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "人均停留时长趋势图(s)", notes = "getPageAvgUserOnlineTimeInfoTrend", httpMethod = "POST")
    public List<GuancetaiQuotaTrendItem> getPageAvgUserOnlineTimeInfoTrend(@RequestBody @ApiParam GuancetaiPageQuotaParam param){
        List<GuancetaiQuotaTrendItem> onlineTimeTrend = guancetaiPageService.getBusinessOnlineTimeTrend(param);
        List<GuancetaiQuotaTrendItem> usersInfoTrend = guancetaiPageService.getPageStartupUsersInfoTrend(param);
        List<GuancetaiQuotaTrendItem> result = new ArrayList<GuancetaiQuotaTrendItem>();
        mergeResult(onlineTimeTrend, usersInfoTrend, result);
        for (GuancetaiQuotaTrendItem item : result) {
            item.setValue(Math.round(item.getValue() * 10) / 10000.0);
        }
        return result;
    }

    @PostMapping("getPageSetupTimeOnlineInfoTrend")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "次停留时长趋势图(s)", notes = "getPageSetupTimeOnlineInfoTrend", httpMethod = "POST")
    public List<GuancetaiQuotaTrendItem> getPageSetupTimeOnlineInfoTrend(@RequestBody @ApiParam GuancetaiPageQuotaParam param){
        List<GuancetaiQuotaTrendItem> onlineTimeTrend = guancetaiPageService.getBusinessOnlineTimeTrend(param);
        List<GuancetaiQuotaTrendItem> timesInfoTrend = guancetaiPageService.getPageStartupTimesInfoTrend(param);
        List<GuancetaiQuotaTrendItem> result = new ArrayList<GuancetaiQuotaTrendItem>();
        mergeResult(onlineTimeTrend, timesInfoTrend, result);
        for (GuancetaiQuotaTrendItem item : result) {
            item.setValue(Math.round(item.getValue() * 10) / 10000.0);
        }
        return result;
    }

    @PostMapping("getGuancetaiPageDetailHuiZongList")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiPageDetail.class)
    @ApiOperation(value = "观测台页面汇总统计", notes = "getGuancetaiPageDetailHuiZongList", httpMethod = "POST")
    public Result<List<GuancetaiPageDetail>> getGuancetaiPageDetailHuiZongList(@RequestBody @ApiParam GuancetaiPageQuotaParam param) throws Exception {
        List<GuancetaiPageDetail> list = guancetaiPageService.getGuancetaiPageDetailHuiZongList(param);
        return Result.success(list);
    }

    @PostMapping("getGuancetaiPageDetailList")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiPageDetail.class)
    @ApiOperation(value = "观测台页面详情统计", notes = "getGuancetaiPageDetailList", httpMethod = "POST")
    public Result<List<GuancetaiPageDetail>> getGuancetaiPageDetailList(@RequestBody @ApiParam GuancetaiPageQuotaParam param) throws Exception {
        List<GuancetaiPageDetail> list = guancetaiPageService.getGuancetaiPageDetailList(param);
        return Result.success(list);
    }

    @PostMapping("exportGuancetaiPageDetailHuiZongList")
    @ApiResponse(code = 200, message = "successs")
    @ApiOperation(value = "观测台页面汇总统计导出", notes = "exportGuancetaiPageDetailHuiZongList", httpMethod = "POST")
    public void exportGuancetaiPageDetailHuiZongList(MostCommonDataExportParam param, HttpServletResponse response) throws Exception {
        List<GuancetaiPageDetail> detailHuiZongList = guancetaiPageService.getGuancetaiPageDetailHuiZongList(param.getObjParams(GuancetaiPageQuotaParam.class));
        ExportParam exportParam = new ExportParam();
        /*Map<String, ExportTitle> exportTitleMap = new HashMap<String, ExportTitle>(6);
        exportTitleMap.put("pageId", new ExportTitle("页面ID", 1));
        exportTitleMap.put("pageName", new ExportTitle("页面名称", 2));
        exportTitleMap.put("source", new ExportTitle("端(IOS/Android)", 3));
        exportTitleMap.put("visitCount", new ExportTitle("访问次数", 4));
        exportTitleMap.put("visitUsers", new ExportTitle("访问用户数", 5));
        exportTitleMap.put("onlineTimeEveryTime",new ExportTitle( "次均停留时长(s)", 6));*/

        exportParam.setTitles(param.getTitles());

        Workbook workbook = ExportExcelUtil.getWorkbook(exportParam, "观测台页面汇总统计", detailHuiZongList);
        response.setContentType("application/xls;charset=utf-8");
        response.setHeader("Content-disposition",
                "attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".xls");
        workbook.write(response.getOutputStream());
    }

    @PostMapping("exportGuancetaiPageDetailList")
    @ApiResponse(code = 200, message = "successs")
    @ApiOperation(value = "观测台页面详情统计导出", notes = "exportGuancetaiPageDetailList", httpMethod = "POST")
    public void exportGuancetaiPageDetailList(@ApiParam MostCommonDataExportParam param, HttpServletResponse response) throws Exception {
        List<GuancetaiPageDetail> pageDetails = guancetaiPageService.getGuancetaiPageDetailList(param.getObjParams(GuancetaiPageQuotaParam.class));
        ExportParam exportParam = new ExportParam();
        Map<String, ExportTitle> exportTitleMap = new HashMap<String, ExportTitle>(7);
        /*exportTitleMap.put("time", new ExportTitle("日期", 0));
        exportTitleMap.put("pageId", new ExportTitle("页面ID", 1));
        exportTitleMap.put("pageName", new ExportTitle("页面名称", 2));
        exportTitleMap.put("source", new ExportTitle("端(IOS/Android)", 3));
        exportTitleMap.put("visitCount", new ExportTitle("访问次数", 4));
        exportTitleMap.put("visitUsers", new ExportTitle("访问用户数", 5));
        exportTitleMap.put("onlineTimeEveryTime",new ExportTitle( "次均停留时长(s)", 6));*/

        exportParam.setTitles(param.getTitles());

        Workbook workbook = ExportExcelUtil.getWorkbook(exportParam, "观测台页面详情统计", pageDetails);
        response.setContentType("application/xls;charset=utf-8");
        response.setHeader("Content-disposition",
                "attachment;filename=" + DateUtil.formatDateToFullString(new Date()) + ".xls");
        workbook.write(response.getOutputStream());
    }


}
