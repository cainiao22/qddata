package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.EarningsReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EarningsReportDao {
    List<EarningsReport> queryImgInfo(Map<String, String> params);

    int queryCount(Map<String, String> map);

    void deleteImgInfoById(String id);

    int insertEarningsReportInfo(EarningsReport earningsReport);

    String queryMonthlyImgInfo(@Param(value = "date") String date,@Param(value = "type") Integer type);

    EarningsReport queryThisRowInfoById(String id);

    int updateEarningsReportInfoById(EarningsReport earningsReport);
}
