package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.model.WechatReport;

/**
 * Created by yanpf on 2017/9/18.
 */

public interface WechatReportService {

    SearchResult<WechatReport> query(WechatReport param);
}
