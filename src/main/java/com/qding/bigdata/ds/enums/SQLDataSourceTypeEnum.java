package com.qding.bigdata.ds.enums;

/**
 * Created by yanpf on 2017/10/13.
 */
public enum SQLDataSourceTypeEnum {
    GPSQL("SQL"),DRUIDSQL("DRUID"),ESSQL("ES");

    public final String name;

    SQLDataSourceTypeEnum(String name){
        this.name = name;
    }
}
