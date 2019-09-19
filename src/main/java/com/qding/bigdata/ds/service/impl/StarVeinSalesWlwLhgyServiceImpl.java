package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.StarVeinSalesWlwLhdnkDao;
import com.qding.bigdata.ds.dao.StarVeinSalesWlwLhgyDao;
import com.qding.bigdata.ds.model.StarVeinSalesWlwLhgy;
import com.qding.bigdata.ds.service.StarVeinSalesWlwLhgyService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
@Service
public class StarVeinSalesWlwLhgyServiceImpl extends BaseServiceImpl<StarVeinSalesWlwLhgy> implements StarVeinSalesWlwLhgyService {

//    @Autowired
    private StarVeinSalesWlwLhgyDao starVeinSalesWlwLhgyDao;
    @Resource(name = "dynamicSqlSession")
    public void initDao(SqlSession sqlSession) {
        this.starVeinSalesWlwLhgyDao = sqlSession.getMapper(StarVeinSalesWlwLhgyDao.class);
    }
    @Override
    public BaseDao<StarVeinSalesWlwLhgy> getDao() {
        return starVeinSalesWlwLhgyDao;
    }

}
