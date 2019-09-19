package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.PropertyLevelDao;
import com.qding.bigdata.ds.dao.StarVeinSalesWlwWtDao;
import com.qding.bigdata.ds.model.PropertyLevel;
import com.qding.bigdata.ds.service.PropertyLevelService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2019/3/5 0005.
 */
@Service
public class PropertyLevelServiceImpl extends BaseServiceImpl<PropertyLevel> implements PropertyLevelService {

//    @Autowired
    private PropertyLevelDao propertyLevelDao;
    @Resource(name = "dynamicSqlSession")
    public void initDao(SqlSession sqlSession) {
        this.propertyLevelDao = sqlSession.getMapper(PropertyLevelDao.class);
    }
    @Override
    public BaseDao<PropertyLevel> getDao() {
        return propertyLevelDao;
    }

}
