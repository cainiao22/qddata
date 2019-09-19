package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.GCTPageV2Param;

import java.util.List;
import java.util.Map;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/16 下午9:03
 */
public interface GCTPageV2Service extends GCTV2BaseService{
    Map<String, List<Map<String, Object>>> getPageAnalysis(List<GCTPageV2Param> paramList);
}