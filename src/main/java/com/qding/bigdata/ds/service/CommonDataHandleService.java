package com.qding.bigdata.ds.service;

import java.util.List;
import java.util.Map;

import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.model.CommonDataDetail;

/**
 * Created by yanpf on 2017/7/27.
 * 通用数据接口处理类接口
 */
public interface CommonDataHandleService {

    /**
     * 根据CommonDataSummary表的id处理对应的查询任务
     * @param jobName
     * @return
     */
    Result<List<Map<String, Object>>> execute(String jobName, Map<String, String> params);

    Result save(CommonDataDetail commonDataDetail);

    Result update(CommonDataDetail commonDataDetail);

    SearchResult<CommonDataDetail> query(CommonDataDetail commonDataDetail);

    CommonDataDetail getById(Integer commonDataSummaryId);

    Result deleteById(Integer commonDataSummaryId);

	Result cleanCache(String name);
}
