package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DsMaidianEventPageRelationship;

import java.util.List;

public interface DsMaidianEventPageRelationshipMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DsMaidianEventPageRelationship record);

    int insertSelective(DsMaidianEventPageRelationship record);

    DsMaidianEventPageRelationship selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DsMaidianEventPageRelationship record);

    int updateByPrimaryKey(DsMaidianEventPageRelationship record);

    List<DsMaidianEventPageRelationship> query(DsMaidianEventPageRelationship param);

    void deleteAll();
}