package com.qding.bigdata.ds.model;

import java.util.List;

public class MidLuopanRengongData extends BaseModel{

    /**
     * 人工录入数据集合
     */
    private List<MidLuopanRengongData> mlrds;

    public List<MidLuopanRengongData> getMlrds() {
        return mlrds;
    }

    public void setMlrds(List<MidLuopanRengongData> mlrds) {
        this.mlrds = mlrds;
    }

    private static final long serialVersionUID = -7983442664191357134L;
    private String id;

    private String dateValue;

    private String regionId;

    private String regionName;

    //社区id
    private String projectId;
    //社区名称
    private String projectName;
    //物业公司
    private  String propertyinfoName;

    private String key;

    private Long value;

    private String createTime;

    private String updateTime;

    private String createUser;

    private String updateUser;

    public String getDateValue() {
        return dateValue;
    }

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue == null ? null : dateValue.trim();
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPropertyinfoName() {
        return propertyinfoName;
    }

    public void setPropertyinfoName(String propertyinfoName) {
        this.propertyinfoName = propertyinfoName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    public Long getValue() {
        return value ;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
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
}