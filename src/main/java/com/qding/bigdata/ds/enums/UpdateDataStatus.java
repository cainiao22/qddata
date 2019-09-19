package com.qding.bigdata.ds.enums;

/**
 * Created by QDHL on 2017/7/27.
 */


public enum UpdateDataStatus {
    running(1,"拉取数据中"), success( 2,"拉取数据成功"),fail(3,"拉取数据失败");

    int    index;
    String value;


    UpdateDataStatus(int index, String value) {
        this.index=index;
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    public int getIndex() {
        return index;
    }
}



