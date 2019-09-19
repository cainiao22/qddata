package com.qding.bigdata.ds.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * startDate和endDate传同一天
 * @author: Yanxiaowei
 * @date: 2019/8/21 下午7:01
 */
@Data
@ApiModel(value = "GCTParamValueV2Param", description = "参数分析入参")
public class GCTParamValueV2Param extends GCTBaseV2Param{
    @ApiModelProperty(value = "查询维度", example = "page|event")
    private String index;
    @ApiModelProperty(value = "参数", example = "platform_channel")
    private String paramKey;
    @ApiModelProperty(value = "参数值", example = "1")
    private String paramValue;
}
