package com.qding.bigdata.ds.controller;


import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.model.GCTBaseV2Param;
import com.qding.bigdata.ds.model.GCTParamValueV2Param;
import com.qding.bigdata.ds.service.GCTParamV2Service;
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
@Api(value = "guancetai-param-api", description = "观测台参数分析查询操作")
public class GCTParamV2Controller {

   @Autowired
   GCTParamV2Service gctParamV2Service;

   @PostMapping("getParamAnalysis")
   @ApiResponse(code = 200, message = "successs")
   @ApiOperation(value = "参数分析", notes = "getParamAnalysis", httpMethod = "POST")
   public Result getParamAnalysis(@RequestBody @ApiParam GCTParamValueV2Param param){
       List<Map<String, Object>> data = gctParamV2Service.getParamAnalysis(param);
       return Result.success(data);
   }

   @PostMapping("getParamValueAnalysis")
   @ApiResponse(code = 200, message = "successs")
   @ApiOperation(value = "参数值", notes = "getParamValueAnalysis", httpMethod = "POST")
   public Result getParamValueAnalysis(@RequestBody @ApiParam GCTParamValueV2Param param){
       List<Map<String, Object>> data = gctParamV2Service.getParamValueAnalysis(GCTBaseV2Param.getAvailableDayInMonth(param));
       return Result.success(data);
   }
}
