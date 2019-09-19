package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.CsmRisksDao;
import com.qding.bigdata.ds.model.CsmRisks;
import com.qding.bigdata.ds.service.CsmRisksService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2019/3/5 0005.
 */
@Service
public class CsmRisksServiceImpl extends BaseServiceImpl<CsmRisks> implements CsmRisksService {

    //@Autowired
    private CsmRisksDao csmRisksDao;

    @Resource(name = "dynamicSqlSession")
    public void initDao(SqlSession sqlSession) {
        this.csmRisksDao = sqlSession.getMapper(CsmRisksDao.class);
    }

    @Override
    public BaseDao<CsmRisks> getDao() {
        return csmRisksDao;
    }

}
