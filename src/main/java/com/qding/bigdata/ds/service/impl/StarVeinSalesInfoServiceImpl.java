package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.StarVeinCityPlanDao;
import com.qding.bigdata.ds.dao.StarVeinSalesInfoDao;
import com.qding.bigdata.ds.model.StarVeinSalesInfo;
import com.qding.bigdata.ds.service.StarVeinSalesInfoService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
@Service
public class StarVeinSalesInfoServiceImpl extends BaseServiceImpl<StarVeinSalesInfo>  implements StarVeinSalesInfoService {

//    @Autowired
    private StarVeinSalesInfoDao starVeinSalesInfoDao;
    @Resource(name = "dynamicSqlSession")
    public void initDao(SqlSession sqlSession) {
        this.starVeinSalesInfoDao = sqlSession.getMapper(StarVeinSalesInfoDao.class);
    }
    @Override
    public BaseDao<StarVeinSalesInfo> getDao() {
        return starVeinSalesInfoDao;
    }

}
