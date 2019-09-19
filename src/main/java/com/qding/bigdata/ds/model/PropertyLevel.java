package com.qding.bigdata.ds.model;

/**
 * Created by Administrator on 2019/3/5 0005.
 */
public class PropertyLevel  extends BaseModel {

    private String city;
    private String property_name;
    private String level;
    private String remarks;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProperty_name() {
        return property_name;
    }

    public void setProperty_name(String property_name) {
        this.property_name = property_name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
