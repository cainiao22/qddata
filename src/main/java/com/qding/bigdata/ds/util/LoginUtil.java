package com.qding.bigdata.ds.util;

import java.net.URLEncoder;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.model.User;

public class LoginUtil {
  public static String INITCTX = "com.sun.jndi.ldap.LdapCtxFactory";
  /*
   * Host name and port number of LDAP server
   */
  public static String MY_SERVICE = "LDAP://ldap.sohu-inc.com:389";

  public static boolean usercheck(String username, String password) {
    Hashtable<String, String> env = new Hashtable<String, String>();
    env.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
    env.put(Context.PROVIDER_URL, MY_SERVICE);
    env.put(Context.SECURITY_AUTHENTICATION, "simple");

    env.put(Context.SECURITY_PRINCIPAL, username);
    env.put(Context.SECURITY_CREDENTIALS, password);
    env.put("com.sun.jndi.ldap.connect.pool.timeout", "3000");
    DirContext ctx = null;
    try {
      ctx = new InitialDirContext(env);
      return true;
    } catch (NamingException e) {
      return false;
    } finally {
      ctx = null;
    }
  }

  public static void setLogin(HttpServletRequest request, HttpServletResponse response, User user) {
    CookieUtil.setCookie(response,Constant.USERID, user.getId());
    CookieUtil.setCookie(response,Constant.USERNAME, user.getUserName());
    CookieUtil.setCookie(response,Constant.REALNAME,URLEncoder.encode( user.getRealName()));
    CookieUtil.setCookie(response,Constant.SALTKEY, CommonUtil.md5(user.getId()+user.getUserName()));
  }

  public static void setLogout(HttpServletRequest request,HttpServletResponse response) {
    request.getSession().invalidate();
    CookieUtil.removeCookie(response, CookieUtil.getCookie(request, Constant.USERID));
    CookieUtil.removeCookie(response, CookieUtil.getCookie(request, Constant.USERNAME));
    CookieUtil.removeCookie(response, CookieUtil.getCookie(request, Constant.REALNAME));
    CookieUtil.removeCookie(response, CookieUtil.getCookie(request, Constant.SALTKEY));

  }
}
