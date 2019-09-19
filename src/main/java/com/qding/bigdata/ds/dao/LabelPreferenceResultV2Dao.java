package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.LabelPreferenceResultV2;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LabelPreferenceResultV2Dao {
    int deleteByPrimaryKey(Integer id);

    int insert(LabelPreferenceResultV2 record);

    int insertSelective(LabelPreferenceResultV2 record);

    LabelPreferenceResultV2 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LabelPreferenceResultV2 record);

    int updateByPrimaryKey(LabelPreferenceResultV2 record);

    List<LabelPreferenceResultV2> list(@Param("t")LabelPreferenceResultV2 t,@Param("sortAndDesc") String sortAndDesc,@Param("pageCount") Integer pageCount,@Param("offset") Integer offset);
}