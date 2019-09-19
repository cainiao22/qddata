package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.model.GuancetaiPageQuotaParam;
import com.qding.bigdata.ds.model.GuancetaiQuotaInfo;
import com.qding.bigdata.ds.model.GuancetaiQuotaParam;
import com.qding.bigdata.ds.model.GuancetaiQuotaTrendItem;
import com.qding.bigdata.ds.service.GuancetaiBaseService;
import com.qding.bigdata.ds.service.GuancetaiPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanpf
 * @date 2018/12/26 11:32
 * @description
 */

@RestController
//@Api(value = "guancetai-base-api", description = "关于观测台基本统计的查询操作", hidden = true)
public class GuancetaiBaseController extends GuancetaiController {

    @Autowired
    GuancetaiBaseService guancetaiBaseService;

   /* @Autowired
    GuancetaiPageService guancetaiPageService;*/

    @PostMapping("getStartupTimesInfo")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "启动次数", notes = "getStartupTimesInfo", httpMethod = "POST")
    public GuancetaiQuotaInfo getStartupTimesInfo(@RequestBody @ApiParam GuancetaiQuotaParam param){
        return guancetaiBaseService.getStartupTimesInfo(param);
    }

    @PostMapping("getStartupUsersInfo")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "启动用户数", notes = "getStartupUsersInfo", httpMethod = "POST")
    public GuancetaiQuotaInfo getStartupUsersInfo(@RequestBody @ApiParam GuancetaiQuotaParam param){
        return guancetaiBaseService.getStartupUsersInfo(param);
    }

    @PostMapping("getAvgOnlineTimeInfo")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "次均停留时长(s)", notes = "getAvgOnlineTimeInfo", httpMethod = "POST")
    public GuancetaiQuotaInfo getAvgOnlineTimeInfo(@RequestBody @ApiParam GuancetaiQuotaParam param){
        GuancetaiQuotaInfo timesInfo = guancetaiBaseService.getStartupTimesInfo(param);
        GuancetaiQuotaInfo onlineTimeInfo = guancetaiBaseService.getBusinessOnlineTime(param);
        GuancetaiQuotaInfo result = new GuancetaiQuotaInfo();
        result.setCurrent(Math.round(onlineTimeInfo.getCurrent() / timesInfo.getCurrent() * 10) / 10000.0);
        result.setLast(Math.round(onlineTimeInfo.getLast() / timesInfo.getLast() * 10) / 10000.0);
        result.setIncreasement();

        return result;
    }

    @PostMapping("getStartupTimesInfoTrend")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "启动次数趋势图", notes = "getStartupTimesInfoTrend", httpMethod = "POST")
    public List<GuancetaiQuotaTrendItem> getStartupTimesInfoTrend(@RequestBody @ApiParam GuancetaiQuotaParam param){
        return guancetaiBaseService.getStartupTimesInfoTrend(param);
    }


    @PostMapping("getStartupUsersInfoTrend")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "启动用户数趋势图", notes = "getStartupUsersInfoTrend", httpMethod = "POST")
    public List<GuancetaiQuotaTrendItem> getStartupUsersInfoTrend(@RequestBody @ApiParam GuancetaiQuotaParam param){
        return guancetaiBaseService.getStartupUsersInfoTrend(param);
    }

    @PostMapping("getAvgStartupUsersInfoTrend")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "人均启动次数趋势图", notes = "getAvgStartupUsersInfoTrend", httpMethod = "POST")
    public List<GuancetaiQuotaTrendItem> getAvgStartupUsersInfoTrend(@RequestBody @ApiParam GuancetaiQuotaParam param){
        List<GuancetaiQuotaTrendItem> usersInfoTrend = guancetaiBaseService.getStartupUsersInfoTrend(param);
        List<GuancetaiQuotaTrendItem> timesInfoTrend = guancetaiBaseService.getStartupTimesInfoTrend(param);
        List<GuancetaiQuotaTrendItem> result = new ArrayList<GuancetaiQuotaTrendItem>();
        mergeResult(timesInfoTrend, usersInfoTrend, result);

        return result;
    }

    @PostMapping("getSetupTimeOnlineInfoTrend")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "次停留时长趋势图(s)", notes = "getSetupTimeOnlineInfoTrend", httpMethod = "POST")
    public List<GuancetaiQuotaTrendItem> getSetupTimeOnlineInfoTrend(@RequestBody @ApiParam GuancetaiQuotaParam param){
        List<GuancetaiQuotaTrendItem> onlineTimeTrend = guancetaiBaseService.getBusinessOnlineTimeTrend(param);
        List<GuancetaiQuotaTrendItem> timesInfoTrend = guancetaiBaseService.getStartupTimesInfoTrend(param);
        List<GuancetaiQuotaTrendItem> result = new ArrayList<GuancetaiQuotaTrendItem>();
        mergeResult(onlineTimeTrend, timesInfoTrend, result);
        for (GuancetaiQuotaTrendItem item : result) {
            item.setValue(Math.round(item.getValue() * 10) / 10000.0);
        }
        return result;
    }

    @PostMapping("getActiveUsersInfoTrend")
    @ApiResponse(code = 200, message = "successs", response = GuancetaiQuotaInfo.class)
    @ApiOperation(value = "活跃用户趋势图", notes = "getActiveUsersInfoTrend", httpMethod = "POST")
    public List<GuancetaiQuotaTrendItem> getActiveUsersInfoTrend(@RequestBody @ApiParam GuancetaiQuotaParam param){
        return guancetaiBaseService.getActiveUsersInfoTrend(param);
    }

}
