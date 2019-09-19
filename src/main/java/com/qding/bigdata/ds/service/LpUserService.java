package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.LpUser;
import com.qding.bigdata.ds.model.LpUserRole;
import com.qding.bigdata.ds.model.RoleAuthorization;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public interface LpUserService {

    /**
     * 查询所有用户列表
     * @return
     */
    List<LpUser> selectAll();


    /**
     * 依据用户账号和模块名称 查询用户权限
     * @param userName
     * @param moduleName
     * @return
     */
    Map<String ,Object> selectRegionListByUser(String userName, String moduleName);

    /**
     * 查询用户角色表有没有
     * @return
     */
    List<LpUserRole> selectLpUserRoleByUSERID(LpUserRole param);
    /**
     * 修改角色权限关联表
     * @return
     */
    void updateRoleAuthorization(List<RoleAuthorization> roleAuthorizationList, LpUserRole lpUserRole);
    /**
     * 插入角色权限关联表
     * @return
     */
    void saveRoleAuthorization(List<RoleAuthorization> roleAuthorizationList, LpUser lpUser);

    LpUser selectBylpUserId(Long id);

    /**
     * 返回 社区id集合
     * @param userName
     * @param moduleName
     * @return
     */
    HashSet<String> selectRegionListByParam(String userName, String moduleName);
}
