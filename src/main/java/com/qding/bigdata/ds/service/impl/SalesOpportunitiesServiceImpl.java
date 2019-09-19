package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.JiashicangYusuanluruDao;
import com.qding.bigdata.ds.dao.SalesOpportunitiesDao;
import com.qding.bigdata.ds.dao.StarVeinCityPlanDao;
import com.qding.bigdata.ds.model.SalesOpportunities;
import com.qding.bigdata.ds.model.StarVeinCityPlan;
import com.qding.bigdata.ds.service.SalesOpportunitiesService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2019/2/21 0021.
 */
@Service
public class SalesOpportunitiesServiceImpl extends BaseServiceImpl<SalesOpportunities> implements SalesOpportunitiesService {

//    @Autowired
    private SalesOpportunitiesDao salesOpportunitiesDao;
    @Resource(name = "dynamicSqlSession")
    public void initDao(SqlSession sqlSession) {
        this.salesOpportunitiesDao = sqlSession.getMapper(SalesOpportunitiesDao.class);
    }
    @Override
    public BaseDao<SalesOpportunities> getDao() {
        return salesOpportunitiesDao;
    }

}
