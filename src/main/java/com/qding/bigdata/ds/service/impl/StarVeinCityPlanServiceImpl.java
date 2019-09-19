package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.SalesOpportunitiesDao;
import com.qding.bigdata.ds.dao.StarVeinCityPlanDao;
import com.qding.bigdata.ds.model.StarVeinCityPlan;
import com.qding.bigdata.ds.service.StarVeinCityPlanService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2019/2/15 0015.
 */
@Service
public class StarVeinCityPlanServiceImpl extends BaseServiceImpl<StarVeinCityPlan> implements StarVeinCityPlanService {

//    @Autowired
    private StarVeinCityPlanDao starVeinCityPlanDao;
    @Resource(name = "dynamicSqlSession")
    public void initDao(SqlSession sqlSession) {
        this.starVeinCityPlanDao = sqlSession.getMapper(StarVeinCityPlanDao.class);
    }
    @Override
    public BaseDao<StarVeinCityPlan> getDao() {
        return starVeinCityPlanDao;
    }

}
