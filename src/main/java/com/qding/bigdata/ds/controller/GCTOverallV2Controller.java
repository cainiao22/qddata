package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.model.GCTOverallV2Param;
import com.qding.bigdata.ds.service.GCTOverallV2Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "guancetai-overall-api", description = "观测台整体分析查询操作")
public class GCTOverallV2Controller {
    @Autowired
    GCTOverallV2Service gctOverallV2Service;

    @PostMapping("getOverallAnalysis")
    @ApiResponse(code = 200, message = "successs")
    @ApiOperation(value = "整体分析", notes = "getOverallAnalysis", httpMethod = "POST")
    public Map<String, List<Map<String, Object>>> getStartupTimesInfo(@RequestBody @ApiParam List<GCTOverallV2Param> paramList){
        return gctOverallV2Service.getOverallAnalysis(paramList);
    }
}
