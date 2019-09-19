package com.qding.bigdata.ds.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "GuancetaiFunnelParam", description = "观测台漏斗分析入参")
@Data
public class GuancetaiFunnelParam {
    @ApiModelProperty(value = "端 all|iOS|Android", example = "all")
    public String deviceType;
    @ApiModelProperty(value = "开始时间", example = "2019-01-16")
    public String startDate;
    @ApiModelProperty(value = "结束时间", example = "2019-01-23")
    public String endDate;
    @ApiModelProperty(value = "漏斗ID", example = "Opendoor,homePage,...")
    public String funnelPath;

}
