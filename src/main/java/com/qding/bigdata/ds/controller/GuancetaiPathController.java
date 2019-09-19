package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.VO.AnalysisVO;
import com.qding.bigdata.ds.VO.PathAnalysisVO;
import com.qding.bigdata.ds.VO.PathNodeVO;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.service.GuancetaiPathService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
//@Api(value = "guancetai-page-path-api", description = "观测台路径分析相关查询接口")
public class GuancetaiPathController {
    @Autowired
    GuancetaiPathService guancetaiPathService;


    @PostMapping("getPathNode")
    @ApiResponse(code = 200, message = "successs", response = PathAnalysisVO.class)
    @ApiOperation(value = "路径分析的所有节点列表", notes = "getPathNode", httpMethod = "POST")
    public List<PathNodeVO> getPathNode (@RequestBody @ApiParam GuancetaiPagePathParam guancetaiPagePathParam){
        return guancetaiPathService.getNode(guancetaiPagePathParam);
    }

    @PostMapping("getPathTree")
    @ApiResponse(code = 200, message = "successs", response = PathAnalysisVO.class)
    @ApiOperation(value = "路径分析完整的树图", notes = "getPathTree", httpMethod = "POST")
    public PathAnalysisVO getPathTree (@RequestBody @ApiParam GuancetaiPagePathParam guancetaiPagePathParam){
        return guancetaiPathService.getPagePathTree(guancetaiPagePathParam);
    }

    @PostMapping("getMaidianPage")
    @ApiResponse(code = 200, message = "successs", response = AnalysisVO.class)
    @ApiOperation(value = "行为分析指定页面接口", notes = "getMaidianPage", httpMethod = "POST")
    public Map<String,String> getMaidianPage (@RequestBody @ApiParam DsMaidianPage page){
        return guancetaiPathService.getMaidianPageByProductID(page);
    }

    @PostMapping("getBehaviorAnalysis")
    @ApiResponse(code = 200, message = "successs", response = AnalysisVO.class)
    @ApiOperation(value = "行为分析", notes = "getBehaviorAnalysis", httpMethod = "POST")
    public List<AnalysisVO> getBehaviorAnalysis (@RequestBody @ApiParam GuancetaiBehaviorParam guancetaiBehaviorParam){
        return guancetaiPathService.getBehaviorAnalysis(guancetaiBehaviorParam);
    }

    @PostMapping("getFunnelAnalysis")
    @ApiResponse(code = 200, message = "successs", response = AnalysisVO.class)
    @ApiOperation(value = "漏斗分析", notes = "getFunnelAnalysis", httpMethod = "POST")
    public List<AnalysisVO> getFunnelAnalysis (@RequestBody @ApiParam GuancetaiFunnelParam guancetaiFunnelParam){
        return guancetaiPathService.getFunnelAnalysis(guancetaiFunnelParam);
    }

    @PostMapping("getFunnelParamList")
    @ApiResponse(code = 200, message = "successs", response = GCTFunnel.class)
    @ApiOperation(value = "获取漏斗参数列表", notes = "getFunnelParamList", httpMethod = "POST")
    public List<GCTFunnel> getFunnelParamList (@RequestBody @ApiParam GCTFunnel gctFunnel){
        return guancetaiPathService.queryFunnelListByProductID(gctFunnel);
    }

    @PostMapping("insertFunnelParam")
    @ApiResponse(code = 200, message = "successs",response = int.class)
    @ApiOperation(value = "插入漏斗参数", notes = "insertFunnelParam", httpMethod = "POST")
    public int insertFunnelParam (@RequestBody @ApiParam GCTFunnel gctFunnel){
        return guancetaiPathService.insertFunnel(gctFunnel);
    }

    @PostMapping("updateFunnelParam")
    @ApiResponse(code = 200, message = "successs",response = int.class)
    @ApiOperation(value = "更新漏斗参数", notes = "updateFunnelParam", httpMethod = "POST")
    public int updateFunnelParam (@RequestBody @ApiParam GCTFunnel gctFunnel){
        return guancetaiPathService.updateFunnel(gctFunnel);
    }
}
