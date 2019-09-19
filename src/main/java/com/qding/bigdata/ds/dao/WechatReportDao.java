package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.WechatReport;

import java.util.List;

/**
 * Created by yanpf on 2017/9/18.
 */
public interface WechatReportDao {

    List<WechatReport> query(WechatReport param);

    int queryCount(WechatReport param);
}
