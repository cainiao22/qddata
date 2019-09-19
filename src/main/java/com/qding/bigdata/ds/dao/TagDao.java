package com.qding.bigdata.ds.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qding.bigdata.ds.model.Tag;

public interface TagDao extends BaseDao<Tag> {

    List<Tag> listByTags(@Param("tags")String tags);

}
