package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.TrackEventMap;

import java.util.List;

public interface TrackEventMapDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TrackEventMap record);

    int insertSelective(TrackEventMap record);

    TrackEventMap selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TrackEventMap record);

    int updateByPrimaryKey(TrackEventMap record);

    String getNameByEventId(String eventId);

    List<TrackEventMap> getTrackEventMapListByType(String eventType);
}