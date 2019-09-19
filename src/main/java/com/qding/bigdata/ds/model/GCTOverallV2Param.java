package com.qding.bigdata.ds.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author: Yanxiaowei
 * @date: 2019/8/13 下午8:23
 */
@Data
@ApiModel(value = "GCTOverallV2Param", description = "整体分析入参")
public class GCTOverallV2Param extends GCTBaseV2Param {
    @ApiModelProperty(value = "前端操作名称", example = "ding_reg_new|active|app_startup|per_start|per_often")
    private String action;
    @ApiModelProperty(hidden = true)
    private String esIndex;
}
