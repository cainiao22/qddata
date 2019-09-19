package com.qding.bigdata.ds.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.EtlJobQueueDao;
import com.qding.bigdata.ds.model.EtlJobExecuteLog;
import com.qding.bigdata.ds.model.EtlJobQueue;
import com.qding.bigdata.ds.service.EtlJobQueueService;
import com.qding.bigdata.ds.util.DateUtil;

@Service
public class EtlJobQueueServiceImpl extends BaseServiceImpl<EtlJobQueue> implements EtlJobQueueService {

	@Autowired
	private EtlJobQueueDao etlJobQueueDao;
 

	@Override
	public BaseDao<EtlJobQueue> getDao() {
		return etlJobQueueDao;
	}


	@Override
	public List<EtlJobExecuteLog> listExecuteLog(EtlJobQueue etlJobQueue) {
	List<EtlJobExecuteLog> result = etlJobQueueDao.listExecuteLog(etlJobQueue);
	for (EtlJobExecuteLog etlJobExecuteLog : result) {
		etlJobExecuteLog.setCreateTimeText(DateUtil.formatDateToFullString2(etlJobExecuteLog.getCreateTime()));
	}
	return result;
	}


}
