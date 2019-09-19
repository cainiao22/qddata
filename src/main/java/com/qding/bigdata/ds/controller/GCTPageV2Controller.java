package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.model.GCTPageV2Param;
import com.qding.bigdata.ds.service.GCTPageV2Service;
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
@Api(value = "guancetai-page-api", description = "观测台页面分析查询操作")
public class GCTPageV2Controller {
    @Autowired
    GCTPageV2Service gctPageV2Service;

    @PostMapping("getPageAnalysisV2")
    @ApiResponse(code = 200, message = "successs")
    @ApiOperation(value = "页面分析", notes = "getPageAnalysisV2", httpMethod = "POST")
    public Map<String, List<Map<String, Object>>> getPageAnalysisV2(@RequestBody @ApiParam List<GCTPageV2Param> paramList){
        return gctPageV2Service.getPageAnalysis(paramList);
    }
}
