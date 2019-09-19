package com.qding.bigdata.ds.model;

public class Measure extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7469183797720615964L;
	
	private String name;
	private String code;
	private String description;
	private String keyword;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
 
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
