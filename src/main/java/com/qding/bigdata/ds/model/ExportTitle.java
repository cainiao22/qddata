package com.qding.bigdata.ds.model;

/**
 * @author yanpf
 * @date 2018/4/23 15:53
 * @description
 */
public class ExportTitle implements Comparable<ExportTitle> {

    private String name;

    private Integer order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public ExportTitle() {
    }

    public ExportTitle(String name, Integer order) {
        this.name = name;
        this.order = order;
    }

    @Override
    public int compareTo(ExportTitle o) {
        if(o == null){
            return 1;
        }
        if(this.getOrder() == null){
            return 0;
        }
        if(o.getOrder() == null){
            return 1;
        }

        return this.getOrder() - o.getOrder();
    }
}
