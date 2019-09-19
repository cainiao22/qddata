package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.GCTOverallV2Param;

import java.util.List;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-8-21 下午3:58
 * @Description TODO
 **/

public interface GCTUserActiveV2Service extends GCTV2BaseService {

    Map<String, List<Map<String, Object>>> getTableAnalysis(List<GCTOverallV2Param> params);
}
