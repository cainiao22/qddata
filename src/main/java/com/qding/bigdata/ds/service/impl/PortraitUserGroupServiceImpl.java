package com.qding.bigdata.ds.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.PortraitUserGroupDao;
import com.qding.bigdata.ds.model.PortraitTag;
import com.qding.bigdata.ds.model.Tag;
import com.qding.bigdata.ds.model.PortraitUserGroup;
import com.qding.bigdata.ds.service.TagService;
import com.qding.bigdata.ds.service.PortraitUserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortraitUserGroupServiceImpl extends BaseServiceImpl<PortraitUserGroup> implements PortraitUserGroupService {
    public static Logger logger = LoggerFactory.getLogger(PortraitUserGroupServiceImpl.class);

    @Autowired
	private PortraitUserGroupDao userGroupDao;

	@Autowired
	private TagService tagService;

	@Override
	public BaseDao<PortraitUserGroup> getDao() {
		return userGroupDao;
	}

	@Override
	public List<PortraitUserGroup> getPortraitUserGroups(PortraitUserGroup userGroupParam) {
		return userGroupDao.getUserGroups(userGroupParam);
	}

	@Override
	public Map<String, Object> getConditionsById(String userGroupId) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		PortraitUserGroup userGroupParam = new PortraitUserGroup();
		userGroupParam.setId(userGroupId);
		PortraitUserGroup userGroup = this.getOne(userGroupParam);
		if(null!=userGroup){
			map.put("reqid", UUID.randomUUID().toString().replace("-", ""));
			map.put("userGroupId", userGroup.getId());

			//获取tag列表
			JSONObject conditions = JSON.parseObject(userGroup.getConditions());
			JSONArray conditionsArr = conditions.getJSONArray("conditions");
			List<String> tagList = new ArrayList<String>();
			for (Object conditionObj : conditionsArr) {
				JSONObject condition = (JSONObject) conditionObj;
				tagList.add(condition.getString("tag").toString());
			}

			//获取tag最新name
			Map<String,String> tagMap = new HashMap<String, String>();
			Map<String,String> dataTypeMap = new HashMap<String, String>();
			if(null!=tagList && tagList.size()>0){
				List<PortraitTag> tags = tagService.listByTags(tagList);
				if(null != tags){
					for (PortraitTag tag:tags ) {
						tagMap.put(tag.getTag(),tag.getName());
						dataTypeMap.put(tag.getTag(),tag.getDataType()+"");
					}
				}
			}

			//处理tag标签name
			if(null != tagMap && tagMap.size()>0){
				for (Object conditionObj : conditionsArr) {
					JSONObject condition = (JSONObject) conditionObj;
					condition.put("tagName",tagMap.get(condition.getString("tag").toString()));
					condition.put("dataType",dataTypeMap.get(condition.getString("tag").toString()));
				}

			}
			map.put("results", conditions);
		}else{
			map.put("reqid",UUID.randomUUID().toString().replace("-", ""));
			map.put("results", "群组ID【"+userGroupId+"】不存在！");
		}

		return map;
	}


}
