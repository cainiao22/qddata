package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.model.GCTEventV2Param;
import com.qding.bigdata.ds.service.GCTEventAnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-8-17 上午11:06
 * @Description
 **/

@RestController
@Api(value = "guancetai-event-v2-analysis-api", description = "观测台事件分析查询操作")
public class GCTEventAnalysisV2Controller {

    @Autowired
    GCTEventAnalysisService gctEventAnalysisService;

    @PostMapping("getTableEventAnalysis")
    @ApiResponse(code = 200, message = "successs", response = Map.class)
    @ApiOperation(value = "事件分析趋势图", notes = "getTableEventAnalysis", httpMethod = "POST")
    public Map<String, List<Map<String, Object>>> getTableAnalysis(@RequestBody List<GCTEventV2Param> params){
        return this.gctEventAnalysisService.getTableAnalysis(params);
    }


}
