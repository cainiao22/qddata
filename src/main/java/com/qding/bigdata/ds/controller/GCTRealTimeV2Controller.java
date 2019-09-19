package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.model.GCTOverallV2Param;
import com.qding.bigdata.ds.model.GuancetaiQuotaInfo;
import com.qding.bigdata.ds.service.GCTRealTimeV2Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/13 下午8:14
 */
@RestController
@Api(value = "guancetai-realtime-api", description = "观测台实时分析查询操作")
public class GCTRealTimeV2Controller {

   @Autowired
    GCTRealTimeV2Service gctRealTimeV2Service;

    @GetMapping("getRealTimeAnalysis")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "实时分析", notes = "getRealTimeAnalysis", httpMethod = "GET")
    public Map<String, GuancetaiQuotaInfo> getStartupTimesInfo(GCTOverallV2Param param){
        return gctRealTimeV2Service.getRealTimeAnalysis(param);
    }

    @GetMapping("getRealTimeTableAnalysis")
    @ApiResponse(code = 200, message = "successs", response = Map.class)
    @ApiOperation(value = "实时分析趋势图指标", notes = "getRealTimeTableAnalysis", httpMethod = "GET")
    public Map<String, List<Map<String, Object>>> getRealTimeTableAnalysis(GCTOverallV2Param param) {
        return gctRealTimeV2Service.getRealTimeTableAnalysis(param);
    }


    @GetMapping("getRealTimeTopAnalysis")
    @ApiResponse(code = 200, message = "successs", response = Map.class)
    @ApiOperation(value = "实时分析top指标", notes = "getRealTimeTop5Analysis", httpMethod = "GET")
    public Map<String, List<Map<String, Object>>> getRealTimeTopAnalysis(GCTOverallV2Param param){
        return gctRealTimeV2Service.getRealTimeTopAnalysis(param);
    }

    @PostMapping("getRealTimeAnalysisByQyrCompany")
    @ApiResponse(code = 200, message = "successs", response = Map.class)
    @ApiOperation(value = "实时分析按分公司", notes = "getRealTimeAnalysisByQyrCompany", httpMethod = "POST")
    public Map<String, GuancetaiQuotaInfo> getRealTimeAnalysisByQyrCompany(@RequestBody GCTOverallV2Param param){
        return gctRealTimeV2Service.getRealTimeAnalysisByQyrCompany(param);
    }

    @PostMapping("getRealTimeAnalysisByCompany")
    @ApiResponse(code = 200, message = "successs", response = Map.class)
    @ApiOperation(value = "实时分析按公司", notes = "getRealTimeAnalysisByCompany", httpMethod = "POST")
    public Map<String, GuancetaiQuotaInfo> getRealTimeAnalysisByCompany(@RequestBody GCTOverallV2Param param){
        return gctRealTimeV2Service.getRealTimeAnalysisByCompany(param);
    }


}
