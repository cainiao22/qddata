package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DsMaidianPageParamRelationship;

public interface DsMaidianPageParamRelationshipMapper {

    int deleteByPrimaryKey(Long id);

    int insert(DsMaidianPageParamRelationship record);

    int insertSelective(DsMaidianPageParamRelationship record);

    DsMaidianPageParamRelationship selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DsMaidianPageParamRelationship record);

    int updateByPrimaryKey(DsMaidianPageParamRelationship record);

    void deleteByPageId(Long pageId);

    void deleteAll();
}