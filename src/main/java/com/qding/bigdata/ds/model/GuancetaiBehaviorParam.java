package com.qding.bigdata.ds.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "GuancetaiBehaviorParam", description = "观测台行为路径入参")
@Data
public class GuancetaiBehaviorParam {
    @ApiModelProperty(value = "端 all|iOS|Android", example = "all")
    public String deviceType;
    @ApiModelProperty(value = "开始时间", example = "2019-04-15")
    public String startDate;
    @ApiModelProperty(value = "结束时间", example = "2019-04-15")
    public String endDate;
    @ApiModelProperty(value = "分析类型 pageAnalysis | eventAnalysis", example = "pageAnalysis")
    public String analysisType;
    @ApiModelProperty(value = "指定结束页面", example = "homePage")
    public String endPi;
    @ApiModelProperty(value = "当前事件IDs event_opendoor_promotion,event_opendoor_pass_bottomTabClick 页面IDs Opendoor,homePage", example = "Opendoor,homePage|event_opendoor_promotion,event_opendoor_pass_bottomTabClick")
    public String ids;
    @ApiModelProperty(value = "业态ID", example = "0 千丁APP | 1丁管家 | 2 丁老板 | 3 千丁云 PC 端")
    private Long productId;
}
