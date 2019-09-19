package com.qding.bigdata.ds.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author yanpf
 * @date 2018/12/26 11:50
 * @description
 */

@ApiModel(value = "GuancetaiQuotaInfo", description = "观测台指标信息")
public class GuancetaiQuotaInfo {

    @ApiModelProperty(value = "当前指标的值")
    private Double current;
    @ApiModelProperty(value = "上期指标的值")
    private Double last;
    @ApiModelProperty(value = "环比")
    private Double increasement;

    @ApiModelProperty(value = "标题")
    private String title;

    private String companyId;

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }

    public Double getIncreasement() {
        return increasement;
    }

    public void setIncreasement(Double increasement) {
        BigDecimal decimal = new BigDecimal(increasement);
        this.increasement = decimal.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIncreasement(){
        if(current != null && last != null){
            if(current == 0){
                this.increasement = -100.0;
            }else if(last == 0){
                this.increasement = 100.00d;
            }else{
                this.increasement = Math.round((current - last) / last * 10000) / 100.0;
            }
        }
    }

    public void setIncreasementNoPercent(){
        if(current != null && last != null){
            if(current == 0 && last == 0){
                this.increasement = Double.NaN;
            }else if(current == 0){
                this.increasement = -1d;
            }else if(last == 0){
                this.increasement = Double.POSITIVE_INFINITY;
            }else{
                BigDecimal decimal = new BigDecimal((current - last) / last);
                this.increasement = decimal.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        }
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
