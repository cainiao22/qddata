package com.qding.bigdata.ds.interceptor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qding.bigdata.ds.config.EnvConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.model.User;
import com.qding.bigdata.ds.service.UserService;
import com.qding.bigdata.ds.service.impl.UserServiceImpl;
import com.qding.bigdata.ds.util.AuthorityUtil;
import com.qding.bigdata.ds.util.CookieUtil;
import com.qding.bigdata.ds.util.LoginUtil;

public class AuthorityInterceptor extends HandlerInterceptorAdapter {
	private List<String> excludedUrls;

	private Logger logger = LoggerFactory.getLogger(AuthorityUtil.class);

	@SuppressWarnings("deprecation")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(EnvConfig.isEnvLocal()){
			return true;
		}
		String servletPath = request.getServletPath().replace("/", "");
		for (String excludedUrl : excludedUrls) {
			Pattern pattern = Pattern.compile(excludedUrl);
			Matcher matcher = pattern.matcher(servletPath);
			boolean b = matcher.find();
			if(b){
				return true;
			}
		}
		String userId = CookieUtil.getCookieValue(request, Constant.USERID);

		if (StringUtils.isNotBlank(userId) && !AuthorityUtil.hasModules(userId)) {
			UserService userService = RequestContextUtils.getWebApplicationContext(request)
					.getBean(UserServiceImpl.class);
			User userParam = new User();
			userParam.setId(userId);
			User user = userService.getOne(userParam);
			if (user == null) {
				return false;
			}
			LoginUtil.setLogin(request, response, user);
			AuthorityUtil.sync(userId, userService.getAllowedModules(user));

		}

		if (AuthorityUtil.hasAuthority(servletPath)) {
			return true;
		}
		String notAllowed = request.getContextPath() + "/notallowed.html";
		logger.info(notAllowed);
		logger.info("servletPath:" + servletPath);
		response.sendRedirect(notAllowed);
		return false;

	}

	public List<String> getExcludedUrls() {
		return excludedUrls;
	}

	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

}
