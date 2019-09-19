package com.qding.bigdata.ds.model;

/**
 * Created by syj on 2018/8/21.
 *罗盘物业云指标录入
 */
public class MidLuopanLuruCityData extends BaseModel{
    private String regionId;

    private String regionName;

    private String jsonValue;

    private Long sortNo;

    private String createtime;

    private String updatetime;

    private String createUser;

    private String updateUser;

    private String todoProjectNum;
    private String doingProjectNum;
    private String doneProjectNum;
    private String cancelProjectNum;

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

    public String getJsonValue() {
        return jsonValue;
    }
    public void setJsonValue(String jsonValue) {
        this.jsonValue = jsonValue == null ? null : jsonValue.trim();
    }

    public Long getSortNo() {
        return sortNo;
    }
    public void setSortNo(Long sortNo) {
        this.sortNo = sortNo;
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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime== null ? null : createtime.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime== null ? null : updatetime.trim();
    }

    public String getTodoProjectNum() {
        return todoProjectNum== null ? "0" : todoProjectNum.trim();
    }

    public void setTodoProjectNum(String todoProjectNum) {
        this.todoProjectNum = todoProjectNum== null ? "0" : todoProjectNum.trim();
    }

    public String getDoingProjectNum() {
        return doingProjectNum== null ? "0" : doingProjectNum.trim();
    }

    public void setDoingProjectNum(String doingProjectNum) {
        this.doingProjectNum = doingProjectNum== null ? "0" : doingProjectNum.trim();
    }

    public String getDoneProjectNum() {
        return doneProjectNum== null ? "0" : doneProjectNum.trim();
    }

    public void setDoneProjectNum(String doneProjectNum) {
        this.doneProjectNum = doneProjectNum== null ? "0" : doneProjectNum.trim();
    }

    public String getCancelProjectNum() {
        return cancelProjectNum== null ? "0" : cancelProjectNum.trim();
    }

    public void setCancelProjectNum(String cancelProjectNum) {
        this.cancelProjectNum = cancelProjectNum== null ? "0" : cancelProjectNum.trim();
    }

    @Override
    public String toString() {
        return "MidLuopanLuruCityData{" +
                "regionId='" + regionId + '\'' +
                ", regionName='" + regionName + '\'' +
                ", jsonValue='" + jsonValue + '\'' +
                ", sortNo=" + sortNo +
                ", createtime='" + createtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", todoProjectNum='" + todoProjectNum + '\'' +
                ", doingProjectNum='" + doingProjectNum + '\'' +
                ", doneProjectNum='" + doneProjectNum + '\'' +
                ", cancelProjectNum='" + cancelProjectNum + '\'' +
                '}';
    }
}