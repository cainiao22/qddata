package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.PortraitUserGroup;
import com.qding.bigdata.ds.model.UserGroup;

import java.util.List;


public interface PortraitUserGroupDao extends BaseDao<PortraitUserGroup> {

	List<PortraitUserGroup> getUserGroups(PortraitUserGroup userGroupParam);



}
