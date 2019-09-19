package com.qding.bigdata.ds.component;

import com.qding.bigdata.ds.model.DimCompanyMappingEntity;
import com.qding.bigdata.ds.service.PropertyCompanyMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.qding.bigdata.ds.service.GCTV2BaseService.COMMON_KEY;
import static com.qding.bigdata.ds.service.GCTV2BaseService.COMMON_VALUE;

/**
 * @Author yanpf
 * @Date 19-8-16 下午2:55
 * @Description 填充companyName
 **/
@Component
public class GCTCompanyNameResultFilter implements ResultFilter<List, List, Object> {

    @Autowired
    PropertyCompanyMappingService companyMappingService;

    @Override
    public List<Map<String, Object>> doAfter(List value, Object params) {
        if(value == null){
            return null;
        }
        value.forEach(item -> {
            Map<String, Object> map = (Map<String, Object>) item;
            String companyId = map.get(COMMON_KEY).toString();
            map.put("companyId", companyId);
            DimCompanyMappingEntity dimCompanyMappingEntity = companyMappingService.dimCompanyMappingEntityById(companyId);
            if(dimCompanyMappingEntity != null){
                map.put(COMMON_KEY, dimCompanyMappingEntity.getCompanyName());
            }
        });

        return value;
    }
}