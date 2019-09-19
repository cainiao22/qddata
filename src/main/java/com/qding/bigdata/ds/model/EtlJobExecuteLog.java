package com.qding.bigdata.ds.model;


public class EtlJobExecuteLog extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2102337816305668215L;
	private String id;
	private String queueId;
	private String etlLog;
	private String createTimeText;
	
	
	public String getCreateTimeText() {
		return createTimeText;
	}

	public void setCreateTimeText(String createTimeText) {
		this.createTimeText = createTimeText;
	}

	public EtlJobExecuteLog(String id, String queueId, String etlLog) {
		super();
		this.id = id;
		this.queueId = queueId;
		this.etlLog = etlLog;
	}
	
	public EtlJobExecuteLog() {
		super();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQueueId() {
		return queueId;
	}
	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}
	public String getEtlLog() {
		return etlLog;
	}
	public void setEtlLog(String etlLog) {
		this.etlLog = etlLog;
	}
	
	
}
