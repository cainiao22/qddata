package com.qding.bigdata.ds.model;

public class WechartAuthority {
    private Long id;

    private String authorityFlag;

    private String userName;

    private String realName;

    private String openId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorityFlag() {
        return authorityFlag;
    }

    public void setAuthorityFlag(String authorityFlag) {
        this.authorityFlag = authorityFlag == null ? null : authorityFlag.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }
}