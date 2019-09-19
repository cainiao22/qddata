package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.VO.AnalysisVO;
import com.qding.bigdata.ds.VO.PathAnalysisVO;
import com.qding.bigdata.ds.VO.PathNodeVO;
import com.qding.bigdata.ds.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface GuancetaiPathService {
    String QDING_GCT_EVENTPATH_DETAIL = "qding_gct_eventpath_detail";
    String QDING_GCT_PAGEPATH_DETAIL = "qding_gct_pagepath_detail";
    List<PathNodeVO> getNode(GuancetaiPagePathParam guancetaiPagePathParam);

    PathAnalysisVO getPagePathTree(GuancetaiPagePathParam guancetaiPagePathParam);

    List<AnalysisVO> getBehaviorAnalysis(GuancetaiBehaviorParam guancetaiBehaviorParam);

    ArrayList<AnalysisVO> getFunnelAnalysis(GuancetaiFunnelParam guancetaiFunnelParam);

    List<GCTFunnel> queryFunnelListByProductID(GCTFunnel gctFunnel);

    int insertFunnel(GCTFunnel gctFunnel);

    int updateFunnel(GCTFunnel gctFunnel);

    Map<String,String> getMaidianPageByProductID(DsMaidianPage page);
}
