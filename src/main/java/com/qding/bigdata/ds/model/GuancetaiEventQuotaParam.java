package com.qding.bigdata.ds.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yanpf
 * @date 2018/12/28 9:55
 * @description
 */

@ApiModel(value = "GuancetaiEventQuotaParam", description = "观测台事件查询的入参")
public class GuancetaiEventQuotaParam extends GuancetaiQuotaParam {

    @ApiModelProperty(value = "事件id", example = "event_opendoor_pass_bottomTabClick")
    private String eventId;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
