package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.GCTOverallV2Param;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/19 下午2:58
 */
public interface GCTApplicationInfoV2CubeDao {
    List<Map<String,Object>> getRegAnalysis(@Param("param") GCTOverallV2Param gctOverallV2Param);

    List<Map<String,Object>> getActiveAnalysis(@Param("param") GCTOverallV2Param gctOverallV2Param);

    List<Map<String,Object>> getStartupAnalysis(@Param("param") GCTOverallV2Param gctOverallV2Param);

    List<Map<String,Object>> getPreStartupAnalysis(@Param("param") GCTOverallV2Param gctOverallV2Param);

    List<Map<String,Object>> getPreVisitAnalysis(@Param("param") GCTOverallV2Param gctOverallV2Param);

    List<Map<String,Object>> getTableAnalysis(@Param("param") GCTOverallV2Param gctOverallV2Param);
}
