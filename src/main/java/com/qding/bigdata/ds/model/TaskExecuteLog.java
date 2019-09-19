package com.qding.bigdata.ds.model;

import java.util.Date;

/**
 * Created by QDHL on 2017/9/12.
 */
public class TaskExecuteLog extends BaseModel {
    private String taskId;
    private String taskType;
    private String log;
    private Date startTime;
    private Date endTime;
    private String tableId;
    private String syncDataStatus;
    public TaskExecuteLog(String taskId, String taskType, String log) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.log = log;
    }
    public TaskExecuteLog(Date startTime, Date endTime, String taskID, String tableId, String taskType, String log,String status) {
        this.startTime=startTime;
        this.endTime=endTime;
        this.taskId = taskID;
        this.tableId=tableId;
        this.taskType = taskType;
        this.log = log;
        this.syncDataStatus=status;
    }
    public TaskExecuteLog(){}

    public String getSyncDataStatus() {
        return syncDataStatus;
    }

    public void setSyncDataStatus(String syncDataStatus) {
        this.syncDataStatus = syncDataStatus;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }



    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
