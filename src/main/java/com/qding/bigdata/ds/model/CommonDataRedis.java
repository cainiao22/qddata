package com.qding.bigdata.ds.model;

import java.util.Date;

public class CommonDataRedis {
    private Integer id;

    private String dataType;

    private String key;

    private String fields;

    private Date createTime;

    private Date updateTime;

    private Short disabled;

    private Integer commonDataSummaryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields == null ? null : fields.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Short getDisabled() {
        return disabled;
    }

    public void setDisabled(Short disabled) {
        this.disabled = disabled;
    }

    public Integer getCommonDataSummaryId() {
        return commonDataSummaryId;
    }

    public void setCommonDataSummaryId(Integer commonDataSummaryId) {
        this.commonDataSummaryId = commonDataSummaryId;
    }
}