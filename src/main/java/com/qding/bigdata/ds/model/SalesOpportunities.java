package com.qding.bigdata.ds.model;

/**
 * Created by Administrator on 2019/2/20 0020.
 */
public class SalesOpportunities extends BaseModel {

     private String dt;
     private String dimDepart;
     private String opportunityName;
     private String ownerId;
     private String accountId;
     private String money;
     private String customItem188__c;
     private String closeDate;
     private String saleStageId;
     private String createdAt;
     private String updatedAt;
     private String comment;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getDimDepart() {
        return dimDepart;
    }

    public void setDimDepart(String dimDepart) {
        this.dimDepart = dimDepart;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCustomItem188__c() {
        return customItem188__c;
    }

    public void setCustomItem188__c(String customItem188__c) {
        this.customItem188__c = customItem188__c;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getSaleStageId() {
        return saleStageId;
    }

    public void setSaleStageId(String saleStageId) {
        this.saleStageId = saleStageId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
