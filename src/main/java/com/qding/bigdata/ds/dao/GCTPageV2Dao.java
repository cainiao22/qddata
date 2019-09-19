package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.GCTOverallV2Param;
import com.qding.bigdata.ds.model.GCTPageV2Param;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-8-16 上午11:53
 * @Description TODO
 **/

public interface GCTPageV2Dao {

    List<Map<String, Object>> getTopVisitPage(@Param("param") GCTOverallV2Param param);

    List<Map<String,Object>> getPageAnalysis(@Param("param") GCTPageV2Param param);

    List<Map<String, Object>> getAnalysisOverviewByCompany(@Param("param") GCTOverallV2Param param, String action);

    Long getAnalysisOverviewByQyrCompany(@Param("param") GCTOverallV2Param param);

    List<Map<String,Object>> getPageAnalysisCur(@Param("param") GCTPageV2Param gctPageV2Param);
}
