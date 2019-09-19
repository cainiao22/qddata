package com.qding.bigdata.ds.component;


import java.util.List;
import java.util.Map;

import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.model.CommonDataDetail;
import com.qding.bigdata.ds.model.CommonDataSummary;

/**
 * Created by yanpf on 2017/7/27.
 */
public interface CommonDataHandler<T> {
    Result<T> execute(CommonDataSummary commonDataSummary, Map<String, String> params) throws Exception;
    void save(CommonDataDetail commonDataDetail);
    void update(CommonDataDetail commonDataDetail);

    /**
     * 校验参数是否合理
     * @param commonDataDetail
     * @return
     */
    Result checkParams(CommonDataDetail commonDataDetail);

    void delete(Integer summaryId);

    /**
     * 填充属性，传进来的是全量，各实现类只需要填充自己关心的部分
     * @param detailList
     */
    void fillProperties(final List<CommonDataDetail> detailList);
}
