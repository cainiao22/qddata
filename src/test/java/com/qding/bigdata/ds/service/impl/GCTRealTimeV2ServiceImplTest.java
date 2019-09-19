package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.GCTBaseV2Dao;
import com.qding.bigdata.ds.model.GCTOverallV2Param;
import com.qding.bigdata.ds.model.GuancetaiQuotaInfo;
import com.qding.bigdata.ds.service.GCTRealTimeV2Service;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import static org.junit.Assert.*;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/controller-servlet.xml","classpath*:/spring/applicationContext.xml"})

public class GCTRealTimeV2ServiceImplTest {

    DateFormat dateFormat = DateFormat.getDateInstance();

    @Autowired
    GCTRealTimeV2Service service;

    GCTBaseV2Dao baseV2Dao;

    @Resource(name = "esSqlSession")
    public void initESSqlDao(SqlSession sqlSession) {
        this.baseV2Dao = sqlSession.getMapper(GCTBaseV2Dao.class);
    }

    @Test
    public void testZero(){
        /*Map<String, String> map = baseV2Dao.testZero();
        System.out.println(map);*/
    }

    @Test
    public void getRealTimeAnalysis() throws ParseException {
        GCTOverallV2Param param = new GCTOverallV2Param();
        param.setStartDate(dateFormat.parse("2019-08-16"));
        Map<String, GuancetaiQuotaInfo> map = service.getRealTimeAnalysis(param);
        System.out.println(map);
    }

    @Test
    public void getRealTimeTop5Analysis() throws ParseException {
        GCTOverallV2Param param = new GCTOverallV2Param();
        param.setStartDate(dateFormat.parse("2019-08-16"));
        service.getRealTimeTopAnalysis(param);
    }

    @Test
    public void getRealTimeTableAnalysis() throws ParseException {
        GCTOverallV2Param param = new GCTOverallV2Param();
        param.setStartDate(dateFormat.parse("2019-08-16"));
        service.getRealTimeTableAnalysis(param);
    }

    @Test
    public void getRealTimeAnalysisByCompany(){
        GCTOverallV2Param param = new GCTOverallV2Param();
        String[] zhibiaos = new String[]{"total", "start_up", "reg_new", "active", "page_visit_time", "event_visit_time"};
        for(String zhibiao : zhibiaos) {
            param.setAction(zhibiao);
            param.setQRYcompanyId("000001");
            Map<String, GuancetaiQuotaInfo> realTimeAnalysisByCompany = service.getRealTimeAnalysisByCompany(param);
            System.out.println(realTimeAnalysisByCompany);
        }
    }

    @Test
    public void getRealTimeAnalysisByQyrCompany(){
        GCTOverallV2Param param = new GCTOverallV2Param();
        String[] zhibiaos = new String[]{"total", "start_up", "reg_new", "active", "page_visit_time", "event_visit_time"};
        for(String zhibiao : zhibiaos) {
            param.setAction(zhibiao);
            param.setQRYcompanyId("000001");
            Map<String, GuancetaiQuotaInfo> realTimeAnalysisByCompany = service.getRealTimeAnalysisByQyrCompany(param);
            System.out.println(realTimeAnalysisByCompany);
        }
    }
}