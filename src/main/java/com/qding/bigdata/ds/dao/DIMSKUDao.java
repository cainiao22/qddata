package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DIMSKU;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DIMSKUDao {
    int deleteByPrimaryKey(Long id);

    int insert(DIMSKU record);

    int insertSelective(DIMSKU record);

    DIMSKU selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DIMSKU record);

    int updateByPrimaryKey(DIMSKU record);

    List<DIMSKU> listByIDs(@Param("ids") String ids);

    List<DIMSKU> matchSKUIDOrWareName(@Param("searchKey") String searchKey, @Param("isMatch") Boolean isMatch, @Param("flag") boolean flag);

    int matchSKUIDOrWareNameCount(@Param("searchKey") String searchKey, @Param("isMatch") Boolean isMatch, @Param("flag") boolean flag);
}