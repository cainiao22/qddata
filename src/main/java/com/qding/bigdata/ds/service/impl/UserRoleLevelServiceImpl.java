package com.qding.bigdata.ds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;

import org.springframework.stereotype.Service;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.UserRoleLevelDao;
import com.qding.bigdata.ds.model.UserRoleLevel;
import com.qding.bigdata.ds.service.UserRoleLevelService;

@Service
public class UserRoleLevelServiceImpl extends BaseServiceImpl<UserRoleLevel>
    implements UserRoleLevelService {
  @Autowired
  private UserRoleLevelDao userRoleLevelDao;
  @Override
  public BaseDao<UserRoleLevel> getDao() {
    return userRoleLevelDao;
  }

  @CacheEvict(value = "redisCache", key = "'userRoleRelation:'.concat(#userRoleLevel.userId)")
  @Override
  public void delete(UserRoleLevel userRoleLevel) {
    super.delete(userRoleLevel);
  }

  @CacheEvict(value = "redisCache", key = "'userRoleRelation:'.concat(#userRoleLevel.userId)")
  @Override
  public void save(UserRoleLevel userRoleLevel) {
    super.save(userRoleLevel);
  }
}
