package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.PortraitDictionaries;
import com.qding.bigdata.ds.model.PortraitTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PortraitTagDao extends BaseDao<PortraitTag> {

    List<PortraitTag> listByTags(@Param("tags") String tags);

    List<PortraitDictionaries> getDictionaries(PortraitDictionaries portraitDictionaries);

}
