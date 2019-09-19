package com.qding.bigdata.ds.service.impl;

import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.MetaFieldDao;
import com.qding.bigdata.ds.dao.MetaTableDao;
import com.qding.bigdata.ds.model.MetaField;
import com.qding.bigdata.ds.model.MetaTable;
import com.qding.bigdata.ds.service.MetaFieldService;
import com.qding.bigdata.ds.service.MetaTableService;

import javax.annotation.Resource;

@Service
public class MetaTableServiceImpl extends BaseServiceImpl<MetaTable> implements MetaTableService {

	@Autowired
	private MetaTableDao metaTableDao;
	@Autowired
	private MetaFieldService metaFieldService;

	@Autowired
	private MetaFieldDao metaFieldDao;

	private MetaFieldDao dwMetaFieldDao;

	@Resource(name = "sqlSessionExecutor")
	public void setMetaFieldDao(SqlSession sqlSession) {
		this.dwMetaFieldDao = sqlSession.getMapper(MetaFieldDao.class);
	}


	@Override
	public BaseDao<MetaTable> getDao() {
		return metaTableDao;
	}

	@Override
	public void syncFieldMeta(MetaTable metaTable) {
		List<MetaField> filedInfoList = dwMetaFieldDao.getFiledInfo(metaTable);
		MetaField fieldParams = new MetaField();
		fieldParams.setPage(1);
		fieldParams.setPageCount(999);
		fieldParams.setTableId(metaTable.getId());
		List<MetaField> metaFields = metaFieldDao.list(fieldParams);
		Set<String> set = new HashSet<String>();

		for (MetaField metaField : filedInfoList) {
			set.add(metaField.getName());
			metaField.setTableId(metaTable.getId());
			metaField.setAlias(metaField.getDescription());
			if(isFieldExist(metaField)){
				this.metaFieldService.update(metaField);
			}else {
				this.metaFieldService.save(metaField);
			}
		}

		for (MetaField field : metaFields) {
			if(!set.contains(field.getName()) && field.getIsVirtual() == 0){
				metaFieldDao.delete(field);
			}
		}

	}



	private boolean isFieldExist(MetaField metaField) {
		String type = metaField.getType();
		metaField.setType(null);
		List<MetaField> list = this.metaFieldDao.list(metaField);
		metaField.setType(type);
		metaField.setDataType(DataTypeMap.get(type));
		if(list != null && list.size() != 0) {
			metaField.setId(list.get(0).getId());
			return true;
		}
		return false;
	}

	@Override
	public int updateFiled(String fieldId, String key, String value) {
		MetaField metaField=new MetaField();
		metaField.setId(fieldId);
		int v="true".equals(value)?1:0;
		if("isDate".equals(key)) metaField.setIsDate(v);
		else if("isDim".equals(key)) metaField.setIsDim(v);
		else if("isFilter".equals(key)) metaField.setIsFilter(v);
		else if("isMetric".equals(key)) metaField.setIsMetric(v);
		else if("isVirtual".equals(key)) metaField.setIsVirtual(v);
		else if("dataType".equals(key)) metaField.setDataType(Integer.parseInt(value));
		else return 0;
		return this.metaFieldDao.updateById(metaField);
	}

}
