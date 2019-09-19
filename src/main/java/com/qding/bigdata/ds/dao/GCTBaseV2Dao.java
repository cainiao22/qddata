package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.GCTOverallV2Param;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-8-16 上午11:33
 * @Description TODO
 **/

public interface GCTBaseV2Dao {

    List<Map<String, Object>> getTopActiveCompany(@Param("param") GCTOverallV2Param param);

    List<Map<String, Object>> getTopActiveVersion(@Param("param") GCTOverallV2Param param);

    List<Map<String, Object>> getTopActivePhoneType(@Param("param") GCTOverallV2Param param);

    List<Map<String, Object>> getAnalysisOverviewByCompany(@Param("param") GCTOverallV2Param param, @Param("action") String action);

    Long getAnalysisOverviewByQyrCompany(@Param("param") GCTOverallV2Param param);


    List<Map<String, Object>> getActiveInfoTableAnalysis(@Param("param") GCTOverallV2Param param);

    List<Map<String, Object>> getActiveUserCount(@Param("param") GCTOverallV2Param param);

    Map<String, Object> getActiveUserCountOverView(@Param("param") GCTOverallV2Param param);

    List<Map<String, Object>> getRegNewUserCount(@Param("param") GCTOverallV2Param param);

    Map<String, Object> getRegNewUserCountOverView(@Param("param") GCTOverallV2Param param);

    Map<String, Object> getActiveInfoTableAnalysisOverView(@Param("param") GCTOverallV2Param param);

}
