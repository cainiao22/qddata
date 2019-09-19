package com.qding.bigdata.ds.model;

public class SchemeInfo extends BaseModel {
    /**
     * \数据库用户名
     */

    private String user;
    /**
     * \数据库用密码
     */
    private String password;
    /**
     * \数据库用的ip+port
     */
    private String ip;
    /**
     * 数据库的库名
     */
    private String dbName;
    /**
     * 数据库的表名
     */
    private String tableName;
    /**
     * 数据库中文明
     */
     private String alias;
    /**
     * 记录拥有者。
     */
    private String owner;
    /**
     * 数据更新类型  {增量更新 1 incre，2 全量更新 full}
     */
    private Integer updateType = 2;

    private String message;

    /***
     * 同步数据状态
     * @return
     */
    private String syncDataStatus;
    /**
     * 同步任务的ID
     */
    private String taskId;



    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSyncDataStatus() {
        return syncDataStatus;
    }

    public void setSyncDataStatus(String syncDataStatus) {
        this.syncDataStatus = syncDataStatus;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }
}


