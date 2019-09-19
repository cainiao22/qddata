package com.qding.bigdata.ds.enums;

/**
 * Created by QDHL on 2017/9/12.
 */
public enum TaskType{
    KETTLE(1,"kettle");

    int    index;
    String  value;
    TaskType(int index, String value) {
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

