package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.Config;

import java.util.List;

public interface ConfigDao extends BaseDao<Config> {
    int insert(Config record);

    int insertSelective(Config record);

    Config getConfigByKey(String type, String key);

    List<Config> getConfigByType(String type);
}