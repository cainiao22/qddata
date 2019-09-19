package com.qding.bigdata.ds.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qding.bigdata.ds.model.CommonDataDetail;
import com.qding.bigdata.ds.model.CommonDataSql;

public interface CommonDataSqlDao {
    int deleteByPrimaryKey(Integer id);

    int insert(CommonDataSql record);

    int insertSelective(CommonDataSql record);

    CommonDataSql selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommonDataSql record);

    int updateByPrimaryKey(CommonDataSql record);

    CommonDataSql getBySummaryId(Integer commonDataSummaryId);

    List<Map<String,Object>> execute(@Param("sql") String queryContent);

    Integer executeCount(@Param("sql") String queryContent);

    int deleteBySummaryId(@Param("commonDataSummaryId") Integer commonDataSummaryId);

    int updateBySummaryId(CommonDataSql commonDataSql);

    List<CommonDataSql> getListBySummaryIds(List<CommonDataDetail> list);

    CommonDataSql getBySummaryName(String name);
}