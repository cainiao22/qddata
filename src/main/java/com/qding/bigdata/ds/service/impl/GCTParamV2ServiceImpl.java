package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.GCTParamV2Dao;
import com.qding.bigdata.ds.model.GCTParamValueV2Param;
import com.qding.bigdata.ds.service.GCTParamV2Service;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/21 下午6:59
 */
@Service
public class GCTParamV2ServiceImpl implements GCTParamV2Service {
    GCTParamV2Dao gctParamV2Dao;
    @Resource(name = "esSqlSession")
    public void initExecutorDao(SqlSession sqlSession) {
        this.gctParamV2Dao = sqlSession.getMapper(GCTParamV2Dao.class);
    }
    @Override
    public List<Map<String, Object>> getParamAnalysis(GCTParamValueV2Param param) {
        List<Map<String, Object>> valueList = gctParamV2Dao.getParamKey(param);
        return valueList;
    }

    @Override
    public List<Map<String, Object>> getParamValueAnalysis(GCTParamValueV2Param param) {
        GCTParamValueV2Param newparam = GCTParamValueV2Param.getAvailableDayInMonth(param);
        newparam.setDateType("day");
        List<Map<String, Object>> valueList = gctParamV2Dao.getParamKeyList(newparam);
        return valueList;
    }
}
