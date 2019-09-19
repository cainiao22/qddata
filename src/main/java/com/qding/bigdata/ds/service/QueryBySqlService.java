package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.ExportTitle;

import java.util.List;
import java.util.Map;

public interface QueryBySqlService {

	List<Object[]> queryBySql(String sql);
	List<Object[]> queryBySqlForTitle(String sql, Map<String, ExportTitle> titles);
}
