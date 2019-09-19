package com.qding.bigdata.ds.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qding.bigdata.ds.model.TrackMsg;
import com.qding.bigdata.ds.model.TrackParam;
import com.qding.bigdata.ds.service.TrackEventMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yanpf
 * @date 2018/7/18 10:29
 * @description
 */

@Component
public class TrackResultViewFilter implements ResultFilter<List<TrackMsg>, List<TrackMsg>, TrackParam> {

    @Autowired
    TrackEventMapService trackEventMapService;

    @Override
    public List<TrackMsg> doAfter(List<TrackMsg> trackMsgList, TrackParam param) {
        for (TrackMsg trackMsg : trackMsgList) {
            String secSource = trackMsg.getSecSource();
            String eventName = trackEventMapService.getNameByEventId(secSource);
            if (!StringUtils.isEmpty(eventName)){
                trackMsg.setSecSource(eventName);
            }

            if(!StringUtils.isEmpty(trackMsg.getEvent())){
                trackMsg.setEvent(processJsonObj(JSON.parseObject(trackMsg.getEvent())));
            }

            if(!StringUtils.isEmpty(trackMsg.getRefEvent())){
                trackMsg.setEvent(processJsonObj(JSON.parseObject(trackMsg.getRefEvent())));
            }
        }

        return trackMsgList;
    }

    private String processJsonObj(JSONObject eventJson){
        if (eventJson == null){
            return null;
        }
        Set<Map.Entry<String, Object>> entries = eventJson.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if (entry.getValue() instanceof String){
                String value = ((String) entry.getValue());
                String replace = trackEventMapService.getNameByEventId(value);
                if (!StringUtils.isEmpty(replace)) {
                    entry.setValue(replace);
                }
            }else if (entry.getValue() instanceof JSONObject){
                JSONObject value = (JSONObject) entry.getValue();
                String processJsonObj = processJsonObj(value);
                if (!StringUtils.isEmpty(processJsonObj)) {
                    entry.setValue(processJsonObj);
                }
            }else if (entry.getValue() instanceof JSONArray) {
                JSONArray value = (JSONArray) entry.getValue();
                for (int i=0; i<value.size(); i++) {
                    Object o = value.get(i);
                    if(o instanceof String) {
                        String replace = trackEventMapService.getNameByEventId(o.toString());
                        if (!StringUtils.isEmpty(replace)) {
                            entry.setValue(replace);
                        }
                    }else if(o instanceof JSONObject){
                        String processJsonObj = processJsonObj(((JSONObject) o));
                        value.set(i, processJsonObj);
                    }
                }
            }
        }

        return JSON.toJSONString(entries);
    }
}
