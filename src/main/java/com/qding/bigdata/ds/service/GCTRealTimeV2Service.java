package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.GCTOverallV2Param;
import com.qding.bigdata.ds.model.GuancetaiQuotaInfo;

import java.util.List;
import java.util.Map;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/14 下午4:24
 */
public interface GCTRealTimeV2Service extends GCTV2BaseService {
    Map<String, GuancetaiQuotaInfo> getRealTimeAnalysis(GCTOverallV2Param param);

    Map<String, List<Map<String, Object>>> getRealTimeTableAnalysis(GCTOverallV2Param param);

    Map<String, List<Map<String, Object>>> getRealTimeTopAnalysis(GCTOverallV2Param param);

    Map<String, GuancetaiQuotaInfo> getRealTimeAnalysisByCompany(GCTOverallV2Param param);

    Map<String, GuancetaiQuotaInfo> getRealTimeAnalysisByQyrCompany(GCTOverallV2Param param);
}
