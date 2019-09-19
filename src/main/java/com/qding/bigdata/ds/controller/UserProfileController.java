package com.qding.bigdata.ds.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eclipsesource.json.Json;
import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.PortraitTag;
import com.qding.bigdata.ds.model.PortraitUserGroup;
import com.qding.bigdata.ds.service.TagService;
import com.qding.bigdata.ds.service.UserGroupService;
import com.qding.bigdata.ds.service.UserProfileV2Service;
import com.qding.bigdata.ds.service.impl.UserProfileService;
import com.qding.bigdata.ds.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserProfileController extends BasicController {
	@Autowired
	private TagService tagService;
	@Autowired
	private UserProfileService  userProfileService;

	@Autowired
	UserProfileV2Service userProfileV2Service;

	@Autowired
	private UserGroupService userGroupService;
	
	
	@RequestMapping("/userProfile")
	public ModelAndView index() {
		ModelAndView modelAndView = initModelAndView();
		modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.画像分析);
		return modelAndView;

	}

	@RequestMapping("/getAllTags")
	@ResponseBody
	public Object getAllTags() {
		PortraitTag tag = new PortraitTag();
		/*if(StringUtils.isNotBlank(modCondition)&&modCondition.equals("1")){
			tag.setSimpleModle(1);
		}else if(StringUtils.isNotBlank(modCondition)&&modCondition.equals("2")){
			tag.setCommonConditon(1);
		}*/
		return tagService.listAll(tag);
	}

	@RequestMapping("/userProfilequery")
	@ResponseBody
	public Object userProfilequery(@RequestParam("query") String query) {
		return userProfileService.query(JSONObject.parseObject(query));
	}
	
	@RequestMapping("/userProfilequeryForSum")
	@ResponseBody
	public Object userProfilequeryForSum(@RequestParam("query") String query) {
		return userProfileService.queryForSum(JSONObject.parseObject(query));
	}

	@RequestMapping("/userProfileQueryById")
	@ResponseBody
	public Object userProfileQueryById(String userGroupId) {
		Map<String, Object> conditions = userGroupService.getConditionsById(userGroupId);
		JSONObject jsonObject = null;
		try {
			jsonObject = JSON.parseObject(conditions.get("results").toString());
		}catch (Exception e){
			return conditions.get("results");
		}

		JSONArray conditionsArr = (JSONArray) jsonObject.get("conditions");
		String wildcardParam = (String) jsonObject.get("wildcardParam");
		int from = 0 ;
		int size = 1000;
		Map<String, Object> result = userProfileV2Service.getUserDetails(conditionsArr,wildcardParam,from,size);
		result.put("results", jsonObject);
		result.put("totalHit", result.get("totalHits"));

		return result;
	}
	
	/**
	 * 导出用户列表
	 * @param userGroupId
	 * @return
	 */
	@RequestMapping("/exportUsers")
	@ResponseBody
	public Object exportUsers(String userGroupId) {
		Object exportUsers = null;
		try {
			//exportUsers = userProfileService.exportUsers(userGroupId);
			exportUsers = userProfileV2Service.exportUsers(userGroupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exportUsers;
	}
	
	/**
	 * 查询用户列表
	 * @param userGroupId
	 * @return
	 */
	@RequestMapping("/getUserListFileById")
	@ResponseBody
	public Object getUsersByGroupId(String userGroupId) {
		
		long beginTime = System.currentTimeMillis();
		Map<String,Object> rsMap = new HashMap<>();
		String fileName = "";
		if(StringUtils.isNotBlank(userGroupId)){
			PortraitUserGroup userGroupParam = new PortraitUserGroup();
			userGroupParam.setId(userGroupId);
			PortraitUserGroup userGroup = userGroupService.getOne(userGroupParam);
			if (null != userGroup) {
				fileName =  PropertiesUtil.getPropertiesByKey(
						Constant.CONFIGFILE, "export.user.pre.url") + File.separator + userGroupId + File.separator +"export_user_"+userGroupId+".txt";
			}else{
				fileName = "文件不存在";
			}
		}
		rsMap.put("fileName",fileName);
		long endTime = System.currentTimeMillis();
		rsMap.put("usetime", (endTime - beginTime));
		return rsMap;
	}
	
	/**
	 * 关键词模糊检索接口
	 * @param
	 * @return
	 */
	@RequestMapping("/getWordsByTag")
	@ResponseBody
	public Object getWordsByTag(String tag, String value) {
		long beginTime = System.currentTimeMillis();
		Map<String,Object> rsMap = new HashMap<>();
		List<String> lists = userProfileService.getWordsByTag(tag,value);
		rsMap.put("tag",tag);
		rsMap.put("value",value);
		rsMap.put("results",lists);
		long endTime = System.currentTimeMillis();
		rsMap.put("usetime", (endTime - beginTime));
		return rsMap;
	}
}
