package com.qding.bigdata.ds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;

import org.springframework.stereotype.Service;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.RoleLevelModuleDao;
import com.qding.bigdata.ds.model.RoleLevelModule;
import com.qding.bigdata.ds.service.RoleLevelModuleService;

@Service
public class RoleLevelModuleServiceImpl extends BaseServiceImpl<RoleLevelModule>
    implements RoleLevelModuleService {
  @Autowired
  private RoleLevelModuleDao roleLevelModuleDao;

  @Override
  public BaseDao<RoleLevelModule> getDao() {
    return roleLevelModuleDao;
  }

  @CacheEvict(value = "redisCache", key = "'roleModuleRelation:'.concat(#roleLevelModule.roleLevelId)")
  @Override
  public void delete(RoleLevelModule roleLevelModule) {
    super.delete(roleLevelModule);
  }

  @CacheEvict(value = "redisCache", key = "'roleModuleRelation:'.concat(#roleLevelModule.roleLevelId)")
  @Override
  public void save(RoleLevelModule roleLevelModule) {
    super.save(roleLevelModule);
  }
}
