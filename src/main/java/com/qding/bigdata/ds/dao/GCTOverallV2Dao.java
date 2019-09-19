package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.GCTOverallV2Param;

import java.util.List;
import java.util.Map;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/14 下午1:48
 */
public interface GCTOverallV2Dao {
    /**
     * 获取整体分析-新增注册用户
     * @param gctOverallV2Param
     * @return
     */
    List<Map<String, Object>> getRegAnalysis(GCTOverallV2Param gctOverallV2Param);

    /**
     * 获取整体分析-活跃用户
     * @param gctOverallV2Param
     * @return
     */
    List<Map<String,Object>> getActiveAnalysis(GCTOverallV2Param gctOverallV2Param);
    /**
     * 获取整体分析-app启动次数
     * @param gctOverallV2Param
     * @return
     */
    List<Map<String,Object>> getStartupAnalysis(GCTOverallV2Param gctOverallV2Param);
    /**
     * 获取整体分析-人均启动次数
     * @param gctOverallV2Param
     * @return
     */
    List<Map<String,Object>> getPreStartupAnalysis(GCTOverallV2Param gctOverallV2Param);
    /**
     * 获取整体分析-人均使用时常
     * @param gctOverallV2Param
     * @return
     */
    List<Map<String,Object>> getPreVisitAnalysis(GCTOverallV2Param gctOverallV2Param);
    /**
     * 当日新增注册用户 当日累计活跃用户
     * @param gctOverallV2Param
     * @return
     */
    Map<String, Object> getBaseRealTimeAnalysisOverview(GCTOverallV2Param gctOverallV2Param);
    /**
     * 获取整体分析的表格
     * @param gctOverallV2Param
     * @return
     */
    List<Map<String,Object>> getTableAnalysis(GCTOverallV2Param gctOverallV2Param);


    /**
     * 获取整体分析-新增注册用户
     * @param gctOverallV2Param
     * @return
     */
    List<Map<String, Object>> getRegAnalysisCur(GCTOverallV2Param gctOverallV2Param);

    /**
     * 获取整体分析-活跃用户
     * @param gctOverallV2Param
     * @return
     */
    List<Map<String,Object>> getActiveAnalysisCur(GCTOverallV2Param gctOverallV2Param);
    /**
     * 获取整体分析-app启动次数
     * @param gctOverallV2Param
     * @return
     */
    List<Map<String,Object>> getStartupAnalysisCur(GCTOverallV2Param gctOverallV2Param);
    /**
     * 获取整体分析-人均启动次数
     * @param gctOverallV2Param
     * @return
     */
    List<Map<String,Object>> getPreStartupAnalysisCur(GCTOverallV2Param gctOverallV2Param);
    /**
     * 获取整体分析-人均使用时常
     * @param gctOverallV2Param
     * @return
     */
    List<Map<String,Object>> getPreVisitAnalysisCur(GCTOverallV2Param gctOverallV2Param);
    /**
     * 当日新增注册用户 当日累计活跃用户 启动次数
     * @param gctOverallV2Param
     * @return
     */
    Map<String, Object> getBaseRealTimeAnalysisOverviewCur(GCTOverallV2Param gctOverallV2Param);
    /**
     * 获取整体分析的表格
     * @param gctOverallV2Param
     * @return
     */
    List<Map<String,Object>> getTableAnalysisCur(GCTOverallV2Param gctOverallV2Param);





    List<Map<String,Object>> getBaseRealTimeTableAnalysis(GCTOverallV2Param gctOverallV2Param);

    /**
     *
     * @param gctOverallV2Param
     * @return
     */
    Map<String, Object> getPageRealTimeAnalysisOverview(GCTOverallV2Param gctOverallV2Param);

    List<Map<String, Object>> getPageRealTimeTableAnalysis(GCTOverallV2Param param);

    Map<String, Object> getEventRealTimeAnalysisOverview(GCTOverallV2Param param);

    List<Map<String, Object>> getEventRealTimeTableAnalysis(GCTOverallV2Param param);

}
