package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.StarVeinSalesTargetImportDao;
import com.qding.bigdata.ds.dao.StarVeinSalesWlwLhdnkDao;
import com.qding.bigdata.ds.dao.StarVeinSalesWlwLhgyDao;
import com.qding.bigdata.ds.model.StarVeinSalesWlwLhdnk;
import com.qding.bigdata.ds.model.StarVeinSalesWlwLhgy;
import com.qding.bigdata.ds.service.StarVeinSalesWlwLhdnkService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
@Service
public class StarVeinSalesWlwLhdnkServiceImpl extends BaseServiceImpl<StarVeinSalesWlwLhdnk> implements StarVeinSalesWlwLhdnkService {

//    @Autowired
    private StarVeinSalesWlwLhdnkDao starVeinSalesWlwLhdnkDao;
    @Resource(name = "dynamicSqlSession")
    public void initDao(SqlSession sqlSession) {
        this.starVeinSalesWlwLhdnkDao = sqlSession.getMapper(StarVeinSalesWlwLhdnkDao.class);
    }
    @Override
    public BaseDao<StarVeinSalesWlwLhdnk> getDao() {
        return starVeinSalesWlwLhdnkDao;
    }

}
