package com.qding.bigdata.ds.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface DataArmorCommonService {

    /**
     * 获取redis toten，查看是否已登录
     * @param request
     * @return
     */
    boolean getToten(HttpServletRequest request);

    /**
     * 设置数据盔甲token
     * @param request
     * @param response
     * @return
     */
    void setToten(HttpServletRequest request,HttpServletResponse response);

    String getDataArmorHost();


}
