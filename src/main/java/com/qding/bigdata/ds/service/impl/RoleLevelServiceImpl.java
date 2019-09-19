package com.qding.bigdata.ds.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.RoleLevelDao;
import com.qding.bigdata.ds.model.Module;
import com.qding.bigdata.ds.model.RoleLevel;
import com.qding.bigdata.ds.model.RoleLevelModule;
import com.qding.bigdata.ds.model.User;
import com.qding.bigdata.ds.model.UserRoleLevel;
import com.qding.bigdata.ds.service.RoleLevelModuleService;
import com.qding.bigdata.ds.service.RoleLevelService;
import com.qding.bigdata.ds.service.UserRoleLevelService;
import com.qding.bigdata.ds.util.CommonUtil;

@Service
public class RoleLevelServiceImpl extends BaseServiceImpl<RoleLevel> implements RoleLevelService {

  @Autowired
  private RoleLevelDao roleLevelDao;
  @Autowired
  private UserRoleLevelService userRoleLevelService;
  @Autowired
  private RoleLevelModuleService roleLevelModuleService;

  @Override
  public BaseDao<RoleLevel> getDao() {
    return roleLevelDao;
  }

  @Cacheable(value = "redisCache", key = "'userRoleRelation:'.concat(#user.id)")
  @Override
  public List<RoleLevel> listByUser(User user) {
    Set<String> ids = new HashSet<String>();
    List<UserRoleLevel> userRoleLevelList =
        userRoleLevelService.listAll(new UserRoleLevel(null, user.getId()));
    for (UserRoleLevel userRoleLevel : userRoleLevelList) {
      ids.add(userRoleLevel.getRoleLevelId());
    }
    return this.listByIDs(ids);
  }


  @Override
  public void update(RoleLevel t) {
    setRoleLevelModule(t);
    setUserRoleLevel(t);
    super.update(t);
  }

  private void setUserRoleLevel(RoleLevel roleLevel) {
    List<UserRoleLevel> userRoleLevels = userRoleLevelService.list(new UserRoleLevel(roleLevel.getId(), null));
    if(userRoleLevels != null){
      for (UserRoleLevel userRoleLevel : userRoleLevels) {
        userRoleLevelService.delete(userRoleLevel);
      }
    }
    //userRoleLevelService.delete(new UserRoleLevel(roleLevel.getId(), null));
    String usersStr = roleLevel.getUsersStr();
    if (CommonUtil.isEmpty(usersStr)) {
      return;
    }
    String[] userSplit = usersStr.split(",");
    for (String userid : userSplit) {
      userRoleLevelService.save(new UserRoleLevel(roleLevel.getId(), userid));
    }
  }

  private void setRoleLevelModule(RoleLevel roleLevel) {
    roleLevelModuleService.delete(new RoleLevelModule(roleLevel.getId(), null));
    String modulesStr = roleLevel.getModulesStr();
    if (CommonUtil.isEmpty(modulesStr)) {
      return;
    }
    String[] moduleSplit = modulesStr.split(",");
    for (String moduleId : moduleSplit) {
      roleLevelModuleService.save(new RoleLevelModule(roleLevel.getId(), moduleId));
    }
  }

  @Override
  public List<RoleLevel> listByModule(Module module) {
    Set<String> ids = new HashSet<String>();
    List<RoleLevelModule> roleLevelModuleList =
        roleLevelModuleService.listAll(new RoleLevelModule(null, module.getId()));
    for (RoleLevelModule roleLevelModule : roleLevelModuleList) {
      ids.add(roleLevelModule.getRoleLevelId());
    }
    return this.listByIDs(ids);
  }

  @Override
  public void delete(RoleLevel roleLevel) {
    List<UserRoleLevel> userRoleLevels = userRoleLevelService.listAll(new UserRoleLevel(roleLevel.getId(), null));
    if(userRoleLevels != null){
      for (UserRoleLevel userRoleLevel : userRoleLevels) {
        userRoleLevelService.delete(userRoleLevel);
      }
    }

    roleLevelModuleService.delete(new RoleLevelModule(roleLevel.getId(), null));
    super.delete(roleLevel);
  }

  @Override
  public List<RoleLevel> getSubRoleLevels(RoleLevel roleLevel) {
    RoleLevel roleLevelParam = new RoleLevel();
    roleLevelParam.setRoleId(roleLevel.getRoleId());
    List<RoleLevel> allSameRoleLevels = this.listAll(roleLevelParam);
    List<RoleLevel> list = new ArrayList<RoleLevel>();
    for (RoleLevel rl : allSameRoleLevels) {
      if (rl.getLevel() > roleLevel.getLevel()) {
        list.add(rl);
      }
    }

    return list;
  }

  @Override
  public RoleLevel getByEnName(String enName) {
    return roleLevelDao.getByEnName(enName);
  }


}
