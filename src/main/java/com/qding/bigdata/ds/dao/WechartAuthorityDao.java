package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.WechartAuthority;
import org.apache.ibatis.annotations.Param;

public interface WechartAuthorityDao {
    int deleteByPrimaryKey(Long id);

    int insert(WechartAuthority record);

    int insertSelective(WechartAuthority record);

    WechartAuthority selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WechartAuthority record);

    int updateByPrimaryKey(WechartAuthority record);

    WechartAuthority getWechartAuthority(@Param("openId") String openId, @Param("authorityFlag") String authorityFlag);
}