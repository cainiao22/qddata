package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.GCTEventV2Param;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-8-17 上午11:34
 * @Description TODO
 **/

public interface GCTEventV2InfoCubeDao {

    List<Map<String, Object>> getTableAnalysis(@Param("param") GCTEventV2Param param);
}
