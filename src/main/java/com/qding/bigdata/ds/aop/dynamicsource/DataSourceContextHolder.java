package com.qding.bigdata.ds.aop.dynamicsource;

/**
 * @author yanpf
 * @date 2019/5/14 10:57
 * @description
 */
public class DataSourceContextHolder {

    private static ThreadLocal<String> dataSourceHolder = ThreadLocal.withInitial(() -> null);

    public static void setDataSource(String  dataSource){
        dataSourceHolder.set(dataSource);
    }

    public static String getDataSource(){
        return dataSourceHolder.get();
    }

    public static void clearDataSource(){
        dataSourceHolder.remove();
    }
}
