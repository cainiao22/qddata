package com;

import com.qding.bigdata.ds.dao.CommonDataSqlDao;
import com.qding.bigdata.ds.dao.GCTOverallV2Dao;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/14 下午2:03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:/spring/controller-servlet.xml","classpath*:/spring/applicationContext.xml"})
public class TestESConnection {
    @Resource(name = "esDataSource")
    BasicDataSource esDataSource;
    GCTOverallV2Dao gctOverallV2Dao;

    @Resource(name = "esSqlSession")
    public void initExecutorGPDao(SqlSession sqlSession) {
        this.gctOverallV2Dao = sqlSession.getMapper(GCTOverallV2Dao.class);
    }
    @Test
    public void getConnection() throws SQLException {
//        List<Map<String, Object>> overallAnalysis = gctOverallV2Dao.getOverallAnalysis();
//        System.out.println(overallAnalysis);
    }
}
