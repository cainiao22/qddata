package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.GCTEventV2Param;
import com.qding.bigdata.ds.model.GCTOverallV2Param;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-8-16 上午11:53
 * @Description TODO
 **/

public interface GCTEventV2Dao {

    List<Map<String, Object>> getTopVisitEvent(@Param("param") GCTOverallV2Param param);

    List<Map<String, Object>> getTableAnalysis(@Param("param") GCTEventV2Param param);

    Map<String, Object> getAnalysisOverView(@Param("param") GCTEventV2Param param);

    List<Map<String, Object>> getAnalysisOverviewByCompany(@Param("param") GCTOverallV2Param param, @Param("action") String action);

    Long getAnalysisOverviewByQyrCompany(@Param("param") GCTOverallV2Param param);
}
