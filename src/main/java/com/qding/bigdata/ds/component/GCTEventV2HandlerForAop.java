package com.qding.bigdata.ds.component;

import com.qding.bigdata.ds.annotation.FilterResultAnno;
import com.qding.bigdata.ds.dao.GCTEventV2Dao;
import com.qding.bigdata.ds.model.GCTOverallV2Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-8-19 下午9:07
 * @Description TODO
 **/

@Component
public class GCTEventV2HandlerForAop {

    GCTEventV2Dao gctEventV2Dao;

    @Resource(name = "esSqlSession")
    public void initESSqlDao(SqlSession sqlSession) {
        this.gctEventV2Dao = sqlSession.getMapper(GCTEventV2Dao.class);
    }


    @FilterResultAnno(GCTCompanyNameResultFilter.class)
    public List<Map<String, Object>> getAnalysisOverviewByCompany(GCTOverallV2Param param, String action){
        return gctEventV2Dao.getAnalysisOverviewByCompany(param, action);
    }
}
