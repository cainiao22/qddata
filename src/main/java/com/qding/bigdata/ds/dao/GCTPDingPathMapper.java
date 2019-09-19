package com.qding.bigdata.ds.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-9-3 上午9:54
 * @Description TODO
 **/

public interface GCTPDingPathMapper {

    List<Map<String, Object>> queryUserTrail(@Param("userId") String userId, @Param("type") String type, @Param("startTime") Long startTime, @Param("endTime") Long endTime);
}
