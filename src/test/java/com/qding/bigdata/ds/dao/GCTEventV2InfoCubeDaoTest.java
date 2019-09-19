package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.GCTEventV2Param;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/controller-servlet.xml","classpath*:/spring/applicationContext.xml"})

public class GCTEventV2InfoCubeDaoTest {


    GCTEventV2InfoCubeDao gctEventV2InfoCubeDao;

    @Resource(name = "esSqlSession")
    public void initESSqlDao(SqlSession sqlSession) {
        this.gctEventV2InfoCubeDao = sqlSession.getMapper(GCTEventV2InfoCubeDao.class);
    }

    @Test
    public void getTableAnalysis() {
        GCTEventV2Param param = new GCTEventV2Param();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -3);
        param.setStartDate(calendar.getTime());
        param.setDateType("month");
        param.setEndDate(new Date());
        List<Map<String, Object>> tableAnalysis = gctEventV2InfoCubeDao.getTableAnalysis(param);
        System.out.println(tableAnalysis);
    }
}