package com.qding.bigdata.ds.enums;

/**
 * Created by QDHL on 2017/7/27.
 */


public enum UpdateType {
    INCRE(1,"incre"), FULL( 2,"full");

    int    index;
    String name;


    UpdateType(int index,String name) {
        this.index=index;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public int getIndex() {
        return index;
    }
}



