package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.GCTOverallV2Param;

import java.util.List;
import java.util.Map;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/13 下午8:54
 */
public interface GCTOverallV2Service extends GCTV2BaseService {
    /**
     * 返回今天和昨天的24小时新增注册用户数据
     * @param paramList
     * @return
     */
    Map<String,List<Map<String,Object>>> getOverallAnalysis(List<GCTOverallV2Param> paramList);

}
