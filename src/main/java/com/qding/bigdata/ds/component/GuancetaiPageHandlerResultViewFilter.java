package com.qding.bigdata.ds.component;

import com.qding.bigdata.ds.model.GuancetaiQuotaParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/1/24 21:06
 * @description
 */

@Component
public class GuancetaiPageHandlerResultViewFilter implements ResultFilter<List, List, GuancetaiQuotaParam>{
    @Override
    public List doAfter(List value, GuancetaiQuotaParam params) {
        if(params.getLimit() != null && value != null && value.size() > params.getLimit()){
            return value.subList(0, params.getLimit());
        }

        return  value;
    }
}
