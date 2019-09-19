package com.qding.bigdata.ds.component;

import java.util.List;
import java.util.Map;

import com.qding.bigdata.ds.model.CommonDataSummary;
import org.springframework.stereotype.Component;

import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.model.CommonDataDetail;

/**
 * Created by yanpf on 2017/7/27.
 */

@Component
public class CommonDataSparkHandler implements CommonDataHandler<List<Map<String, Object>>> {

    @Override
    public Result<List<Map<String, Object>>> execute(CommonDataSummary commonDataSummary, Map<String, String> params) {
        return null;
    }

    @Override
    public void save(CommonDataDetail commonDataDetail) {

    }

    @Override
    public void update(CommonDataDetail commonDataDetail) {

    }

    @Override
    public Result checkParams(CommonDataDetail commonDataDetail) {
        return null;
    }

    @Override
    public void delete(Integer summaryId) {

    }

    @Override
    public void fillProperties(List<CommonDataDetail> detailList) {

    }
}
