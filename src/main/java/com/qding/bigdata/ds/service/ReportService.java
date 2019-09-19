package com.qding.bigdata.ds.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.qding.bigdata.ds.model.Report;

public interface ReportService {

  List<Map<String, Object>> execute(Report report, HttpServletRequest request);
  
  List<Map<String, Object>> loadDimension(String dim);
  Map<String, Object> loadDimension2IdNameMap(String dim,String idColumn,String nameColumn);
  List<Map<String, Object>> executeSql(String sql);
}
