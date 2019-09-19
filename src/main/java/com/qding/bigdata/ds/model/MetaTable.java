package com.qding.bigdata.ds.model;


public class MetaTable extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1150036301614909437L;
	private String name;
	private String alias;
	private String description;
	private String type;
	private String keyword;
	private String owner;
	private int enableEtl;
	private String etlJobType;
	private String etlJobSql;
	private Integer etlJobScheduleHour;
	private Integer etlJobScheduleMinute;
	private Integer priority;
	private Integer queryEnable;
	
	
	
	
	


	public Integer getQueryEnable() {
		return queryEnable;
	}
	public void setQueryEnable(Integer queryEnable) {
		this.queryEnable = queryEnable;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getEtlJobScheduleHour() {
		return etlJobScheduleHour;
	}
	public void setEtlJobScheduleHour(Integer etlJobScheduleHour) {
		this.etlJobScheduleHour = etlJobScheduleHour;
	}
	public Integer getEtlJobScheduleMinute() {
		return etlJobScheduleMinute;
	}
	public void setEtlJobScheduleMinute(Integer etlJobScheduleMinute) {
		this.etlJobScheduleMinute = etlJobScheduleMinute;
	}
	public int getEnableEtl() {
		return enableEtl;
	}
	public void setEnableEtl(int enableEtl) {
		this.enableEtl = enableEtl;
	}
	public String getEtlJobSql() {
		return etlJobSql;
	}
	public void setEtlJobSql(String etlJobSql) {
		this.etlJobSql = etlJobSql;
	}
	public String getEtlJobType() {
		return etlJobType;
	}
	public void setEtlJobType(String etlJobType) {
		this.etlJobType = etlJobType;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	
}
