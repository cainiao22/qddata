package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DimCompanyMappingEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface DimCompanyMappingEntityMapper {

    DimCompanyMappingEntity findByCompanyId(@Param("companyId") String companyId);

    List<DimCompanyMappingEntity> findAllQyrCompany();
}