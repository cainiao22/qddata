package com.qding.bigdata.ds.model;

public class Tag extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3349394179933753380L;
	private String name;
	private String tag;
	private Integer dataType;
	private Integer sortNo;
	private String pid;
	private Integer level;
	private String description;
	private Integer simpleModle;
	private Integer commonConditon;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	 
	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSimpleModle() {
		return simpleModle;
	}

	public void setSimpleModle(Integer simpleModle) {
		this.simpleModle = simpleModle;
	}

	public Integer getCommonConditon() {
		return commonConditon;
	}

	public void setCommonConditon(Integer commonConditon) {
		this.commonConditon = commonConditon;
	}
}
