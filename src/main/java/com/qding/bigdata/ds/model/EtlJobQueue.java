package com.qding.bigdata.ds.model;

import java.util.Date;
import java.util.List;


public class EtlJobQueue extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8487315414612013859L;
	/**
	 * 
	 */
	private String id;
	private String type;
	private String tableName;
	private String jobType;
	private String sql;
	private Date scheduleTime;
	private String jobUniqueId;
	private String statusText;
	private String etlJobId;
	

	private List<EtlJobExecuteLog> logList;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String getJobType() {
		return jobType;
	}


	public void setJobType(String jobType) {
		this.jobType = jobType;
	}


	public String getSql() {
		return sql;
	}


	public void setSql(String sql) {
		this.sql = sql;
	}


	public Date getScheduleTime() {
		return scheduleTime;
	}


	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}


	public String getJobUniqueId() {
		return jobUniqueId;
	}


	public void setJobUniqueId(String jobUniqueId) {
		this.jobUniqueId = jobUniqueId;
	}


	


	public String getStatusText() {
		return statusText;
	}


	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}


	public String getEtlJobId() {
		return etlJobId;
	}


	public void setEtlJobId(String etlJobId) {
		this.etlJobId = etlJobId;
	}


	public List<EtlJobExecuteLog> getLogList() {
		return logList;
	}


	public void setLogList(List<EtlJobExecuteLog> logList) {
		this.logList = logList;
	}
	
}
