package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.Report;
import com.qding.bigdata.ds.model.ReportConfig;

public interface ReportConfigService extends BaseService<ReportConfig> {


  void fillDimention(Report report);

  

}
