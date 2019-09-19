package com.qding.bigdata.ds.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yanpf
 * @date 2018/12/27 17:21
 * @description
 */

@ApiModel(value = "GuancetaiPageQuotaParam", description = "观测台页面相关查询的入参")
public class GuancetaiPageQuotaParam extends GuancetaiQuotaParam {

    @ApiModelProperty(value = "页面id", example = "Opendoor")
    private String pageId;

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }
}
