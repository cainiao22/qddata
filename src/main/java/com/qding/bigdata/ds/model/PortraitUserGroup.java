package com.qding.bigdata.ds.model;


public class PortraitUserGroup extends BaseModel {

    private static final long serialVersionUID = 4634972749267003018L;

    private String name;
    private String conditions;
    private String description;
    private String createUser;
    private String updateUser;
    private Integer isShare;//0:公有  1:私有
	private Integer modModel;//模式：1、简单模式；2、高级模式


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
 
	public Integer getIsShare() {
		return isShare;
	}
	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getModModel() {
		return modModel;
	}

	public void setModModel(Integer modModel) {
		this.modModel = modModel;
	}
}
