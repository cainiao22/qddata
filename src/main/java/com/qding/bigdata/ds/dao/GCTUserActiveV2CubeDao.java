package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.GCTOverallV2Param;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-8-21 下午3:38
 * @Description
 **/

public interface GCTUserActiveV2CubeDao {

    List<Map<String, Object>> getTableAnalysis(@Param("param") GCTOverallV2Param param);

    List<Map<String, Object>> getActiveUserCountAnalysis(@Param("param") GCTOverallV2Param param);

}
