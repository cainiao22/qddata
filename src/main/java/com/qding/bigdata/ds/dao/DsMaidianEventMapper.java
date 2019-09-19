package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DsMaidianEvent;

import java.util.List;

public interface DsMaidianEventMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DsMaidianEvent record);

    int insertSelective(DsMaidianEvent record);

    DsMaidianEvent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DsMaidianEvent record);

    int updateByPrimaryKeyWithBLOBs(DsMaidianEvent record);

    int updateByPrimaryKey(DsMaidianEvent record);

    List<DsMaidianEvent> list(DsMaidianEvent param);

    void deleteAll();

    DsMaidianEvent findByCode(String code);

    List<DsMaidianEvent> queryAll();
}