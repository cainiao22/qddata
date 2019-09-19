package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DsMaidianSkip;

import java.util.List;

public interface DsMaidianSkipMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DsMaidianSkip record);

    int insertSelective(DsMaidianSkip record);

    DsMaidianSkip selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DsMaidianSkip record);

    int updateByPrimaryKey(DsMaidianSkip record);

    List<DsMaidianSkip> query(DsMaidianSkip param);

    void deleteAll();
}