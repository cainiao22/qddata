package com.qding.bigdata.ds.component;

import com.qding.bigdata.ds.annotation.FilterResultAnno;
import com.qding.bigdata.ds.dao.GCTBaseV2Dao;
import com.qding.bigdata.ds.model.GCTOverallV2Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-8-17 上午9:19
 * @Description TODO
 **/
@Component
public class GCTBaseV2HandlerForAop {

    GCTBaseV2Dao gctBaseV2Dao;

    @Resource(name = "esSqlSession")
    public void initESSqlDao(SqlSession sqlSession) {
        this.gctBaseV2Dao = sqlSession.getMapper(GCTBaseV2Dao.class);
    }

    @FilterResultAnno(GCTCompanyNameWithQryResultFilter.class)
    public List<Map<String, Object>> getTopActiveCompany(GCTOverallV2Param param){
        return gctBaseV2Dao.getTopActiveCompany(param);
    }

    @FilterResultAnno(GCTCompanyNameResultFilter.class)
    public List<Map<String, Object>> getAnalysisOverviewByCompany(GCTOverallV2Param param, String action){
        return gctBaseV2Dao.getAnalysisOverviewByCompany(param, action);
    }
}
