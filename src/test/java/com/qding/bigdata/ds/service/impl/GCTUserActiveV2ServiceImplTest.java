package com.qding.bigdata.ds.service.impl;

import com.alibaba.fastjson.JSON;
import com.qding.bigdata.ds.model.GCTEventV2Param;
import com.qding.bigdata.ds.model.GCTOverallV2Param;
import com.qding.bigdata.ds.service.GCTUserActiveV2Service;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;



@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/controller-servlet.xml","classpath*:/spring/applicationContext.xml"})

public class GCTUserActiveV2ServiceImplTest {


    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    GCTUserActiveV2Service gctUserActiveV2Service;

    @Test
    public void getTableAnalysis() throws ParseException {

        List<GCTOverallV2Param> paramList = new ArrayList<>();
        GCTOverallV2Param param = new GCTEventV2Param();
        param.setDateType("month");
        param.setAction("active_user_count");
        param.setCompanyId("all");
        param.setVersion("all");
        param.setDeviceType("all");
        param.setQRYcompanyId("all");
        param.setStartDate(dateFormat.parse("2019-04-01"));
        param.setEndDate(new Date());
        paramList.add(param);

        GCTOverallV2Param param2 = BeanMapper.map(param, GCTEventV2Param.class);
        param2.setDateType("day");
        param2.setAction("new_user_rate");
        param2.setStartDate(new Date());
        param2.setEndDate(new Date());
        paramList.add(param2);

        GCTOverallV2Param param3 = BeanMapper.map(param, GCTEventV2Param.class);
        param3.setDateType("month");
        param3.setAction("active_user_rate");
        param3.setStartDate(dateFormat.parse("2019-04-01"));
        param3.setEndDate(new Date());
        paramList.add(param3);

        Map<String, List<Map<String, Object>>> tableAnalysis = gctUserActiveV2Service.getTableAnalysis(paramList);
        System.out.println(JSON.toJSONString(tableAnalysis));
    }
}