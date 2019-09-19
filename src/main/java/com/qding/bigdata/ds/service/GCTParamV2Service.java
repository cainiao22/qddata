package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.GCTParamValueV2Param;

import java.util.List;
import java.util.Map;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/21 下午6:56
 */
public interface GCTParamV2Service extends GCTV2BaseService {
    List<Map<String, Object>> getParamAnalysis(GCTParamValueV2Param param);

    List<Map<String, Object>> getParamValueAnalysis(GCTParamValueV2Param param);
}
