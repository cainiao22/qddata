package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.dao.WechatReportDao;
import com.qding.bigdata.ds.model.WechatReport;
import com.qding.bigdata.ds.service.WechatReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yanpf on 2017/9/18.
 */

@Service
public class WechatReportServiceImpl implements WechatReportService {

    @Autowired
    WechatReportDao wechatReportDao;

    @Override
    public SearchResult<WechatReport> query(WechatReport param){
        List<WechatReport> reportList = wechatReportDao.query(param);
        if(reportList != null){
            for (WechatReport wechatReport : reportList) {
                if(wechatReport.getDt() != null && !wechatReport.getDt().equals("")){
                    wechatReport.setSendStatus("SUCCESS");
                }else{
                    wechatReport.setDt(param.getDt());
                    wechatReport.setSendStatus("FAILED");
                }
            }
        }
        int count = wechatReportDao.queryCount(param);
        SearchResult<WechatReport> result = new SearchResult<WechatReport>();
        result.setPageCount(param.getPageCount());
        result.setCurrentPage(param.getPage());
        result.setTotal(count);
        result.setRows(reportList);
        return  result;
    }
}
