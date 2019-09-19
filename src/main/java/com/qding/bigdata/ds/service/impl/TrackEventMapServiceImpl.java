package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.TrackEventMapDao;
import com.qding.bigdata.ds.model.TrackEventMap;
import com.qding.bigdata.ds.service.TrackEventMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author yanpf
 * @date 2018/7/18 10:57
 * @description
 */

@Service
public class TrackEventMapServiceImpl implements TrackEventMapService {


    @Autowired
    TrackEventMapDao trackEventMapDao;

    @Override
    @Cacheable(value = "redisCache", key = "'TrackEventMap'.concat(':').concat('eventId:').concat(#eventId)")
    public String getNameByEventId(String eventId) {
        if (StringUtils.isEmpty(eventId)){
            return null;
        }
        return trackEventMapDao.getNameByEventId(eventId);
    }

    @Override
    @Cacheable(value = "redisCache", key = "'TrackEventMap'.concat(':').concat('eventType:').concat(#eventType)")
    public List<TrackEventMap> getTrackEventMapListByType(String eventType) {
        if (StringUtils.isEmpty(eventType)){
            return null;
        }
        return trackEventMapDao.getTrackEventMapListByType(eventType);
    }
}
