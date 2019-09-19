package com.qding.bigdata.ds;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qding.bigdata.ds.model.TrackEventMap;
import com.qding.bigdata.ds.model.TrackMsg;
import com.qding.bigdata.ds.model.TrackParam;
import com.qding.bigdata.ds.service.TrackEventMapService;
import com.qding.bigdata.ds.service.TrackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author yanpf
 * @date 2018/7/16 13:21
 * @description
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/controller-servlet.xml","classpath*:/spring/applicationContext.xml"})
public class TrackServiceTest {

    @Autowired
    private TrackService trackService;

    @Autowired
    private TrackEventMapService trackEventMapService;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void test() throws Exception {
        TrackParam trackParam = new TrackParam();
        trackParam.setSecondSource("<二级来源>");
        trackParam.setStartDate(dateFormat.parse("2018-07-13"));
        trackParam.setEndDate(dateFormat.parse("2018-08-13"));
        List<TrackMsg> trackMsgs = trackService.queryTrackDetails(trackParam);
        System.out.println(trackMsgs);
    }

    @Test
    public void test2() throws Exception {
        TrackParam trackParam = new TrackParam();
        trackParam.setStartDate(dateFormat.parse("2018-07-13"));
        trackParam.setEndDate(dateFormat.parse("2018-08-13"));
        List<TrackMsg> trackMsgs = trackService.queryTrackSummary(trackParam);
        System.out.println(trackMsgs);
    }

    @Test
    public void testfastjson(){
        JSONObject s = JSON.parseObject("{city:[],yetai:[formats001],index:[],time:time001}");
        System.out.println(s);
    }

    @Test
    public void testGetByEventType(){
        List<TrackEventMap> event = trackEventMapService.getTrackEventMapListByType("event");
        System.out.println(event);
    }
}
