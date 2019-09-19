package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.model.SchemeInfo;
import com.qding.bigdata.ds.model.TaskExecuteLog;

/**
 * Created by QDHL on 2017/9/11.
 */
public interface SyncDataService {
    public boolean executeTask(SchemeInfo info);
    SearchResult<TaskExecuteLog> queryList(TaskExecuteLog info);
}
