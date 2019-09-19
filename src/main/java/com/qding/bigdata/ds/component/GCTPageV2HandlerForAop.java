package com.qding.bigdata.ds.component;

import com.qding.bigdata.ds.annotation.FilterResultAnno;
import com.qding.bigdata.ds.dao.GCTPageV2Dao;
import com.qding.bigdata.ds.model.GCTOverallV2Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-8-19 下午9:03
 * @Description TODO
 **/
@Component
public class GCTPageV2HandlerForAop {

    GCTPageV2Dao gctPageV2Dao;

    @Resource(name = "esSqlSession")
    public void initESSqlDao(SqlSession sqlSession) {
        this.gctPageV2Dao = sqlSession.getMapper(GCTPageV2Dao.class);
    }


    @FilterResultAnno(GCTCompanyNameResultFilter.class)
    public List<Map<String, Object>> getAnalysisOverviewByCompany(GCTOverallV2Param param, String action) {
        return gctPageV2Dao.getAnalysisOverviewByCompany(param, action);
    }
}
