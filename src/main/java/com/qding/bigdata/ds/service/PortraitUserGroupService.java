package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.PortraitUserGroup;
import com.qding.bigdata.ds.model.UserGroup;

import java.util.List;
import java.util.Map;

public interface PortraitUserGroupService extends BaseService<PortraitUserGroup> {

	List<PortraitUserGroup> getPortraitUserGroups(PortraitUserGroup userGroupParam);

	Map<String,Object> getConditionsById(String userGroupId);



}
