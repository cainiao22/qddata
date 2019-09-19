package com.qding.bigdata.ds.VO;

public class PathNodeVO {
    public String id;
    public String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public PathNodeVO() {
    }
    public PathNodeVO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
