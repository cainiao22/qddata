package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.Business;

import java.util.List;

public interface BusinessMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Business record);

    int insertSelective(Business record);

    Business selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Business record);

    int updateByPrimaryKey(Business record);

    List<Business> getAll();
}