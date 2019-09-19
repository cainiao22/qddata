package com.qding.bigdata.ds.service;

import java.util.List;

import com.qding.bigdata.ds.model.EtlJobExecuteLog;
import com.qding.bigdata.ds.model.EtlJobQueue;

public interface EtlJobQueueService extends BaseService<EtlJobQueue> {
	
	
	List<EtlJobExecuteLog> listExecuteLog(EtlJobQueue etlJobQueue);
}
