package com.qding.bigdata.ds.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yanpf
 * @date 2018/12/26 18:16
 * @description
 */

@ApiModel(value = "GuancetaiQuotaTrendItem", description = "观测台趋势图指标信息")
public class GuancetaiQuotaTrendItem {

    @ApiModelProperty(value = "x坐标")
    private String key;
    @ApiModelProperty(value = "y坐标")
    private Double value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
