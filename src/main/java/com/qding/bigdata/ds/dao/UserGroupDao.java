package com.qding.bigdata.ds.dao;

import java.util.List;

import com.qding.bigdata.ds.model.UserGroup;



public interface UserGroupDao extends BaseDao<UserGroup> {

	List<UserGroup> getUserGroups(UserGroup userGroupParam);



}
