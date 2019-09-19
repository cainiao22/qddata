package com.qding.bigdata.ds.dao;

import java.util.List;

import com.qding.bigdata.ds.model.CommonDataDetail;
import com.qding.bigdata.ds.model.CommonDataSummary;

public interface CommonDataSummaryDao {
    int deleteByPrimaryKey(Integer id);

    int insert(CommonDataSummary record);

    int insertSelective(CommonDataSummary record);

    CommonDataSummary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommonDataSummary record);

    int updateByPrimaryKey(CommonDataSummary record);

    CommonDataSummary getByName(String name);

    List<CommonDataSummary> query(CommonDataDetail commonDataDetail);

    int queryCount(CommonDataDetail commonDataDetail);
}