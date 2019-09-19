package com.qding.bigdata.ds.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qding.bigdata.ds.model.RoleLevel;
import com.qding.bigdata.ds.model.User;

public interface UserService extends BaseService<User> {

    HashSet<String> getAllowedModules(User user);

    void syncsAllUsersAuthority();

    List<User> listByRoleLevel(RoleLevel roleLevel);
    
    User getUserByUserName(String userName);

	int updatePassword(User userParam);
	
	void loginByBoss(User user, HttpServletRequest request,HttpServletResponse response, Map<String, String> responseMap) throws Exception;

    void updateUserInDB(User user, HttpServletRequest request, HttpServletResponse response);

    /**
     * 校验用户密码
     * @param user
     * @return
     */
    Map<String,Object> checkUserPassword(HttpServletRequest request,User user);
}
