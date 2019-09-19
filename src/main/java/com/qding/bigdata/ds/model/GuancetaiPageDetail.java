package com.qding.bigdata.ds.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yanpf
 * @date 2018/12/28 11:16
 * @description
 */

@ApiModel(value = "GuancetaiPageDetail", description = "观测台页面列表详情")
public class GuancetaiPageDetail implements Comparable<GuancetaiPageDetail> {

    @ApiModelProperty(value = "时间", example = "2018-01-29")
    protected String time;

    @ApiModelProperty(value = "页面ID", example = "homePage")
    protected String pageId;

    @ApiModelProperty(value = "页面名称", example = "首页")
    protected String pageName;

    @ApiModelProperty(value = "设备类型 (Android | iOS)", example = "Android")
    protected String source;

    @ApiModelProperty(value = "访问次数", example = "9999")
    protected Long visitCount;

    @ApiModelProperty(value = "访问人数", example = "9999")
    protected Long visitUsers;

    @ApiModelProperty(value = "次均停留时长", example = "4")
    protected Double onlineTimeEveryTime;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

    public Long getVisitUsers() {
        return visitUsers;
    }

    public void setVisitUsers(Long visitUsers) {
        this.visitUsers = visitUsers;
    }

    public Double getOnlineTimeEveryTime() {
        return onlineTimeEveryTime;
    }

    public void setOnlineTimeEveryTime(Double onlineTimeEveryTime) {
        this.onlineTimeEveryTime = onlineTimeEveryTime;
    }

    @Override
    public int compareTo(GuancetaiPageDetail o) {
        if(compare(time, o.time) != 0){
            return compare(time, o.time);
        }
        if(compare(pageId, o.pageId) != 0){
            return compare(pageId, o.pageId);
        }
        if(compare(source, o.source) != 0){
            return compare(source, o.source);
        }
        return 0;
    }

    public int compare(Comparable o1, Comparable o2){
        if(o1 == null && o2 == null){
            return 0;
        }else if(o1 == null){
            return -1;
        }else if(o2 == null){
            return 1;
        }else {
            return o1.compareTo(o2);
        }
    }
}
