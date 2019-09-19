package com.qding.bigdata.ds.model;

/**
 * Created by lzs on 2018/7/4.
 */
public class CountData {
    private String startTime;
    private String endTime;
    private String source;

    public CountData() {
    }

    public CountData(String startTime, String endTime, String source) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.source = source;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
