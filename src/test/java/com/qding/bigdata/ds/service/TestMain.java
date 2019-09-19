package com.qding.bigdata.ds.service;

import com.alibaba.fastjson.JSON;
import com.qding.bigdata.ds.model.GCTEventV2Param;
import com.qding.bigdata.ds.model.GCTOverallV2Param;
import com.qding.bigdata.ds.util.BeanMapper;
import javafx.util.Pair;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

/**
 * @author yanpf
 * @date 2018/5/25 18:51
 * @description
 */
public class TestMain {

    static DateFormat dateFormat = DateFormat.getDateInstance();

    public static void main(String[] args) throws ParseException {
        /*List<GCTOverallV2Param> paramList = new ArrayList<>();
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
        param2.setStartDate(dateFormat.parse("2019-08-21"));
        param2.setEndDate(new Date());
        paramList.add(param2);

        GCTOverallV2Param param3 = BeanMapper.map(param, GCTEventV2Param.class);
        param3.setDateType("month");
        param3.setAction("active_user_rate");
        param3.setStartDate(dateFormat.parse("2019-04-01"));
        param3.setEndDate(new Date());
        paramList.add(param3);
        System.out.println(JSON.toJSONString(paramList));*/
        System.out.println(Double.NaN);
        System.out.println(Double.POSITIVE_INFINITY);
        System.out.println(Double.NEGATIVE_INFINITY);

        Integer value = Integer.valueOf("NaN");
        System.out.println(value);
    }
}
