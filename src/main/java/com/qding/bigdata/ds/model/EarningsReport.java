package com.qding.bigdata.ds.model;

import java.io.Serializable;

public class EarningsReport extends BaseModel implements Serializable{

    /**
     * 类型
     */
    private Integer type;

    /**
     * 上传人
     */
    private String upname;

    /**
     * 图片路径
     */
    private String url;

    /**
     * 日期
     */
    private String createData;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUpname() {
        return upname;
    }

    public void setUpname(String upname) {
        this.upname = upname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateData() {
        return createData;
    }

    public void setCreateData(String createData) {
        this.createData = createData;
    }

    @Override
    public String toString() {
        return "EarningsReport{" +
                ", type=" + type +
                ", upname='" + upname + '\'' +
                ", url='" + url + '\'' +
                ", createData='" + createData + '\'' +
                '}';
    }
}
