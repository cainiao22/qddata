package com.qding.bigdata.ds.component;

import com.qding.bigdata.ds.model.GuancetaiEventDetail;
import com.qding.bigdata.ds.model.GuancetaiQuotaParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/1/2 11:34
 * @description
 */

@Component
public class GuancetaiEventTypeHandler implements ResultFilter<List<GuancetaiEventDetail>, List<GuancetaiEventDetail>, GuancetaiQuotaParam> {
    @Override
    public List<GuancetaiEventDetail> doAfter(List<GuancetaiEventDetail> value, GuancetaiQuotaParam params) {
        if(value == null){
            return value;
        }
        for (GuancetaiEventDetail eventDetail : value) {
            eventDetail.setEventType("点击");
        }
        return value;
    }
}
