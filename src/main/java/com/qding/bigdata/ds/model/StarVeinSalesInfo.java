package com.qding.bigdata.ds.model;

/**
 * Created by Administrator on 2019/2/13 0013.
 */
public class StarVeinSalesInfo extends BaseModel {

    private String dim_type;
    private String city;
    private String person;
    private String client_name;
    private String plan_amount;
    private String plan_product;
    private String project_stage;
    private String plan_date;
    private String plan_text;
    private String remarks;

    public String getDim_type() {
        return dim_type;
    }

    public void setDim_type(String dim_type) {
        this.dim_type = dim_type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPlan_amount() {
        return plan_amount;
    }

    public void setPlan_amount(String plan_amount) {
        this.plan_amount = plan_amount;
    }

    public String getPlan_product() {
        return plan_product;
    }

    public void setPlan_product(String plan_product) {
        this.plan_product = plan_product;
    }

    public String getProject_stage() {
        return project_stage;
    }

    public void setProject_stage(String project_stage) {
        this.project_stage = project_stage;
    }

    public String getPlan_date() {
        return plan_date;
    }

    public void setPlan_date(String plan_date) {
        this.plan_date = plan_date;
    }

    public String getPlan_text() {
        return plan_text;
    }

    public void setPlan_text(String plan_text) {
        this.plan_text = plan_text;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }
}
