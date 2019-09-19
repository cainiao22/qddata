package com.qding.bigdata.ds.dao;

import org.apache.ibatis.annotations.Param;

import com.qding.bigdata.ds.model.SchemeInfo;

/**
 * Created by QDHL on 2017/7/26.
 */
public interface SchemeDao extends  BaseDao<SchemeInfo> {

     SchemeInfo queryById(@Param("id")String id);

     SchemeInfo updateStatus( String id,String status);


}
