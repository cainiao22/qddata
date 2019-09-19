package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.TaskExecuteLogDao;
import com.qding.bigdata.ds.model.TaskExecuteLog;
import com.qding.bigdata.ds.service.TaskExecuteLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by QDHL on 2017/9/13.
 */
@Service
public class TaskExecuteLogServiceImpl extends BaseServiceImpl<TaskExecuteLog> implements TaskExecuteLogService {
    @Autowired
    private TaskExecuteLogDao taskExecuteLogDao;

    @Override
    public SearchResult<TaskExecuteLog> queryList(TaskExecuteLog param){
        SearchResult<TaskExecuteLog> result = new SearchResult<TaskExecuteLog>();
        result.setCurrentPage(param.getPage());
        result.setPageCount(param.getPageCount());
        param.setSortAndDesc("desc");
        List<TaskExecuteLog> list = taskExecuteLogDao.list(param);
        result.setRows(list);
        int totalCount = taskExecuteLogDao.count(param);
        result.setTotal(totalCount);
        return result;
    }
    @Override
    public BaseDao<TaskExecuteLog> getDao() {
        return taskExecuteLogDao;
    }
}
