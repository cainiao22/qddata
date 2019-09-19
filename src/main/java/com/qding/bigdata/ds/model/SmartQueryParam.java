package com.qding.bigdata.ds.model;

public class SmartQueryParam {

	private String tableId;
	private String fullTableName;
	private String groupBy;
	private String metric ;
	private String filter;
	private Integer limit;
	
	
	public String getFullTableName() {
		return fullTableName;
	}
	public void setFullTableName(String fullTableName) {
		this.fullTableName = fullTableName;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getGroupBy() {
		return groupBy;
	}
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}
	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
 
	
}
