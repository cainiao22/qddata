package com.qding.bigdata.ds.component;

import com.qding.bigdata.ds.model.TrackMsg;
import com.qding.bigdata.ds.model.TrackParam;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author yanpf
 * @date 2018/7/20 16:52
 * @description
 */

@Component
public class PageHandlerResultViewFilter implements ResultFilter<List<TrackMsg>, List<TrackMsg>, TrackParam> {

    @Override
    public List<TrackMsg> doAfter(List<TrackMsg> trackMsgList, TrackParam params) {

        if(params.getOffset() != null && params.getPageCount() != null){
            if (params.getOffset() >= trackMsgList.size()){
                return Collections.emptyList();
            }else if (params.getOffset() + params.getPageCount() >= trackMsgList.size()){
                return trackMsgList.subList(params.getOffset(), trackMsgList.size());
            }else{
                return trackMsgList.subList(params.getOffset(), params.getOffset() + params.getPageCount());
            }
        }

        return trackMsgList;
    }
}
