package com.qding.bigdata.ds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.MeasureDao;
import com.qding.bigdata.ds.model.Measure;
import com.qding.bigdata.ds.service.MeasureService;

@Service
public class MeasureServiceImpl extends BaseServiceImpl<Measure> implements MeasureService {

	@Autowired
	private MeasureDao measureDao;

	@Override
	public BaseDao<Measure> getDao() {
		return measureDao;
	}

}
