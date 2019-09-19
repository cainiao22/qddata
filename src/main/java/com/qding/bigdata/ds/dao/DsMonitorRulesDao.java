package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DsMonitorRules;

public interface DsMonitorRulesDao {
    int deleteByPrimaryKey(Long id);

    int insert(DsMonitorRules record);

    int insertSelective(DsMonitorRules record);

    DsMonitorRules selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DsMonitorRules record);

    int updateByPrimaryKeyWithBLOBs(DsMonitorRules record);

    int updateByPrimaryKey(DsMonitorRules record);
}