package com.qding.bigdata.ds.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * 行为路径
 */
@Data
@AllArgsConstructor
@ApiModel(value = "AnalysisVO", description = "行为路径")
public class AnalysisVO {
    @ApiModelProperty(value = "页面名称", example = "首页")
    public String name;
    @ApiModelProperty(value = "页面ID", example = "homePage")
    public String id;
    @ApiModelProperty(value = "访问值PV/UV", example = "21")
    public Long number;


}
