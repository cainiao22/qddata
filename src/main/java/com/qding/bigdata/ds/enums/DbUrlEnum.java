package com.qding.bigdata.ds.enums;

/**
 * Created by QDHL on 2018/1/22.
 */
public enum DbUrlEnum {

    MYSQL("mysql", "jdbc:mysql://url/dbname"),
    SQLSERVER("sqlserver", "jdbc:sqlserver://url;databaseName=dbname"),
    POSTGRESQL("postgresql", "jdbc:postgresql://url/dbname"),
    ORACLE("oracle", "jdbc:oracle:thin:@url:dbname");

    String dbType;
    String dbUrl;

    DbUrlEnum(String dbType, String dbUrl) {
        this.dbType = dbType;
        this.dbUrl = dbUrl;
    }

    public String getDbType() {
        return dbType;
    }


    public String getDbUrl() {
        return dbUrl;
    }

}
