package com.qding.bigdata.ds.model;

/**
 * @author yanpf
 * @date 2019/2/15 16:18
 * @description
 */
public class RibaoActivityDetail {

    private String name;
    private long counts;
    private Double increasement;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
