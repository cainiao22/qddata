package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.model.TaskExecuteLog;

/**
 * Created by QDHL on 2017/9/13.
 */
public interface TaskExecuteLogService extends  BaseService<TaskExecuteLog> {
    SearchResult<TaskExecuteLog> queryList(TaskExecuteLog param);
}
