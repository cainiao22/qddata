package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.RoleLevel;

public interface RoleLevelDao extends BaseDao<RoleLevel> {

    RoleLevel getByEnName(String enName);
}
