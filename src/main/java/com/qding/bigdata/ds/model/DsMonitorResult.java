package com.qding.bigdata.ds.model;

import java.math.BigDecimal;
import java.util.Date;

public class DsMonitorResult {
    private Long id;

    private Long monitorRuleId;

    private BigDecimal resultValue;

    private Integer offsetDay;

    private Date scheduleTime;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMonitorRuleId() {
        return monitorRuleId;
    }

    public void setMonitorRuleId(Long monitorRuleId) {
        this.monitorRuleId = monitorRuleId;
    }

    public BigDecimal getResultValue() {
        return resultValue;
    }

    public void setResultValue(BigDecimal resultValue) {
        this.resultValue = resultValue;
    }

    public Integer getOffsetDay() {
        return offsetDay;
    }

    public void setOffsetDay(Integer offsetDay) {
        this.offsetDay = offsetDay;
    }

    public Date getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
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
}