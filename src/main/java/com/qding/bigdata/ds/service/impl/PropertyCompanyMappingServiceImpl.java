package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.annotation.DynamicDataSource;
import com.qding.bigdata.ds.aop.dynamicsource.DataSourceContextHolder;
import com.qding.bigdata.ds.dao.*;
import com.qding.bigdata.ds.model.DimCompanyMappingEntity;
import com.qding.bigdata.ds.service.PropertyCompanyMappingService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author yanpf
 * @Date 19-8-16 下午4:07
 * @Description TODO
 **/
@Service
public class PropertyCompanyMappingServiceImpl implements PropertyCompanyMappingService {

    DimCompanyMappingEntityMapper companyMappingEntityMapper;

    @Resource(name = "dynamicSqlSession")
    public void initDao(SqlSession sqlSession) {
        DataSourceContextHolder.setDataSource("databus");
        this.companyMappingEntityMapper = sqlSession.getMapper(DimCompanyMappingEntityMapper.class);
        DataSourceContextHolder.clearDataSource();
    }

    @DynamicDataSource("databus")
    @Override
    @Cacheable(value = "redisCache", key = "'DimCompanyMappingEntity'.concat(':').concat(#companyId).concat(':').concat(#companyId).concat(':').concat(#companyId).concat(':').concat(3600)")
    public DimCompanyMappingEntity dimCompanyMappingEntityById(String companyId){
        return companyMappingEntityMapper.findByCompanyId(companyId);
    }

    @Override
    @Cacheable(value = "redisCache", key = "'DimCompanyMappingEntity'.concat(':').concat('findAllQyrCompany').concat(':').concat('findAllQyrCompany').concat(':').concat('findAllQyrCompany').concat(':').concat(3600)")
    public List<DimCompanyMappingEntity> findAllQyrCompany() {
        return companyMappingEntityMapper.findAllQyrCompany();
    }

}
