package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.TrackMsg;
import com.qding.bigdata.ds.model.TrackParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author yanpf
 * @date 2018/7/13 14:46
 * @description
 */
public interface TrackService {

    String INDEX = "bigdata_system_monitor";

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+08:00");

    //String EVENT_LOGIN = "login001";
    String EVENT_LOGIN = "{\"event\":\"login001\"}";

    List<TrackMsg> queryTrackDetails(TrackParam param) throws IllegalAccessException;

    List<TrackMsg> queryTrackSummary(TrackParam param) throws IllegalAccessException;
}
