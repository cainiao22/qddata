package com.qding.bigdata.ds.component;

import com.qding.bigdata.ds.model.DimCompanyMappingEntity;
import com.qding.bigdata.ds.service.PropertyCompanyMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.qding.bigdata.ds.service.GCTV2BaseService.COMMON_KEY;

/**
 * @Author yanpf
 * @Date 19-8-23 上午11:55
 * @Description TODO
 **/

@Component
public class GCTCompanyNameWithQryResultFilter implements ResultFilter<List, List, Object> {

    @Autowired
    PropertyCompanyMappingService companyMappingService;

    @Override
    public List doAfter(List value, Object params) {
        if (value == null) {
            return null;
        }
        value.forEach(item -> {
            Map<String, Object> map = (Map<String, Object>) item;
            String companyId = map.get(COMMON_KEY).toString();
            DimCompanyMappingEntity dimCompanyMappingEntity = companyMappingService.dimCompanyMappingEntityById(companyId);
            if (dimCompanyMappingEntity != null) {
                map.put(COMMON_KEY, String.format("%s(%s)", dimCompanyMappingEntity.getCompanyName(), dimCompanyMappingEntity.getQyrCompanyNameList().substring(0, 2)));
            }
        });

        return value;
    }
}
