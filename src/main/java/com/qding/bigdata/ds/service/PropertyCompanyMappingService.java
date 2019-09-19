package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.DimCompanyMappingEntity;

import java.util.List;

/**
 * @Author yanpf
 * @Date 19-8-16 下午3:56
 * @Description TODO
 **/

public interface PropertyCompanyMappingService {

    DimCompanyMappingEntity dimCompanyMappingEntityById(String companyId);

    List<DimCompanyMappingEntity> findAllQyrCompany();
}
