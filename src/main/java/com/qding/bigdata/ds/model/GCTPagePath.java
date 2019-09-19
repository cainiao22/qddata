package com.qding.bigdata.ds.model;

public class GCTPagePath extends BaseModel{

    private String dateType;

    private String deviceType;

    private String pi;
    private String piName;

    private String rpi;
    private String rpiName;

    private String uv;

    private String dt;

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType == null ? null : dateType.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi == null ? null : pi.trim();
    }

    public String getRpi() {
        return rpi;
    }

    public void setRpi(String rpi) {
        this.rpi = rpi == null ? null : rpi.trim();
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv == null ? null : uv.trim();
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt == null ? null : dt.trim();
    }

    public String getPiName() {
        return piName;
    }

    public void setPiName(String piName) {
        this.piName = piName;
    }

    public String getRpiName() {
        return rpiName;
    }

    public void setRpiName(String rpiName) {
        this.rpiName = rpiName;
    }
}