package com.qding.bigdata.ds.model;

/**
 * Created by Administrator on 2019/3/5 0005.
 */
public class CsmRisks extends BaseModel {

    private String client_level;
    private String client_name;
    private String site;
    private String city;
    private String manager;
    private String risk_description;
    private String propose_time;
    private String programme;
    private String end_time;
    private String csm_status;
    private String adviser;
    private String remarks;

    public String getClient_level() {
        return client_level;
    }

    public void setClient_level(String client_level) {
        this.client_level = client_level;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getRisk_description() {
        return risk_description;
    }

    public void setRisk_description(String risk_description) {
        this.risk_description = risk_description;
    }

    public String getPropose_time() {
        return propose_time;
    }

    public void setPropose_time(String propose_time) {
        this.propose_time = propose_time;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCsm_status() {
        return csm_status;
    }

    public void setCsm_status(String csm_status) {
        this.csm_status = csm_status;
    }

    public String getAdviser() {
        return adviser;
    }

    public void setAdviser(String adviser) {
        this.adviser = adviser;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
