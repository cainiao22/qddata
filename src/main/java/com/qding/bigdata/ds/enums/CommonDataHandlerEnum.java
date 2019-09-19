package com.qding.bigdata.ds.enums;

/**
 * Created by yanpf on 2017/7/27.
 */
public enum CommonDataHandlerEnum {

    SQL("SQL", "commonDataSqlHandler"),
    DRUID("DRUID", "commonDataSqlHandler"),
    REDIS("REDIS", "commonDataRedisHandler"),
    SPARK("SPARK", "commonDataSparkHandler"),
	ES("ES", "commonDataSqlHandler");

    String dataSource;
    String handlerName;

    CommonDataHandlerEnum(String dataSource, String handlerName) {
        this.dataSource = dataSource;
        this.handlerName = handlerName;
    }

    public String getDataSource() {
        return dataSource;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public static String getHandler(String dataSource){
        for (CommonDataHandlerEnum handlerEnum : CommonDataHandlerEnum.values()) {
            if(handlerEnum.getDataSource().equals(dataSource)){
                return handlerEnum.getHandlerName();
            }
        }
        return null;
    }

    public static String getDataSource(String handlerName){
        for (CommonDataHandlerEnum handlerEnum : CommonDataHandlerEnum.values()) {
            if(handlerEnum.getHandlerName().equals(handlerName)){
                return handlerEnum.getDataSource();
            }
        }
        return null;
    }
}
