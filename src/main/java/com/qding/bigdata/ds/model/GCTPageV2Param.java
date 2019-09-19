package com.qding.bigdata.ds.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/13 下午8:23
 */
@Data
@ApiModel(value = "GCTPageV2Param", description = "页面分析入参")
public class GCTPageV2Param extends GCTBaseV2Param {
    @ApiModelProperty(value = "前端操作名称", example = "pv|uv|pr_pv_time|pr_uv_time")
    private String action;
    @ApiModelProperty(hidden = true)
    private String esIndex;
}
