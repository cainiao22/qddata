package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.StarVeinSalesInfoDao;
import com.qding.bigdata.ds.dao.StarVeinSalesWlwLhgyDao;
import com.qding.bigdata.ds.dao.StarVeinSalesWlwWtDao;
import com.qding.bigdata.ds.model.StarVeinSalesInfo;
import com.qding.bigdata.ds.model.StarVeinSalesWlwWt;
import com.qding.bigdata.ds.service.StarVeinSalesWlwWtService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
@Service
public class StarVeinSalesWlwWtServiceImpl extends BaseServiceImpl<StarVeinSalesWlwWt>  implements StarVeinSalesWlwWtService {
    @Autowired
    private StarVeinSalesWlwWtDao starVeinSalesWlwWtDao;
    @Resource(name = "dynamicSqlSession")
    public void initDao(SqlSession sqlSession) {
        this.starVeinSalesWlwWtDao = sqlSession.getMapper(StarVeinSalesWlwWtDao.class);
    }
    @Override
    public BaseDao<StarVeinSalesWlwWt> getDao() {
        return starVeinSalesWlwWtDao;
    }

}
