package com.qding.bigdata.ds.service;

import java.util.List;

import com.qding.bigdata.ds.model.Module;
import com.qding.bigdata.ds.model.RoleLevel;
import com.qding.bigdata.ds.model.User;

public interface RoleLevelService extends BaseService<RoleLevel> {

    List<RoleLevel> listByUser(User user);
    List<RoleLevel> listByModule(Module module);
    List<RoleLevel> getSubRoleLevels(RoleLevel roleLevel);
    RoleLevel getByEnName(String enName);
}
