package com.qding.bigdata.ds.model;

/**
 * Created by Administrator on 2019/2/13 0013.
 */
public class StarVeinCityPlan extends BaseModel {

    private String year;
    private String dt;
    private String region_name;
    private String region_id;
    private String yewuxian_name;
    private String yewuxian_id;
    private String all_shouru;
    private String all_lirun;
    private String all_chuhuo;
    private String all_huikuan;
    private String all_gsv;
    private Integer sortno;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAll_gsv() {
        return all_gsv;
    }

    public void setAll_gsv(String all_gsv) {
        this.all_gsv = all_gsv;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getYewuxian_name() {
        return yewuxian_name;
    }

    public void setYewuxian_name(String yewuxian_name) {
        this.yewuxian_name = yewuxian_name;
    }

    public String getYewuxian_id() {
        return yewuxian_id;
    }

    public void setYewuxian_id(String yewuxian_id) {
        this.yewuxian_id = yewuxian_id;
    }

    public String getAll_shouru() {
        return all_shouru;
    }

    public void setAll_shouru(String all_shouru) {
        this.all_shouru = all_shouru;
    }

    public String getAll_lirun() {
        return all_lirun;
    }

    public void setAll_lirun(String all_lirun) {
        this.all_lirun = all_lirun;
    }

    public String getAll_chuhuo() {
        return all_chuhuo;
    }

    public void setAll_chuhuo(String all_chuhuo) {
        this.all_chuhuo = all_chuhuo;
    }

    public String getAll_huikuan() {
        return all_huikuan;
    }

    public void setAll_huikuan(String all_huikuan) {
        this.all_huikuan = all_huikuan;
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }
}
