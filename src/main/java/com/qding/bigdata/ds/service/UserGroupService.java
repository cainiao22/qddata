package com.qding.bigdata.ds.service;

import java.util.List;
import java.util.Map;

import com.qding.bigdata.ds.model.PortraitUserGroup;
import com.qding.bigdata.ds.model.UserGroup;

public interface UserGroupService extends BaseService<PortraitUserGroup> {

	List<PortraitUserGroup> getUserGroups(PortraitUserGroup userGroupParam);

	Map<String,Object> getConditionsById(String userGroupId);



}
