package com.qding.bigdata.ds.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.CorpusKeywordsDao;
import com.qding.bigdata.ds.model.CorpusKeywords;
import com.qding.bigdata.ds.service.CorpusKeywordsService;

import javax.annotation.Resource;

@Service
public class CorpusKeywordsServiceImpl extends BaseServiceImpl<CorpusKeywords> implements CorpusKeywordsService {

	private CorpusKeywordsDao corpusKeywordsDao;

	@Resource(name = "sqlSessionExecutor")
	public void setCorpusKeywordsDao(SqlSession sqlSession) {
		this.corpusKeywordsDao = sqlSession.getMapper(CorpusKeywordsDao.class);
	}

	@Override
	public BaseDao<CorpusKeywords> getDao() {
		return corpusKeywordsDao;
	}

}
