package com.qding.bigdata.ds.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "GuancetaiPagePathParam", description = "观测台路径分析入参")
public class GuancetaiPagePathParam{
    @ApiModelProperty(value = "时间类型month|day|day_07|day_30", example = "day")
    public String dateType;
    @ApiModelProperty(value = "节点ID", example = "AlltheService")
    public String nodeID;
    @ApiModelProperty(value = "节点ID名称", example = "全部服务")
    public String nodeName;
    @ApiModelProperty(value = "当前节点ID", example = "AlltheService")
    public String pi;
    @ApiModelProperty(value = "上级节点ID", example = "Information")
    public String rpi;
    @ApiModelProperty(value = "查询日期", example = "2019-03-04")
    public String dt;
    @ApiModelProperty(value = "端all|iOS|Android", example = "all")
    public String deviceType;

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi;
    }

    public String getRpi() {
        return rpi;
    }

    public void setRpi(String rpi) {
        this.rpi = rpi;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }
}
