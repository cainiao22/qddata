package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DIMLiveClassify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DIMLiveClassifyDao {
    int deleteByPrimaryKey(Long id);

    int insert(DIMLiveClassify record);

    int insertSelective(DIMLiveClassify record);

    DIMLiveClassify selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DIMLiveClassify record);

    int updateByPrimaryKey(DIMLiveClassify record);

    List<DIMLiveClassify> listByIDs(@Param("ids") String ids);

    int matchClassifIDOrNameCount(@Param("searchKey") String searchKey, @Param("isMatch") Boolean isMatch, @Param("flag") boolean flag);

    List<DIMLiveClassify> matchClassifIDOrName(@Param("searchKey") String searchKey, @Param("isMatch") Boolean isMatch, @Param("flag") boolean flag);
}