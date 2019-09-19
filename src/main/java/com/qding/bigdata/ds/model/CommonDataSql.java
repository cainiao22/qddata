package com.qding.bigdata.ds.model;

import java.util.Date;

public class CommonDataSql {
    private Integer id;

    private Integer commonDataSummaryId;

    private String queryContent;

    private Integer disabled;

    private Date createTime;

    private Date updateTime;

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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommonDataSummaryId() {
        return commonDataSummaryId;
    }

    public void setCommonDataSummaryId(Integer commonDataSummaryId) {
        this.commonDataSummaryId = commonDataSummaryId;
    }

    public String getQueryContent() {
        return queryContent;
    }

    public void setQueryContent(String queryContent) {
        this.queryContent = queryContent == null ? null : queryContent.trim();
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }
}