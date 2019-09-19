package com.qding.bigdata.ds.model;

import java.util.List;

public class MetaField extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5243557886803851778L;
	private String name;
	private String alias;
	private String description;
	private String type;
	private String keyword;
	private String measureId;
	private String tableId;
	private String sourceTables;
	private List<MetaTable> sourceTablesObj;
	private Integer dataType;
	private Integer isDate;
	private Integer isDim;
	private Integer isFilter;
	private Integer isMetric;
	private Integer isVirtual;

	
	
	
	
	public Integer getIsMetric() {
		return isMetric;
	}
	public void setIsMetric(Integer isMetric) {
		this.isMetric = isMetric;
	}
	public Integer getIsDim() {
		return isDim;
	}
	public void setIsDim(Integer isDim) {
		this.isDim = isDim;
	}
	public Integer getIsFilter() {
		return isFilter;
	}
	public void setIsFilter(Integer isFilter) {
		this.isFilter = isFilter;
	}
	public Integer getIsVirtual() {
		return isVirtual;
	}
	public void setIsVirtual(Integer isVirtual) {
		this.isVirtual = isVirtual;
	}
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public List<MetaTable> getSourceTablesObj() {
		return sourceTablesObj;
	}
	public void setSourceTablesObj(List<MetaTable> sourceTablesObj) {
		this.sourceTablesObj = sourceTablesObj;
	}
	public String getSourceTables() {
		return sourceTables;
	}
	public void setSourceTables(String sourceTables) {
		this.sourceTables = sourceTables;
	}
	public String getMeasureId() {
		return measureId;
	}
	public void setMeasureId(String measureId) {
		this.measureId = measureId;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
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

	public Integer getIsDate() {
		return isDate;
	}

	public void setIsDate(Integer isDate) {
		this.isDate = isDate;
	}
}
