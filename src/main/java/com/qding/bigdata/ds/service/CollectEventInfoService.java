package com.qding.bigdata.ds.service;

import java.util.Map;

public interface CollectEventInfoService {

    Map<String,Object> getCollectEventInfo();

    Map<String,Object> collectEventInfoBySource(Map<String, String> params) throws Exception;

    Map<String,Object> collectEventInfoByEvent(Map<String, String> params) throws Exception;
}
