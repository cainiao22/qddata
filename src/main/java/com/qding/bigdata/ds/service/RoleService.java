package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.Role;

public interface RoleService extends BaseService<Role> {


    void putSubRoleLevels(Role role);

}
