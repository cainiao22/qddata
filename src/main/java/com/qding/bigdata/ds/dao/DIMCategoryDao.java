package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DIMCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DIMCategoryDao{
    int deleteByPrimaryKey(Long id);

    int insert(DIMCategory record);

    int insertSelective(DIMCategory record);

    DIMCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DIMCategory record);

    int updateByPrimaryKey(DIMCategory record);

    List<DIMCategory> listByIDs(@Param("ids") String ids);

    int matchCategoryIDOrNameCount(@Param("searchKey") String searchKey, @Param("isMatch") Boolean isMatch, @Param("flag") boolean flag);

    List<DIMCategory> matchCategoryIDOrName(@Param("searchKey") String searchKey, @Param("isMatch") Boolean isMatch, @Param("flag") boolean flag);
}