package com.qding.bigdata.ds.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface QueryBySqlDao {

	List<Map<String, Object>> queryBySql(@Param("sql")String sql);

}
