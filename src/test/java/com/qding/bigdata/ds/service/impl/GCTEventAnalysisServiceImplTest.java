package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.model.GCTEventV2Param;
import com.qding.bigdata.ds.service.GCTEventAnalysisService;
import com.qding.bigdata.ds.util.BeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/controller-servlet.xml","classpath*:/spring/applicationContext.xml"})
public class GCTEventAnalysisServiceImplTest {

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    GCTEventAnalysisService eventAnalysisService;

    @Test
    public void getTableAnalysis() throws ParseException {
        List<GCTEventV2Param> paramList = new ArrayList<>();
        GCTEventV2Param param = new GCTEventV2Param();
        param.setDateType("month");
        param.setEventId("all");
        param.setAction("active");
        param.setCompanyId("all");
        param.setVersion("all");
        param.setDeviceType("all");
        param.setQRYcompanyId("all");
        param.setStartDate(dateFormat.parse("2019-08-01"));
        param.setEndDate(new Date());
        paramList.add(param);

        GCTEventV2Param param2 = BeanMapper.map(param, GCTEventV2Param.class);
        param2.setDateType("month");
        param2.setStartDate(dateFormat.parse("2019-08-01"));
        param2.setEndDate(new Date());
        paramList.add(param);
        eventAnalysisService.getTableAnalysis(paramList);
    }
}