package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.GCTPagePath;
import com.qding.bigdata.ds.model.GuancetaiPagePathParam;

import java.util.List;

public interface GCTPagePathDao extends BaseDao<GCTPagePath>{
    int deleteByPrimaryKey(Integer id);

    int insert(GCTPagePath record);

    int insertSelective(GCTPagePath record);

    GCTPagePath selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GCTPagePath record);

    int updateByPrimaryKey(GCTPagePath record);

    List<GCTPagePath> getAllNode(GuancetaiPagePathParam guancetaiPagePathParam);

    List<GCTPagePath> getPagePath(GuancetaiPagePathParam guancetaiPagePathParam);
}