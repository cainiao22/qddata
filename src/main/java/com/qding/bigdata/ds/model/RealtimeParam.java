package com.qding.bigdata.ds.model;

import java.io.Serializable;

public class RealtimeParam implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6142706655724445418L;
    private Integer minute;
    private String plat;
    private Integer chartType;
    private String cityCode;
    private String projectId;
    private String albumIds;
    private Integer limit;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAlbumIds() {
        return albumIds;
    }

    public void setAlbumIds(String albumIds) {
        this.albumIds = albumIds;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public Integer getChartType() {
        return chartType;
    }

    public void setChartType(Integer chartType) {
        this.chartType = chartType;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }



}
