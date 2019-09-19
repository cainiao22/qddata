package com.qding.bigdata.ds.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.enums.UserSource;
import com.qding.bigdata.ds.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.Role;
import com.qding.bigdata.ds.model.RoleLevel;
import com.qding.bigdata.ds.model.User;
import com.qding.bigdata.ds.service.RoleLevelService;
import com.qding.bigdata.ds.service.RoleService;
import com.qding.bigdata.ds.service.UserRoleLevelService;
import com.qding.bigdata.ds.service.UserService;

@Controller
public class UserController extends BasicController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleLevelService roleLevelService;
	@Autowired
	private UserRoleLevelService userRoleLevelService;
	private static final String QY_WECHAT_USERNAME_URL = "http://m7-vm-bd-66:12051/getUserName?state=%s&code=%s";

	@ResponseBody
	@RequestMapping("/loginCheckAll")
	public Object loginCheckAll(User user, HttpServletRequest request, HttpServletResponse response)  throws Exception {

		Map<String, String> responseMap = new HashMap<String, String>();
		User userInDb = userService.getUserByUserName(user.getUserName());
		boolean isValidUser = (userInDb != null);


		if (!isValidUser) {
			userService.loginByBoss(user, request, response,responseMap);
			return responseMap;
		}else{//是否禁用
			if(userInDb.getStatus()== 1){
				responseMap.put("result", Boolean.FALSE.toString());
				responseMap.put("reason", "账号禁用");
				return responseMap;
			}
		}
		if (userInDb.getPassword().equals(CommonUtil.md5(user.getPassword()))) {
			LoginUtil.setLogin(request, response, userInDb);
			AuthorityUtil.sync(userInDb.getId(), userService.getAllowedModules(userInDb));
			responseMap.put("result", Boolean.TRUE.toString());
			responseMap.put("reason", "");
			responseMap.put("url", "index");
			return responseMap;
		} else {
			userService.loginByBoss(user, request, response,responseMap);
			return responseMap;
		}
	}

	@ResponseBody
	@RequestMapping("/loginCheck")
	public Object loginCheck(User user, HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> responseMap = new HashMap<String, String>();
		if( user.getUserName().trim().equals("admin")){
			User userInDb = userService.getUserByUserName(user.getUserName());
			boolean isValidUser = (userInDb != null);
			if (!isValidUser) {
				responseMap.put("result", Boolean.FALSE.toString());
				responseMap.put("reason", "抱歉，该用户未加入本系统认证，请联系管理员！");
				return responseMap;
			}

			if (userInDb.getPassword().equals(CommonUtil.md5(user.getPassword()))) {
				LoginUtil.setLogin(request, response, userInDb);
				AuthorityUtil.sync(userInDb.getId(), userService.getAllowedModules(userInDb));
				responseMap.put("result", Boolean.TRUE.toString());
				responseMap.put("reason", "");
				responseMap.put("url", "index");
				return responseMap;
			} else {
				responseMap.put("result", Boolean.FALSE.toString());
				responseMap.put("reason", "用户名或者密码错误！");
				return responseMap;
			}
		}else{
			responseMap.put("result", Boolean.FALSE.toString());
			responseMap.put("reason", "抱歉，该用户未加入本系统认证，请联系管理员！");
			return responseMap;
		}
	}
	
	@ResponseBody
	@RequestMapping("/loginByBoss")
	public Object loginByBoss(User user, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, String> responseMap = new HashMap<String, String>();
		//通过boss登陆
		userService.loginByBoss(user, request, response,responseMap);
		return responseMap;
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		LoginUtil.setLogout(request, response);
		return "redirect:./login";

	}

	@ResponseBody
	@RequestMapping("/getCurrentUserInfo")
	public Object getCurrentUserInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put(Constant.USERNAME, CookieUtil.getCookieValue(request, Constant.USERNAME));
		responseMap.put(Constant.REALNAME, CookieUtil.getCookieValue(request, Constant.REALNAME));
		return responseMap;

	}

	@ResponseBody
	@RequestMapping("/syncAuthority")
	public Object syncAuthority() {
		Map<String, String> responseMap = new HashMap<String, String>();
		userService.syncsAllUsersAuthority();
		responseMap.put("result", "1");
		responseMap.put("info", "同步完成");
		return responseMap;

	}

	@RequestMapping("/userlist")
	public ModelAndView userlist(User userParam) {
		Integer totalCount = userService.count(userParam);
		List<User> userList = userService.list(userParam);
		ModelAndView modelAndView = initModelAndView();
		modelAndView.addObject("totalCount", totalCount);
		modelAndView.addObject("totalPage", PageUtil.getTotalCount(totalCount, userParam.getPageCount()));
		modelAndView.addObject("userList", userList);
		modelAndView.addObject("userParam", userParam);
		modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.用户管理);
		return modelAndView;
	}

	@RequestMapping("/useradd")
	public ModelAndView useradd() {
		ModelAndView modelAndView = initModelAndView();
		modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.用户管理);
		return modelAndView;
	}

	@RequestMapping("/usersave")
	public String usersave(User userParam) {
		if (CommonUtil.isEmpty(userParam.getId())) {
			userParam.setUserSource(UserSource.COMPASS.getValue());
			userService.save(userParam);
		} else {
			userService.update(userParam);
		}
		return "redirect:./userlist";
	}

	@RequestMapping("/usermanage")
	public ModelAndView usermanage(User userParam) {
		User user = userService.getOne(userParam);
		List<RoleLevel> userRoleLevels = roleLevelService.listByUser(userParam);

		HashSet<String> userRolesSet = new HashSet<String>();
		HashSet<String> userRoleLevelsSet = new HashSet<String>();
		for (RoleLevel roleLevel : userRoleLevels) {
			userRoleLevelsSet.add(roleLevel.getId());
		}
		List<Role> roleList = roleService.listAll(new Role());
		for (Role role : roleList) {

			roleService.putSubRoleLevels(role);
		}
		ModelAndView modelAndView = initModelAndView();
		modelAndView.addObject("roleList", roleList);
		modelAndView.addObject("userRolesSet", userRolesSet);
		modelAndView.addObject("userRoleLevelsSet", userRoleLevelsSet);
		modelAndView.addObject("user", user);
		modelAndView.setViewName("useradd");
		modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.用户管理);
		return modelAndView;
	}

	@RequestMapping("/userinfo")
	public ModelAndView userinfo(HttpServletRequest request,User userParam ) {
		userParam.setId( CookieUtil.getCookieValue(request, Constant.USERID));
		User currentUser = userService.getOne(userParam);
		ModelAndView modelAndView = initModelAndView();
		if("POST".equals(request.getMethod())){
		modelAndView.addObject("resCode",this.userService.updatePassword(userParam));
		}
	
		modelAndView.addObject("currentUser", currentUser);
		modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.用户管理);
		return modelAndView;
	}

	@RequestMapping("/userdelete")
	public String userdelete(User userParam) {
		userService.delete(userParam);
		return "redirect:./userlist";
	}

	@ResponseBody
	@RequestMapping("/getTotalByUserName")
	public Object getTotalCountByUsername(User user) {
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("total", this.userService.count(user).toString());
		return responseMap;

	}

	@ResponseBody
	@RequestMapping("/getModulesByUser")
	public Result getModulesByUser(String userName) {
		if(StringUtils.isBlank(userName)){
			return Result.failed(100, "该用户不存在");
		}
		User user = userService.getUserByUserName(userName);
		if(user == null){
			return Result.failed(100, "该用户不存在");
		}

		HashSet<String> modules = userService.getAllowedModules(user);
		return Result.success(modules);
	}

	/**
	 * 龙信登录拦截
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/longxinLogin")
	public Map<String,Object> longxinLogin(HttpServletRequest request, HttpServletResponse response,String userName,String code,String state){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", false);
		result.put("url", "index");
		//如果用户名为空
		if(StringUtils.isEmpty(userName) && StringUtils.isEmpty(code) && StringUtils.isEmpty(state)){
			return result;
		}else if(!StringUtils.isEmpty(userName)){//来自龙信

		}else if(!StringUtils.isEmpty(code) && !StringUtils.isEmpty(state)){//来自企业微信
			userName = HttpClientUtil.doGet(String.format(QY_WECHAT_USERNAME_URL,state,code));
		}
		User user = null;
		if(!StringUtils.isEmpty(userName)){
			 user = userService.getUserByUserName(userName);
		}
		//如果用户不存在
		if (user == null) {
			return result;
		}
		//用户禁用
        if (user.getStatus() == 1){
		    return result;
        }
		LoginUtil.setLogin(request, response, user);
		AuthorityUtil.sync(user.getId(), userService.getAllowedModules(user));
		result.put("result", true);
		return result;
	}

	/**
	 * 依据用户名称获取用户所有信息，前台依据userSource是否等于1 判断是否展示修改按钮的框
	 * @param userName
	 * @return
	 */
	@RequestMapping("getUserByName")
	@ResponseBody
	public User getUserByName(String userName){
		return  userService.getUserByUserName(userName);
	}

	/**
	 * 更改用户密码 提交
	 * @param user
	 * @return
	 */
	@RequestMapping("updateUserPassword")
	@ResponseBody
	public Map<String,Object> updateUserPassword(HttpServletRequest request,HttpServletResponse response,User user){
		Map<String,Object> result = new HashMap<String,Object>();
		//从cookie中取出用户id
		user.setId(CookieUtil.getCookieValue(request, Constant.USERID));
		if(null==user.getId()){
			result.put("success",false);
			return result;
		}
		int i = userService.updatePassword(user);
		if(0==i){
			result.put("success",true);
			LoginUtil.setLogout(request, response);
			return result;
		}else {
			result.put("success",false);
			if(1==i){
				result.put("msg","密码和原始密码均不能为空");
			}else if(2==i){
				result.put("msg","原始密码均不正确");
			}
			return result;
		}
	}

	/**
	 * 用户密码修改页面跳转
	 * @param userParam
	 * @return
	 */
	@RequestMapping("/updatePassword")
	public ModelAndView updatePassword(User userParam) {
		ModelAndView modelAndView = initModelAndView();
		modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.用户管理);
		return modelAndView;
	}

	/**
	 * 用户密码校验
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping("/checkUserPassword")
	@ResponseBody
	public Map<String,Object> checkUserPassword(HttpServletRequest request,User user){
		return  userService.checkUserPassword(request,user);
	}


}
