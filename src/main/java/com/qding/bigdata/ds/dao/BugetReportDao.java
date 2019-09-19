package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.BugetReport;

import java.util.List;

public interface BugetReportDao extends BaseDao<BugetReport> {

    List<BugetReport> getTotal(BugetReport bugetReport);

}
