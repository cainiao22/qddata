package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.service.DataArmorCommonService;
import com.qding.bigdata.ds.util.CommonUtil;
import com.qding.bigdata.ds.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DataArmorCommonServiceImpl implements DataArmorCommonService {

    @Resource(name = "redisCluster")
    private JedisCluster jedisClient;

    @Value("${data.armor.host}")
    private String dataArmorHost;

    public static int SESSION_EXPIRE = 1800;
    public static String TOKEN_KEY = "QD_DATA_ARMOR_TOKEN";
    public static String SET_TOKEN_PREFIX = "USER_DATA_ARMOR_TOKEN";

    /**
     * 获取redis toten，查看是否已登录
     *
     * @param request
     * @return
     */
    @Override
    public boolean getToten(HttpServletRequest request) {
        //1.用户是否登录 加密方式是用户+用户ID+当日时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        //登录key
        String token = CookieUtil.getCookieValue(request, TOKEN_KEY);
        String json = jedisClient.get(SET_TOKEN_PREFIX +":" +date+":" +token);
        if (StringUtils.isBlank(json)) {
            return false;
        }
        //3.重置Session的过期时间
        jedisClient.expire(SET_TOKEN_PREFIX + ":" + date+":" + token, SESSION_EXPIRE);
        return true;
    }

    /**
     * 设置数据盔甲token
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public void setToten(HttpServletRequest request, HttpServletResponse response) {
        String username = CookieUtil.getCookieValue(request, "username");
        String userId = CookieUtil.getCookieValue(request, "userId");

        //2.生成token,token加密方式是用户+用户ID+当日时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());//为获取当前系统时间
        //3.加密token
        String token = CommonUtil.md5(username + userId + date);
        //4.设置redis
        jedisClient.set(SET_TOKEN_PREFIX + ":" + date + ":" + token, username);
        jedisClient.expire(SET_TOKEN_PREFIX + ":" + date + ":" + token, SESSION_EXPIRE);

    }

    @Override
    public String getDataArmorHost() {
        return dataArmorHost;
    }


}
