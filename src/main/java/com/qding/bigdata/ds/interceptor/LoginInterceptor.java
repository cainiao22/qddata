package com.qding.bigdata.ds.interceptor;

import com.alibaba.fastjson.JSON;
import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.component.HessionProxyHandler;
import com.qding.bigdata.ds.config.EnvConfig;
import com.qding.bigdata.ds.enums.UserSource;
import com.qding.bigdata.ds.model.User;
import com.qding.bigdata.ds.service.UserService;
import com.qding.bigdata.ds.util.CommonUtil;
import com.qding.bigdata.ds.util.CookieUtil;
import com.qding.framework.common.AppUser;
import com.qding.manager.strut.response.ManagerBaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<String> excludedUrls;

    private List<String> refererList;

    @Autowired
    private HessionProxyHandler hessionProxyHandler;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(EnvConfig.isEnvLocal()){
            return true;
        }

        String referer = request.getHeader("Referer");
        if(StringUtils.isNotBlank(referer)) {
            for (String s : refererList) {
                if(referer.contains(s)){
                    return true;
                }
            }
        }

        for (String excludedUrl : excludedUrls) {
            if (request.getServletPath().endsWith(excludedUrl)) {
                return true;
            }
        }

        String userId = CookieUtil.getCookieValue(request, Constant.USERID);
        String username = CookieUtil.getCookieValue(request, Constant.USERNAME);
        String saltkey = CookieUtil.getCookieValue(request, Constant.SALTKEY);
        if (CommonUtil.isEmpty(username) || CommonUtil.isEmpty(userId) || !CommonUtil.md5(userId + username).equals(saltkey)) {
            //如果我们没有登录，判断jboss是否登录
            String sessionId = CookieUtil.getCookieValue(request, Constant.SESSION_ID);
            logger.info("从cookie获取的sessionId为：" + sessionId);
            if (StringUtils.isNotBlank(sessionId)) {
                AppUser appUser = getUserInfoFromBoss(request, response, sessionId);
                 /*AppUser appUser = new AppUser();
                appUser.setUsername("test_login");
                appUser.setRealname("测试登录eeee");
                appUser.setMobile("18513705620");*/
                if (appUser == null) {
                    response.sendRedirect(Constant.LOGINPATH);
                    return false;
                }
                request.getSession().setAttribute(sessionId, appUser);
                //updateUserInDB(request, response, appUser);
                StringBuffer redirectUrl = request.getRequestURL();
                if (StringUtils.isNotBlank(request.getQueryString())) {
                    redirectUrl.append("?").append(request.getQueryString());
                }
                //强迫用户从新访问，保证cookie可以设置到客户端
                response.sendRedirect(request.getScheme() + "://" + request.getRequestURL().toString() + "?" + request.getQueryString());
                return false;
            } else {
                //session为空 也不符合，直接强制登录
               /* String target = request.getRequestURI().toString() + "?"
                        + (request.getQueryString() == null ? "" : request.getQueryString());*/

                String ContextPath= request.getContextPath();//获取项目名
                String Scheme=request.getScheme();//协议类型：
                String ServerName=request.getServerName();//域名
                String queryString=request.getQueryString();//?后面的参数
                int ServerPort=request.getServerPort();//端口
                String url="qddata.qdingnet.com";
                String target=StringUtils.substringAfter(request.getRequestURI().toString(),ContextPath);
                    if(ServerName!=url){
                        if(target.isEmpty()||target.equals("/")){
                            response.sendRedirect(Scheme+"://"+ServerName+":"+ServerPort+ContextPath+"/login");
                        }else {
                            if(StringUtils.isEmpty(queryString)){
                                response.sendRedirect(Scheme + "://" + ServerName + ":" + ServerPort + ContextPath + "/login?target=" + target);
                            }else{
                                response.sendRedirect(Scheme + "://" + ServerName + ":" + ServerPort + ContextPath + "/login?target=" + target + "&" + queryString);
                            }
                        }
                    }else{
                        if(target.isEmpty()||target.equals("/")){
                            response.sendRedirect(Scheme+"://"+ServerName+ContextPath+"/login");
                        }else {
                            if(StringUtils.isEmpty(queryString)){
                                response.sendRedirect(Scheme + "://" + ServerName + ContextPath + "/login?target=" + target);
                            }else{
                                response.sendRedirect(Scheme + "://" + ServerName + ContextPath + "/login?target=" + target + "&" + queryString);
                            }
                        }
                    }
                return false;
            }
        }

        return true;
    }

    /**
     * 从boss系统获取用户信息
     * @param request
     * @param response
     * @param sessionId
     * @return
     */
    private AppUser getUserInfoFromBoss(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        AppUser userInfo = (AppUser) request.getSession().getAttribute(sessionId);
        if (userInfo != null) {
            return userInfo;
        }
        logger.info("调用jboss获取用户信息,sissionId:" + sessionId);
        ManagerBaseResponse<AppUser> rpcResponse = null;
        try {
            rpcResponse = hessionProxyHandler.getHessionProxyService().getIPuserRPCService().getAppUserBySessionId(sessionId);
        } catch (Exception e) {
            logger.error("调用boss系统出错重新登录，{}", e);
            return null;
        }
        logger.info("调用jboss获取用户信息返回:" + JSON.toJSONString(rpcResponse));
        if (rpcResponse != null && rpcResponse.getReturnInfo().getCode() != 200) {
            //使用jbos登录失败，让它重新登录
            return null;
        }
        return rpcResponse.getResultObject();
    }

    /**
     * 更新用户信息到我们自己的系统
     *
     * @param request
     * @param response
     * @param appUser
     */
    private void updateUserInDB(HttpServletRequest request, HttpServletResponse response, AppUser appUser) {
        User user = new User();
        user.setRealName(appUser.getRealname());
        user.setUserName(appUser.getUsername());
        user.setPassword(appUser.getMobile());
        //设置用户来源为boss
        user.setUserSource(UserSource.BOSS.getValue());
        userService.updateUserInDB(user, request, response);
    }

    public List<String> getExcludedUrls() {
        return excludedUrls;
    }

    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }

    public List<String> getRefererList() {
        return refererList;
    }

    public void setRefererList(List<String> refererList) {
        this.refererList = refererList;
    }
}
