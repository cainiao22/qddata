package com.qding.bigdata.ds.model;

import java.util.Date;

/**
 * @author yanpf
 * @date 2018/7/13 17:28
 * @description
 */
public class TrackMsg {

    /**
     * 访问时间
     */
    private Date time;

    /**
     * 用户
     */
    private String user;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 平台
     */
    private String firSource;

    /**
     * 产品名称
     */
    private String secSource;

    /**
     * 上一次事件
     */
    private String refEvent;

    /**
     * 本次事件
     */
    private String event;

    private Long eventCount;

    /**
     * 停留时间
     */
    private String onlineTime;

    private Long pv;

    private Long uv;

    /**
     * 活跃用户数量
     */
    private Long activeUsers;

    /**
     * 登陆次数
     */
    private Long loginCount;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirSource() {
        return firSource;
    }

    public void setFirSource(String firSource) {
        this.firSource = firSource;
    }

    public String getSecSource() {
        return secSource;
    }

    public void setSecSource(String secSource) {
        this.secSource = secSource;
    }

    public String getRefEvent() {
        return refEvent;
    }

    public void setRefEvent(String refEvent) {
        this.refEvent = refEvent;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    public Long getUv() {
        return uv;
    }

    public void setUv(Long uv) {
        this.uv = uv;
    }

    public Long getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(Long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public Long getEventCount() {
        return eventCount;
    }

    public void setEventCount(Long eventCount) {
        this.eventCount = eventCount;
    }

    public Long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Long loginCount) {
        this.loginCount = loginCount;
    }
}
