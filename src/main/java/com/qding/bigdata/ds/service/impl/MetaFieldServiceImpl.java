package com.qding.bigdata.ds.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.MetaFieldDao;
import com.qding.bigdata.ds.model.MetaField;
import com.qding.bigdata.ds.model.MetaTable;
import com.qding.bigdata.ds.service.MetaFieldService;
import com.qding.bigdata.ds.service.MetaTableService;

@Service
public class MetaFieldServiceImpl extends BaseServiceImpl<MetaField> implements MetaFieldService {
	@Autowired
	private MetaTableService metaFieldService;
	@Autowired
	private MetaFieldDao metaFieldDao;

	@Override
	public BaseDao<MetaField> getDao() {
		return metaFieldDao;
	}

	@Override
	public List<MetaField> list(MetaField t) {
		List<MetaField> resultList = super.list(t);
		Set<String> ids = new HashSet<String>();
		for (MetaField mf : resultList) {
			if (StringUtils.isNotBlank(mf.getSourceTables()))
				ids.addAll(Arrays.asList(mf.getSourceTables().split(",")));
		}
		List<MetaTable> listByIDs = this.metaFieldService.listByIDs(ids);
		Map<String, MetaTable> metaTableMap = new HashMap<String, MetaTable>();
		for (MetaTable metaTable : listByIDs) {
			metaTableMap.put(metaTable.getId(), metaTable);
		}
		for (MetaField mf : resultList) {
			if (StringUtils.isBlank(mf.getSourceTables())) {
				continue;
			}
			if (mf.getSourceTablesObj() == null) {
				mf.setSourceTablesObj(new ArrayList<MetaTable>());
			}
			for (String tableId : mf.getSourceTables().split(",")) {
				if (metaTableMap.containsKey(tableId)) {
					mf.getSourceTablesObj().add(metaTableMap.get(tableId));
				}
			}
		}
		return resultList;
	}

}
