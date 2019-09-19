package com.qding.bigdata.ds.model;

import java.util.List;

public class PortraitTag extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3349394179933753380L;
	private String name;//标签名称
	private String tag;//标签ID
	private Integer dataType;//标签类别  数值  枚举等等
	private Integer sortNo;//排序数值
	private String pid;//父ID
	private Integer level;//节点等级
	private String description;//规则描述
	private String example;//暂时无用
	private Integer status;//0 真实   1 虚拟


	private List<PortraitDictionaries> lpd;

	
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

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public List<PortraitDictionaries> getLpd() {
		return lpd;
	}

	public void setLpd(List<PortraitDictionaries> lpd) {
		this.lpd = lpd;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
