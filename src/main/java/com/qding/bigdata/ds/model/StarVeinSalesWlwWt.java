package com.qding.bigdata.ds.model;

/**
 * Created by Administrator on 2019/2/13 0013.
 */
public class StarVeinSalesWlwWt extends BaseModel {

    private String dim_type;
    private String person;
    private String client_name;
    private String plan_amount;
    private String main_product;
    private String project_stage;
    private String plan_date;
    private String shipment_info;
    private String remarks;

    public String getDim_type() {
        return dim_type;
    }

    public void setDim_type(String dim_type) {
        this.dim_type = dim_type;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getPlan_amount() {
        return plan_amount;
    }

    public void setPlan_amount(String plan_amount) {
        this.plan_amount = plan_amount;
    }

    public String getMain_product() {
        return main_product;
    }

    public void setMain_product(String main_product) {
        this.main_product = main_product;
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

    public String getShipment_info() {
        return shipment_info;
    }

    public void setShipment_info(String shipment_info) {
        this.shipment_info = shipment_info;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
