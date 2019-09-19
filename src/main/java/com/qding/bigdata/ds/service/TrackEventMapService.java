package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.TrackEventMap;

import java.util.List;

/**
 * @author yanpf
 * @date 2018/7/18 10:56
 * @description
 */
public interface TrackEventMapService {

    String getNameByEventId(String eventId);
    List<TrackEventMap> getTrackEventMapListByType(String eventType);
}
