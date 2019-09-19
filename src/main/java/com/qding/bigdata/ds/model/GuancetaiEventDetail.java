package com.qding.bigdata.ds.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yanpf
 * @date 2018/12/28 11:23
 * @description
 */

@ApiModel(value = "GuancetaiPageDetail", description = "观测台事件列表详情")
public class GuancetaiEventDetail extends GuancetaiPageDetail {

    @ApiModelProperty(value = "事件id", example = "event_home_bottomTabClick")
    private String eventId;

    @ApiModelProperty(value = "事件名称", example = "底部首页导航栏点击")
    private String eventName;

    @ApiModelProperty(value = "事件类型", example = "点击")
    private String eventType;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public int compareTo(GuancetaiPageDetail o) {

        if(compare(time, o.time) != 0){
            return compare(time, o.time);
        }

        if(o instanceof GuancetaiEventDetail){
            if(compare(eventId, ((GuancetaiEventDetail) o).eventId) != 0){
                return compare(eventId, ((GuancetaiEventDetail) o).eventId);
            }
        }

        if(compare(pageId, o.pageId) != 0){
            return compare(pageId, o.pageId);
        }
        if(compare(source, o.source) != 0){
            return compare(source, o.source);
        }
        return 0;
    }
}
