package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DsMaidianParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DsMaidianParamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DsMaidianParam record);

    int insertSelective(DsMaidianParam record);

    DsMaidianParam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DsMaidianParam record);

    int updateByPrimaryKeyWithBLOBs(DsMaidianParam record);

    int updateByPrimaryKey(DsMaidianParam record);

    List<DsMaidianParam> query(DsMaidianParam param);

    DsMaidianParam getByCode(String code);

    void deleteAll();

    List<DsMaidianParam> queryByPageCode(@Param("pageCode") String pageCode);

    List<DsMaidianParam> queryByEventCode(@Param("eventCode") String eventCode);

}