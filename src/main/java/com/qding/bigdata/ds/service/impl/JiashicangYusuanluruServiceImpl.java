package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.BugetReportDao;
import com.qding.bigdata.ds.dao.JiashicangYusuanluruDao;
import com.qding.bigdata.ds.model.JiashicangYusuanluru;
import com.qding.bigdata.ds.service.JiashicangYusuanluruService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2019/2/14 0014.
 */
@Service
public class JiashicangYusuanluruServiceImpl extends BaseServiceImpl<JiashicangYusuanluru> implements  JiashicangYusuanluruService {


//    @Autowired
    private JiashicangYusuanluruDao jiashicangYusuanluruDao;
    @Resource(name = "dynamicSqlSession")
    public void initDao(SqlSession sqlSession) {
        this.jiashicangYusuanluruDao = sqlSession.getMapper(JiashicangYusuanluruDao.class);
    }
    @Override
    public BaseDao<JiashicangYusuanluru> getDao() {
        return jiashicangYusuanluruDao;
    }

}
