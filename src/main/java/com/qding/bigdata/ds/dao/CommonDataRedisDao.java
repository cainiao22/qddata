package com.qding.bigdata.ds.dao;

import java.util.List;

import com.qding.bigdata.ds.model.CommonDataDetail;
import com.qding.bigdata.ds.model.CommonDataRedis;

public interface CommonDataRedisDao {

    int insert(CommonDataRedis record);

    int insertSelective(CommonDataRedis record);

    void updateBySummaryId(CommonDataRedis commonDataRedis);

    List<CommonDataRedis> getListBySummaryIds(List<CommonDataDetail> detailList);

    void deleteBySummaryId(Integer summaryId);

    CommonDataRedis getBySummaryId(Integer commonDataSummaryId);
}