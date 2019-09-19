package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.GCTPageV2Param;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/21 上午10:47
 */
public interface GCTPageInfoCubeV2Dao {
    List<Map<String,Object>> getPageAnalysis(@Param("param") GCTPageV2Param param);
}
