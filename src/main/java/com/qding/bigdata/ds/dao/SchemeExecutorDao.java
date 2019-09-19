package com.qding.bigdata.ds.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by yanpf on 2017/9/18.
 */
public interface SchemeExecutorDao {

    int execute(@Param("sql")String sql);

    String checkIsExistTable(@Param("sql")String sql);
}
