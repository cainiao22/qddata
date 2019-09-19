package com.qding.bigdata.ds.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.RoleDao;
import com.qding.bigdata.ds.model.Role;
import com.qding.bigdata.ds.model.RoleLevel;
import com.qding.bigdata.ds.service.RoleLevelService;
import com.qding.bigdata.ds.service.RoleService;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleLevelService roleLevelService;

    @Override
    public BaseDao<Role> getDao() {
        return roleDao;
    }


    @Override
    public void update(Role t) {
        super.update(t);
    }


    @Override
    public void putSubRoleLevels(Role role) {
        RoleLevel roleLevelParam = new RoleLevel();
        roleLevelParam.setRoleId(role.getId());
        List<RoleLevel> subRoleLevelList = roleLevelService.list(roleLevelParam);
        if (subRoleLevelList != null) {

            role.setSubRoleLevels(subRoleLevelList);
        }
    }
}
