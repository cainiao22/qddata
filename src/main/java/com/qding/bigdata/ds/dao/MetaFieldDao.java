package com.qding.bigdata.ds.dao;

import java.util.List;

import com.qding.bigdata.ds.model.MetaField;
import com.qding.bigdata.ds.model.MetaTable;

public interface MetaFieldDao extends BaseDao<MetaField> {

	List<MetaField> getFiledInfo(MetaTable metaTable);
}
