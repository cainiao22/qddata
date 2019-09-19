package com.qding.bigdata.ds.component;

import com.qding.bigdata.ds.model.GCTBaseV2Param;
import com.qding.bigdata.ds.model.GuancetaiQuotaInfo;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author yanpf
 * @Date 19-8-21 下午3:19
 * @Description
 **/
@Component
public class GuancetaiQuotaInfoSortResultFilter implements ResultFilter<Map, Map, GCTBaseV2Param> {
    @Override
    public Map doAfter(Map value, GCTBaseV2Param params) {
        LinkedHashMap<String, GuancetaiQuotaInfo> result = new LinkedHashMap();
        List<Pair<String, GuancetaiQuotaInfo>> list = new ArrayList<>();
        value.forEach((k, v) -> {
            Pair<String, GuancetaiQuotaInfo> pair = new Pair<>(k.toString(), ((GuancetaiQuotaInfo) v));
            list.add(pair);
        });
        list.sort((o1, o2) -> o2.getValue().getCurrent().compareTo(o1.getValue().getCurrent()));
        for (Pair<String, GuancetaiQuotaInfo> pair : list) {
            result.put(pair.getKey(), pair.getValue());
        }

        return result;
    }
}
