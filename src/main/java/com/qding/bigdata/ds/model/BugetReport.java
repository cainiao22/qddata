package com.qding.bigdata.ds.model;

import com.qding.bigdata.ds.model.BaseModel;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.List;

public class BugetReport extends BaseModel {

    //年份
    private String year;
    //一级业态
    private String first_format;
    //一级业态英文
    private String first_format_en;
    //二级业态
    private String second_format;
    //二级业态英文
    private String second_format_en;
    //三级业态
    private String third_format;
    //三级业态英文
    private String third_format_en;
    //月份
    private String month;
    //月归档收入
    @NumberFormat(pattern = "0.00")
    private BigDecimal shouru_month;
    //月归档毛利
    @NumberFormat(pattern = "0.00")
    private BigDecimal maoli_month ;
    //月GSV
    @NumberFormat(pattern = "0.00")
    private BigDecimal gsv_month ;
    //所属周
    private Integer week ;
    //周归档收入
    private BigDecimal shouru_week ;
    //周归档毛利
    private BigDecimal maoli_week;
    //周GSV
    private BigDecimal gsv_week ;

    private String data_dt;

    private String update_dt;
    //计算合计
    private String second_format_total;
    // 所有的二级业态
    private List<String> second_format_totalList;

    public String getUpdate_dt(){ return update_dt; }

    public void setUpdate_dt(String update_dt){this.update_dt = update_dt; }

    public String getData_dt() {
        return data_dt;
    }

    public void setData_dt(String data_dt) {
        this.data_dt = data_dt;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFirst_format() {
        return first_format;
    }

    public void setFirst_format(String first_format) {
        this.first_format = first_format;
    }

    public String getFirst_format_en() {
        return first_format_en;
    }

    public void setFirst_format_en(String first_format_en) {
        this.first_format_en = first_format_en;
    }

    public List<String> getSecond_format_totalList() {
        return second_format_totalList;
    }

    public void setSecond_format_totalList(List<String> second_format_totalList) {
        this.second_format_totalList = second_format_totalList;
    }

    public String getSecond_format() {
        return second_format;
    }

    public void setSecond_format(String second_format) {
        this.second_format = second_format;
    }

    public String getSecond_format_en() {
        return second_format_en;
    }

    public void setSecond_format_en(String second_format_en) {
        this.second_format_en = second_format_en;
    }

    public String getThird_format_en() {
        return third_format_en;
    }

    public void setThird_format_en(String third_format_en) {
        this.third_format_en = third_format_en;
    }

    public String getSecond_format_total() {
        return second_format_total;
    }

    public void setSecond_format_total(String second_format_total) {
        this.second_format_total = second_format_total;
    }

    public String getThird_format() {
        return third_format;
    }

    public void setThird_format(String third_format) {
        this.third_format = third_format;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getShouru_month() {
        return shouru_month;
    }

    public void setShouru_month(BigDecimal shouru_month) {
        this.shouru_month = shouru_month;
    }

    public BigDecimal getMaoli_month() {
        return maoli_month;
    }

    public void setMaoli_month(BigDecimal maoli_month) {
        this.maoli_month = maoli_month;
    }

    public BigDecimal getGsv_month() {
        return gsv_month;
    }

    public void setGsv_month(BigDecimal gsv_month) {
        this.gsv_month = gsv_month;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public BigDecimal getShouru_week() {
        return shouru_week;
    }

    public void setShouru_week(BigDecimal shouru_week) {
        this.shouru_week = shouru_week;
    }

    public BigDecimal getMaoli_week() {
        return maoli_week;
    }

    public void setMaoli_week(BigDecimal maoli_week) {
        this.maoli_week = maoli_week;
    }

    public BigDecimal getGsv_week() {
        return gsv_week;
    }

    public void setGsv_week(BigDecimal gsv_week) {
        this.gsv_week = gsv_week;
    }
}
