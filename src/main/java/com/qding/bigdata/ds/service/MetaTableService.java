package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.MetaTable;

import java.util.HashMap;
import java.util.Map;

public interface MetaTableService extends BaseService<MetaTable> {
	void syncFieldMeta(MetaTable metaTable);

	int updateFiled(String fieldId, String key, String value);

	Map<String, Integer> DataTypeMap = new HashMap<String, Integer>(){
		{
			put("text", 0);
			put("varchar", 0);
			put("char", 0);

			put("int", 1);
			put("int2", 1);
			put("int4", 1);
			put("int8", 1);
			put("float8", 1);
			put("numeric", 1);


			put("date", 2);
			put("datetime", 2);
			put("timestamp", 2);
		}
	};
}
