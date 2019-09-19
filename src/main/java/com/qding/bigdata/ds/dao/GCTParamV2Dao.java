package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.GCTParamValueV2Param;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/21 下午8:17
 */
public interface GCTParamV2Dao {
    List<Map<String,Object>> getParamKey(@Param("param") GCTParamValueV2Param param);

    List<Map<String,Object>> getParamKeyList(@Param("param") GCTParamValueV2Param param);
}
