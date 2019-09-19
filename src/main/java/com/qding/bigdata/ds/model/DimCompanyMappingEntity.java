package com.qding.bigdata.ds.model;

public class DimCompanyMappingEntity {
    private Integer id;

    private String companyId;

    private String companyName;

    private String qyrCompanyBinaryList;

    private Integer qyrCompanyTenCode;

    private String qyrCompanyNameList;

    private String qyrCompanyTenList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getQyrCompanyBinaryList() {
        return qyrCompanyBinaryList;
    }

    public void setQyrCompanyBinaryList(String qyrCompanyBinaryList) {
        this.qyrCompanyBinaryList = qyrCompanyBinaryList == null ? null : qyrCompanyBinaryList.trim();
    }

    public Integer getQyrCompanyTenCode() {
        return qyrCompanyTenCode;
    }

    public void setQyrCompanyTenCode(Integer qyrCompanyTenCode) {
        this.qyrCompanyTenCode = qyrCompanyTenCode;
    }

    public String getQyrCompanyNameList() {
        return qyrCompanyNameList;
    }

    public void setQyrCompanyNameList(String qyrCompanyNameList) {
        this.qyrCompanyNameList = qyrCompanyNameList == null ? null : qyrCompanyNameList.trim();
    }

    public String getQyrCompanyTenList() {
        return qyrCompanyTenList;
    }

    public void setQyrCompanyTenList(String qyrCompanyTenList) {
        this.qyrCompanyTenList = qyrCompanyTenList == null ? null : qyrCompanyTenList.trim();
    }
}