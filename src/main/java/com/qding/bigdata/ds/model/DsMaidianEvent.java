package com.qding.bigdata.ds.model;

import java.util.List;

public class DsMaidianEvent {
    private Long id;

    private String code;

    private String name;

    private Long productId;

    private Long businessId;

    private Integer status;

    private List<DsMaidianParam> paramList;

    private Long[] paramIds;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<DsMaidianParam> getParamList() {
        return paramList;
    }

    public void setParamList(List<DsMaidianParam> paramList) {
        this.paramList = paramList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long[] getParamIds() {
        return paramIds;
    }

    public void setParamIds(Long[] paramIds) {
        this.paramIds = paramIds;
    }
}