package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DsMonitorResult;

public interface DsMonitorResultDao {
    int deleteByPrimaryKey(Long id);

    int insert(DsMonitorResult record);

    int insertSelective(DsMonitorResult record);

    DsMonitorResult selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DsMonitorResult record);

    int updateByPrimaryKey(DsMonitorResult record);
}