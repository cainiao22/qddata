package com.qding.bigdata.ds.util;

import com.alibaba.fastjson.JSON;
import com.qding.bigdata.ds.cache.RedisCache;
import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.config.EnvConfig;
import com.qding.framework.common.codis.JedisClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorityUtil extends TagSupport implements ApplicationContextAware {

    /**
     *
     */
    private static final long serialVersionUID = -9184810721635487176L;
    private static String PREFIX = RedisCache.PREFIX + "USER_MODULES:";
    private static ApplicationContext context;
    private static RedisCache redisCache;
    private String module;
    private static Logger logger = LoggerFactory.getLogger(AuthorityUtil.class);

    @Override
    public int doStartTag() throws JspException {
        if (hasAuthority(module)) {
            return TagSupport.EVAL_BODY_INCLUDE;
        } else {
            return TagSupport.SKIP_BODY;
        }
    }

    public static Boolean hasAuthorityRegex(String module){
        if(EnvConfig.isEnvLocal()){
            return true;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        String username =CookieUtil.getCookieValue(request, Constant.USERNAME);
        String userId =CookieUtil.getCookieValue(request, Constant.USERID);
        if(StringUtils.isBlank(username)){
            return false;
        }
        Set<String> moduleSet = redisCache.get(PREFIX + userId, Set.class);
        logger.info("userId:" + userId);
        logger.info("module tree:" + JSON.toJSONString(moduleSet));
        logger.info("module name:" + module);
        if (moduleSet == null) {
            return false;
        }
        for (String s : moduleSet) {
            Pattern p = Pattern.compile(s);
            Matcher matcher = p.matcher(module);
            if(matcher.find()){
                return true;
            }
        }
        return false;
    }

    public static Boolean hasAuthority(String module) {
		if(EnvConfig.isEnvLocal()){
            return true;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        
        String username =CookieUtil.getCookieValue(request, Constant.USERNAME);
        String userId =CookieUtil.getCookieValue(request, Constant.USERID);
        if(StringUtils.isBlank(username)){
          return false;
        }
        Set<String> moduleSet = redisCache.get(PREFIX + userId, Set.class);
        logger.info("userId:" + userId);
        logger.info("module tree:" + JSON.toJSONString(moduleSet));
        logger.info("module name:" + module);
        if (moduleSet == null) {
            return false;
        }
        if(moduleSet.contains(module.replace("/", ""))){
            return true;
        }
        return false;
    }

    public static Boolean hasModules(String userId) {
        if(EnvConfig.isEnvLocal()){
            return true;
        }
        Set<String> moduleSet = redisCache.get(PREFIX + userId, Set.class);
        return moduleSet != null;
    }

    public static Boolean isLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String username =CookieUtil.getCookieValue(request, Constant.USERNAME);

        return !(username == null || username.isEmpty());

    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public static void sync(String userid, HashSet<String> pathSet) {

        redisCache.evict(PREFIX + userid);
        redisCache.put(PREFIX + userid,  pathSet);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AuthorityUtil.context = applicationContext;
        redisCache = (RedisCache) applicationContext.getBean("redisCache");
        PREFIX = RedisCache.PREFIX + "USER_MODULES:";
    }
}
