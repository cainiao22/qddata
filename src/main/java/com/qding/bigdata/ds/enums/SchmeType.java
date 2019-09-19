package com.qding.bigdata.ds.enums;

/**
 * Created by QDHL on 2017/7/27.
 */


public enum SchmeType {
    STG("stg","stg.stg_"), ODS("ods","ods.ods_"), ALL("all","all");
    String name;
    String value;

    SchmeType(String name,String value) {
        this.value=value;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }
}



