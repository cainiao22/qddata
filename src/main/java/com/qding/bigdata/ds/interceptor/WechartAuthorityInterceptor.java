package com.qding.bigdata.ds.interceptor;


import com.qding.bigdata.ds.config.EnvConfig;
import com.qding.bigdata.ds.dao.WechartAuthorityDao;
import com.qding.bigdata.ds.model.User;
import com.qding.bigdata.ds.model.WechartAuthority;
import com.qding.bigdata.ds.service.UserService;
import com.qding.bigdata.ds.util.AuthorityUtil;
import com.qding.bigdata.ds.util.HttpClientUtil;
import com.qding.bigdata.ds.util.LoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @author yanpf
 * @date 2018/12/13 9:49
 * @description 日月报权限拦截
 */
public class WechartAuthorityInterceptor extends HandlerInterceptorAdapter implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ERROR_PAGE = "/assets/statics/common/403.html";

    private static final String APPID = "wxd23d0632ad28c805";

    private static final String WECHART_CODE_GET_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
    private static final String WECHAT_OPENID_GET_URL = "http://m7-vm-bd-66:12051/getOpenIDByCode?code=%s";

    private String authorityFlag;

    private WechartAuthorityDao wechartAuthorityDao;

    private UserService userService;

    public void setAuthorityFlag(String authorityFlag) {
        this.authorityFlag = authorityFlag;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(EnvConfig.isEnvLocal()){
            return true;
        }
        String code = request.getParameter("code");
        if(StringUtils.isEmpty(code)){
            String encodedUrl = URLEncoder.encode("https://m2.iqdnet.com/bigdata" + request.getServletPath() + "?" + request.getQueryString(),"UTF-8");
            String redirectUrl = String.format(WECHART_CODE_GET_URL, APPID, encodedUrl);
            logger.info("redirectURL:{}", redirectUrl);
            response.sendRedirect(redirectUrl);
            return false;
        }

        String openId = HttpClientUtil.doGet(String.format(WECHAT_OPENID_GET_URL, code));

        if(StringUtils.isEmpty(openId)){
            response.sendRedirect(ERROR_PAGE);
            return false;
        }

        WechartAuthority authority = wechartAuthorityDao.getWechartAuthority(openId, authorityFlag);
        if(authority != null){
            User user = userService.getUserByUserName(authority.getUserName());
            if(user != null){
                LoginUtil.setLogin(request, response, user);
                AuthorityUtil.sync(user.getId(), userService.getAllowedModules(user));
                logger.info("pass openId:", openId);
                return true;
            }
        }
        logger.info("invalid openId:", openId);
        response.sendRedirect(ERROR_PAGE);
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        wechartAuthorityDao = applicationContext.getBean(WechartAuthorityDao.class);
        userService = applicationContext.getBean(UserService.class);
    }
}
