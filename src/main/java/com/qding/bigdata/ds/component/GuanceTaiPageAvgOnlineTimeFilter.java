package com.qding.bigdata.ds.component;

import com.qding.bigdata.ds.model.GuancetaiPageDetail;
import com.qding.bigdata.ds.model.GuancetaiQuotaParam;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author yanpf
 * @date 2018/12/28 18:03
 * @description
 */

@Component
public class GuanceTaiPageAvgOnlineTimeFilter implements ResultFilter<List<GuancetaiPageDetail>, List<GuancetaiPageDetail>, GuancetaiQuotaParam> {
    @Override
    public List<GuancetaiPageDetail> doAfter(List<GuancetaiPageDetail> value, GuancetaiQuotaParam params) {
        if(value == null){
            return null;
        }
        for (GuancetaiPageDetail detail : value) {
            if(detail.getOnlineTimeEveryTime() != null) {
                detail.setOnlineTimeEveryTime(Math.round(detail.getOnlineTimeEveryTime() / detail.getVisitUsers() * 10) / 10000.0);
            }else {
                detail.setOnlineTimeEveryTime(0.0);
            }
        }
        /*Collections.sort(value, new Comparator<GuancetaiPageDetail>() {
            @Override
            public int compare(GuancetaiPageDetail o1, GuancetaiPageDetail o2) {
                if(o1.compare(o1.getTime(), o2.getTime()) != 0){
                    return o1.compare(o1.getTime(), o2.getTime());
                }
                if(o1.compare(o1.getPageId(), o2.getPageId()) != 0){
                    return o1.compare(o1.getPageId(), o2.getPageId());
                }

                return o1.compare(o1.getSource(), o2.getSource());
            }
        });*/
        return value;
    }
}
