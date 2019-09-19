package com.qding.bigdata.ds.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author yanpf
 * @date 2018/12/26 11:54
 * @description
 */

@ApiModel(value = "GuancetaiQuotaParam", description = "观测台查询的入参")
public class GuancetaiQuotaParam {

    @ApiModelProperty(value = "区分活跃和开启用户", hidden = true)
    private Integer startupTimes;

    @ApiModelProperty(value = "设备类型 (Android | IOS)", example = "Android")
    private String source;
    @ApiModelProperty(value = "业务类型 (千丁app 0, 丁管家 1)", example = "0")
    private Integer businessType;

    @ApiModelProperty(value = "物业公司ID", example = "1099100700000006108")
    private String lesseeId;

    @ApiModelProperty(value = "设备状态(1 登录, 0 未登录)", example = "1")
    private Integer deviceStatus;

    @ApiModelProperty(value = "查询日期", example = "2019-01-25")
    private Date queryDate;

    @ApiModelProperty(value = "返回条数 (仅在获取列表的时候使用)", example = "100")
    private Integer limit;

    public Integer getStartupTimes() {
        return startupTimes;
    }

    public void setStartupTimes(Integer startupTimes) {
        this.startupTimes = startupTimes;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getLesseeId() {
        return lesseeId;
    }

    public void setLesseeId(String lesseeId) {
        this.lesseeId = lesseeId;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
