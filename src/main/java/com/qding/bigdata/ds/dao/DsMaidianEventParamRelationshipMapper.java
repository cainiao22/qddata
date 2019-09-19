package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DsMaidianEventParamRelationship;

public interface DsMaidianEventParamRelationshipMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DsMaidianEventParamRelationship record);

    int insertSelective(DsMaidianEventParamRelationship record);

    DsMaidianEventParamRelationship selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DsMaidianEventParamRelationship record);

    int updateByPrimaryKey(DsMaidianEventParamRelationship record);

    void deleteByEventId(Long eventId);

    void deleteAll();
}