package com.qding.bigdata.ds.dao;

import java.util.List;

import com.qding.bigdata.ds.model.EtlJobExecuteLog;
import com.qding.bigdata.ds.model.EtlJobQueue;

public interface EtlJobQueueDao  extends BaseDao<EtlJobQueue> {

	List<EtlJobExecuteLog> listExecuteLog(EtlJobQueue etlJobQueue);

}
