package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.BugetReportDao;
import com.qding.bigdata.ds.dao.CsmRisksDao;
import com.qding.bigdata.ds.model.BugetReport;
import com.qding.bigdata.ds.service.BugetReportService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BugetReportServiceImpl extends BaseServiceImpl<BugetReport> implements BugetReportService {

//    @Autowired
    private BugetReportDao bugetReportDao;

    @Resource(name = "dynamicSqlSession")
    public void initDao(SqlSession sqlSession) {
        this.bugetReportDao = sqlSession.getMapper(BugetReportDao.class);
    }
    @Override
    public BaseDao<BugetReport> getDao() {
        return bugetReportDao;
    }

    @Override
    public List<BugetReport> getTotal(BugetReport bugetReport) {
        return bugetReportDao.getTotal(bugetReport);
    }
}
