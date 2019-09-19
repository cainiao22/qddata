package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.StarVeinSalesInfoDao;
import com.qding.bigdata.ds.dao.StarVeinSalesTargetImportDao;
import com.qding.bigdata.ds.model.StarVeinSalesTargetImport;
import com.qding.bigdata.ds.service.StarVeinSalesTargetService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2019/3/12 0012.
 */
@Service
public class StarVeinSalesTargetServiceImpl extends BaseServiceImpl<StarVeinSalesTargetImport>  implements StarVeinSalesTargetService {

//    @Autowired
    private StarVeinSalesTargetImportDao starVeinSalesTargetImportDao;
    @Resource(name = "dynamicSqlSession")
    public void initDao(SqlSession sqlSession) {
        this.starVeinSalesTargetImportDao = sqlSession.getMapper(StarVeinSalesTargetImportDao.class);
    }
    @Override
    public BaseDao<StarVeinSalesTargetImport> getDao() {
        return starVeinSalesTargetImportDao;
    }

}
