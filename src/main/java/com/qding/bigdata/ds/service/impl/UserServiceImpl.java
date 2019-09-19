package com.qding.bigdata.ds.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qding.bigdata.ds.util.*;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.UserDao;
import com.qding.bigdata.ds.model.Module;
import com.qding.bigdata.ds.model.RoleLevel;
import com.qding.bigdata.ds.model.User;
import com.qding.bigdata.ds.model.UserRoleLevel;
import com.qding.bigdata.ds.service.ModuleService;
import com.qding.bigdata.ds.service.RoleLevelService;
import com.qding.bigdata.ds.service.UserRoleLevelService;
import com.qding.bigdata.ds.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    public static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final Pattern resultPat = Pattern.compile("^callback\\((.*)\\)");
    
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleLevelService roleLevelService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private UserRoleLevelService userRoleLevelService;

	@Override
	public BaseDao<User> getDao() {
		return userDao;
	}

	@Override
	public void save(User user) {
		if (StringUtils.isNotBlank(user.getPassword())) {
			user.setPassword(CommonUtil.md5(user.getPassword()));
		}
		super.save(user);
	}

	@Override
	public void update(User user) {
		if (StringUtils.isNotBlank(user.getPassword())) {
			user.setPassword(CommonUtil.md5(user.getPassword()));
		}
		if (user.getRolesStr() != null) {
			setUserRole(user);
		}
		super.update(user);
	}

	private void setUserRole(User user) {
		userRoleLevelService.delete(new UserRoleLevel(null, user.getId()));
		String rolesStr = user.getRolesStr();
		if (CommonUtil.isEmpty(rolesStr)) {
			return;
		}
		String[] roleSplit = rolesStr.split(",");
		for (String roleId : roleSplit) {
			userRoleLevelService.save(new UserRoleLevel(roleId, user.getId()));
		}
	}

	@Override
	public HashSet<String> getAllowedModules(User user) {
		HashSet<String> pathSet = new HashSet<String>();
		List<RoleLevel> listRoleByUser = roleLevelService.listByUser(user);
		List<RoleLevel> subRoleLevels = new ArrayList<RoleLevel>();

		for (RoleLevel roleLevel : listRoleByUser) {
			subRoleLevels.addAll(this.getSubRoleLevels(roleLevel));

		}
		listRoleByUser.addAll(subRoleLevels);
		for (RoleLevel roleLevel : listRoleByUser) {

			List<Module> listModuleByRole = moduleService.listByRoleLevel(roleLevel);
			for (Module module : listModuleByRole) {
				String[] pathsplit = module.getModuleUrl().split(",");
				for (String path : pathsplit) {
					pathSet.add(path);
				}

			}

		}
		return pathSet;
	}

	private List<RoleLevel> getSubRoleLevels(RoleLevel roleLevel) {
		return roleLevelService.getSubRoleLevels(roleLevel);
	}

	@Override
	public void syncsAllUsersAuthority() {
		List<User> allusers = listAll(new User());
		for (User user : allusers) {
			AuthorityUtil.sync(user.getId(), this.getAllowedModules(user));
		}

	}

	@Override
	public void delete(User user) {
		userRoleLevelService.delete(new UserRoleLevel(null, user.getId()));
		super.delete(user);
	}

	@Override
	public User getUserByUserName(String userName) {
		User userParam = new User();
		userParam.setUserName(userName);
		List<User> userList = this.list(userParam);
		return userList.isEmpty() ? null : userList.get(0);
	}

	@Override
	public List<User> listByRoleLevel(RoleLevel roleLevel) {
		Set<String> ids = new HashSet<String>();
		List<UserRoleLevel> userRoleLevelList = userRoleLevelService
				.listAll(new UserRoleLevel(roleLevel.getId(), null));
		for (UserRoleLevel userRoleLevel : userRoleLevelList) {
			ids.add(userRoleLevel.getUserId());
		}
		return this.listByIDs(ids);
	}

	@Override
	public int updatePassword(User userParam) {

		if (StringUtils.isBlank(userParam.getPassword()) || StringUtils.isBlank(userParam.getNewPassword())) {
			return 1;
		}

		User user = this.getOne(userParam);
		if (!user.getPassword().equals(CommonUtil.md5(userParam.getPassword()))) {
			// 原始密码不符
			return 2;
		}
		user.setPassword(userParam.getNewPassword());
		this.update(user);
		return 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void loginByBoss(User user, HttpServletRequest request,HttpServletResponse response, Map<String, String> responseMap) throws Exception {
		
		String originPasswd = user.getPassword();
		String responseStr = "";
		
		Map<String,String> params = new HashMap<String,String>();
		params.put(Constant.BOSS_LOGIN_ACCOUNT, user.getUserName());
		params.put(Constant.BOSS_LOGIN_PASSWORD, user.getPassword());
		params.put(Constant.BOSS_LOGIN_ORGTYPEFLAG_KEY, Constant.BOSS_LOGIN_ORGTYPEFLAG);
		params.put(Constant.BOSS_LOGIN_CALLBACK,Constant.BOSS_LOGIN_CALLBACK);
		//请求自动重试
		for(int i=1;i<=Constant.HTTP_RETRY_NUM;i++){
			try{
				responseStr = HttpUtil.post(Constant.BOSS_LOGIN_URL, params);
				break;
			}catch(Exception e){
		        logger.error("HttpUtil has retryed "+i+" times...,request url:" + Constant.BOSS_LOGIN_URL + " and params:" + params);
		        logger.error(e.toString());
		        Thread.sleep(Constant.HTTP_RETRY_NUM);
				continue;
			}
		}
		Matcher matcher = resultPat.matcher(responseStr);
		Map<String, Object> map = null;
		if(matcher.find()){
            HashMap<String, Object> hashMap = (HashMap<String, Object>) objectMapper.readValue(matcher.group(1).toString(), Map.class);
			map = hashMap;
			String code = "";
			if(null!=map.get("code")){
				code = map.get("code").toString();
			}
            if(Constant.BOSS_LOGIN_SUCCESS_CODE.equals(code)){

            	map =(HashMap<String, Object>)  map.get("data");
            	map =(HashMap<String, Object>)  map.get("entity");
    			
    			//检查用户是否存在DS数据库
				user.setPassword(originPasswd);
				user.setRealName(map.get("realname").toString());
				user.setStatus(0);
				//同步用户信息
				this.updateUserInDB(user, request, response);
    			responseMap.put("result", Boolean.TRUE.toString());
    			responseMap.put("reason", "");
    			responseMap.put("url", "index");
            }else{
				String reason = "";
				if(null!=map.get("message")){
					reason = map.get("message").toString();
				}
				responseMap.put("result", Boolean.FALSE.toString());
				responseMap.put("reason", reason);
            }
		}else{
			responseMap.put("result", Boolean.FALSE.toString());
			responseMap.put("reason", "登陆失败，请联系管理员");
		}
	}

	@Override
	public void updateUserInDB(User user, HttpServletRequest request, HttpServletResponse response) {
		User userInDb = getUserByUserName(user.getUserName());
		if(null == userInDb){
			//如果用户没有状态，则默认为0-正常
			if(user.getStatus()==null){
				user.setStatus(0);
			}
            user.setRealName(user.getRealName());
            save(user);
            //分配普通用户权限
            String roleLevelId = "";
			RoleLevel roleLevel = roleLevelService.getByEnName("common_member");
			if(null != roleLevel){
                roleLevelId =roleLevel.getId();
            }
            userInDb = getUserByUserName(user.getUserName());
            UserRoleLevel userRoleLevel = new UserRoleLevel(roleLevelId,userInDb.getId());
            userRoleLevelService.save(userRoleLevel);

        }else{
			//更新时不更新用户来源
			userInDb.setUserSource(null);
            userInDb.setRealName(user.getRealName());
            userInDb.setPassword(user.getPassword());
            update(userInDb);
        }

		LoginUtil.setLogin(request, response, userInDb);
		AuthorityUtil.sync(userInDb.getId(), getAllowedModules(userInDb));
	}

	/**
	 * 校验用户密码
	 * @param user
	 * @return
	 */
	@Override
	public Map<String, Object> checkUserPassword(HttpServletRequest request,User user) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success",false);
		if(StringUtils.isEmpty(user.getPassword())){
			result.put("msg","请输入原密码");
			return result;
		}
		String username = CookieUtil.getCookieValue(request, Constant.USERNAME);
		//如果能获取到用户名
		if(!StringUtils.isEmpty(username) && !"undefined".equals(username)){
			User userOld = this.getUserByUserName(username);
			if(null!=userOld){
				if(!CommonUtil.md5(user.getPassword()).equals(userOld.getPassword())){
					result.put("msg","请输入正确的原密码");
					return  result;
				}else{
					/*if(StringUtils.isEmpty(user.getNewPassword())){
						result.put("msg","请输入新的密码");
						return result;
					}*/
					result.put("success",true);
				}

			}else{
				result.put("msg","用户未登录，请重新登陆");
				return  result;
			}
		}else{
			result.put("msg","用户未登录，请重新登陆");
		}
		return result;
	}
}
