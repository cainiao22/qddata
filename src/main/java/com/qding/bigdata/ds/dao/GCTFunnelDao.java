package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.GCTFunnel;

import java.util.List;

public interface GCTFunnelDao {
    int insert(GCTFunnel record);
    int update(GCTFunnel record);

    int insertSelective(GCTFunnel record);

    List<GCTFunnel> query(GCTFunnel gctFunnel);
}