package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DsMaidianPage;
import com.qding.bigdata.ds.model.DsMaidianParam;

import java.util.List;

public interface DsMaidianPageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DsMaidianPage record);

    int insertSelective(DsMaidianPage record);

    DsMaidianPage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DsMaidianPage record);

    int updateByPrimaryKeyWithBLOBs(DsMaidianPage record);

    int updateByPrimaryKey(DsMaidianPage record);

    List<DsMaidianPage> query(DsMaidianPage record);

    List<DsMaidianPage> queryByProductId(Long productId);

    List<DsMaidianParam> getParamsById(Long id);

    DsMaidianPage getMaidianPageByCode(String code);

    void deleteAll();

    List<DsMaidianPage> queryAll();
}