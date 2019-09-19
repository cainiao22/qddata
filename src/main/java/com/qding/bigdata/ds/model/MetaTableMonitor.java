package com.qding.bigdata.ds.model;

import java.util.Date;

public class MetaTableMonitor extends BaseModel {

    private String id;


    private String metaTableId;


    private String monitorSql;


    private Integer valueMin;


    private Integer valueMax;


    private Integer enableMonitor;


    private Integer monitorjobScheduleHour;


    private Integer monitorjobScheduleMinute;


    private Date createTime;


    private Date updateTime;


    private Integer priority;

    private String monitorName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMetaTableId() {
        return metaTableId;
    }

    public void setMetaTableId(String metaTableId) {
        this.metaTableId = metaTableId;
    }

    public String getMonitorSql() {
        return monitorSql;
    }

    public void setMonitorSql(String monitorSql) {
        this.monitorSql = monitorSql;
    }

    public Integer getValueMin() {
        return valueMin;
    }

    public void setValueMin(Integer valueMin) {
        this.valueMin = valueMin;
    }

    public Integer getValueMax() {
        return valueMax;
    }

    public void setValueMax(Integer valueMax) {
        this.valueMax = valueMax;
    }

    public Integer getEnableMonitor() {
        return enableMonitor;
    }

    public void setEnableMonitor(Integer enableMonitor) {
        this.enableMonitor = enableMonitor;
    }

    public Integer getMonitorjobScheduleHour() {
        return monitorjobScheduleHour;
    }

    public void setMonitorjobScheduleHour(Integer monitorjobScheduleHour) {
        this.monitorjobScheduleHour = monitorjobScheduleHour;
    }

    public Integer getMonitorjobScheduleMinute() {
        return monitorjobScheduleMinute;
    }

    public void setMonitorjobScheduleMinute(Integer monitorjobScheduleMinute) {
        this.monitorjobScheduleMinute = monitorjobScheduleMinute;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }
}