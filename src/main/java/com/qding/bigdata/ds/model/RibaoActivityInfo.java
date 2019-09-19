package com.qding.bigdata.ds.model;
import java.util.List;

/**
 * @author yanpf
 * @date 2019/2/18 10:14
 * @description
 */
public class RibaoActivityInfo {

    private long counts;

    private Double increasement;

    private List<RibaoActivityDetail> businessDataList;
    private List<RibaoActivityDetail> cityDataList;

    public long getCounts() {
        return counts;
    }

    public void setCounts(long counts) {
        this.counts = counts;
    }

    public Double getIncreasement() {
        return increasement;
    }

    public void setIncreasement(Double increasement) {
        this.increasement = increasement;
    }

    public List<RibaoActivityDetail> getBusinessDataList() {
        return businessDataList;
    }

    public void setBusinessDataList(List<RibaoActivityDetail> businessDataList) {
        this.businessDataList = businessDataList;
    }

    public List<RibaoActivityDetail> getCityDataList() {
        return cityDataList;
    }

    public void setCityDataList(List<RibaoActivityDetail> cityDataList) {
        this.cityDataList = cityDataList;
    }
}
