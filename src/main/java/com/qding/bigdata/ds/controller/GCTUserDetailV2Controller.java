package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.model.UserDetailParam;
import com.qding.bigdata.ds.model.UsertrilParam;
import com.qding.bigdata.ds.service.GCTUserDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-9-2 下午7:30
 * @Description
 **/

@RestController
@Api(value = "guancetai-userdetail-api", description = "观测台用户详情查询操作")
public class GCTUserDetailV2Controller {

    @Autowired
    GCTUserDetailService userDetailService;

    @ApiResponse(code = 200, message = "successs", response = Result.class)
    @ApiOperation(value = "用户详情接口查询", notes = "queryUserDetail")
    @PostMapping("queryUserDetail")
    public Result queryUserDetail(@RequestBody UserDetailParam param){
        List<Map<String, Object>> data = userDetailService.queryUserDetails(param);
        if(data != null){
            data.forEach(item -> {
                item.keySet().forEach(key -> {
                    if(item.get(key) instanceof Timestamp){
                        item.put(key, DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA).format(((Timestamp) item.get(key))));
                    }
                });
            });
        }
        return Result.success(data);
    }

    @ApiResponse(code = 200, message = "successs", response = Result.class)
    @ApiOperation(value = "用户轨迹接口查询", notes = "queryUserTrail")
    @PostMapping("queryUserTrail")
    public Result queryUserTrail(@RequestBody UsertrilParam param){
        Map<String, List<Map<String, Object>>> map =
                userDetailService.queryUserTrail(param.getUserId(), param.getQueryDate());
        return Result.success(map);
    }
}
