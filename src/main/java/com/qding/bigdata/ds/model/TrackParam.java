package com.qding.bigdata.ds.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yanpf
 * @date 2018/7/13 17:05
 * @description
 */
public class TrackParam extends BaseModel {

    private Integer page;
    private Integer pageCount;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 模块名称
     */
    private String secondSource;

    public TrackParam() {
    }

    public TrackParam(Date startDate, Date endDate, String secondSource) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.secondSource = secondSource;
    }

    @Override
    public Integer getOffset() {
        if(page != null && pageCount != null) {
            return (page - 1) * pageCount;
        }else {
            return 0;
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSecondSource() {
        return secondSource;
    }

    public void setSecondSource(String secondSource) {
        this.secondSource = secondSource;
    }

    @Override
    public Integer getPage() {
        return page;
    }

    @Override
    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public Integer getPageCount() {
        return pageCount;
    }

    @Override
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
}
