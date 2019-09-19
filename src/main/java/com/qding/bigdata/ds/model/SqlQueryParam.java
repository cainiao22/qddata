package com.qding.bigdata.ds.model;

public class SqlQueryParam {
	/**
	 * 1:数据仓库 2：impala 3：es
	 */
	private String dataSource;
	private String sql;
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}

	
}
