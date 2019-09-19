package com.qding.bigdata.ds.model;

/**
 * Created by yanpf on 2017/9/18.
 */
public class WechatReport extends BaseModel {

    /**
     * 数据时间
     */
    private String dt;

    private String openId;

    private String mobile;
    /**
     * 发送状态
     */
    private String sendStatus;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }
}
