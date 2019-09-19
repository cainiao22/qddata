package com.qding.bigdata.ds.model;

/**
 * @author yanpf
 * @date 2019/2/18 10:10
 * @description
 */

public class IOTDeviceActivityInfo {

    private String name;
    private long counts;
    private double increasement;

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

    public double getIncreasement() {
        return increasement;
    }

    public void setIncreasement(double increasement) {
        this.increasement = increasement;
    }
}
