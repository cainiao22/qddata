package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.dao.ConfigDao;
import com.qding.bigdata.ds.model.Config;
import com.qding.bigdata.ds.service.ConfigService;
import com.qding.bigdata.ds.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yanpf on 2017/9/5.
 */

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigDao configDao;

    @Override
    public Config getConfig(String type, String key) {
        return configDao.getConfigByKey(type, key);
    }

    @Override
    public List<Config> getConfigByType(String type) {
        return configDao.getConfigByType(type);
    }

    @Override
    public SearchResult<Config> query(Config param) {
        SearchResult<Config> result = new SearchResult<Config>();
        List<Config> list = configDao.list(param);
        int count = configDao.count(param);
        result.setPageCount(param.getPageCount());
        result.setRows(list);
        result.setTotal(count);
        result.setCurrentPage(param.getPage());

        return result;
    }

    @Override
    public Config getConfigById(String id) {
        Config param = new Config();
        param.setId(id);
        Config config = configDao.getById(param);
        return config;
    }

    @Override
    public void save(Config config) {
        config.setId(UUIDUtil.createId());
        config.setCreateTime(new Date());
        config.setUpdateTime(new Date());
        configDao.save(config);
    }

    @Override
    public void update(Config config) {
        configDao.updateById(config);
    }

    @Override
    public void delete(String id) {
        Config config = new Config();
        config.setId(id);
        configDao.delete(config);
    }
}
