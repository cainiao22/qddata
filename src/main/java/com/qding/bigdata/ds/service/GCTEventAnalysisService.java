package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.GCTEventV2Param;

import java.util.List;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-8-17 上午9:59
 * @Description TODO
 **/

public interface GCTEventAnalysisService extends GCTV2BaseService {

    Map<String, List<Map<String, Object>>> getTableAnalysis(List<GCTEventV2Param> params);



}
