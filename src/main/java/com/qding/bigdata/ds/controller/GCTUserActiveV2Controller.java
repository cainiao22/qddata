package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.model.GCTOverallV2Param;
import com.qding.bigdata.ds.service.GCTUserActiveV2Service;
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
 * @Date 19-8-22 上午9:28
 * @Description TODO
 **/

@RestController
@Api(value = "guancetai-realtime-api", description = "观测台实时分析查询操作")
public class GCTUserActiveV2Controller extends BasicController {

    @Autowired
    GCTUserActiveV2Service userActiveV2Service;

    @PostMapping("getTableUserActiveV2Analysis")
    @ApiResponse(code = 200, message = "successs", response = Map.class)
    @ApiOperation(value = "用户活跃分析", notes = "getTableUserActiveV2Analysis", httpMethod = "POST")
    public Map<String, List<Map<String, Object>>> getTableUserActiveV2Analysis(@RequestBody List<GCTOverallV2Param> params){
        return this.userActiveV2Service.getTableAnalysis(params);
    }
}
