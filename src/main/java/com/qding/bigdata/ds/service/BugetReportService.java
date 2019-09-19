package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.BugetReport;

import java.util.List;

public interface BugetReportService extends BaseService<BugetReport>{


    List<BugetReport> getTotal(BugetReport bugetReport);
}
