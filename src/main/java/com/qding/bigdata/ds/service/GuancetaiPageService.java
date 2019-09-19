package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.*;

import java.util.List;

/**
 * @author yanpf
 * @date 2018/12/27 15:08
 * @description
 */
public interface GuancetaiPageService {

    String INDEX_GCT_PAGE = "gct_page.";

    /**
     * 获取业务线总的停留时长
     * @param param
     * @return
     */
    GuancetaiQuotaInfo getBusinessOnlineTime(GuancetaiQuotaParam param);

    /**
     * 获取页面访问次数
     * @param param
     * @return
     */
    GuancetaiQuotaInfo getPageStartupTimesInfo(GuancetaiQuotaParam param);

    /**
     * 获取页面访问用户数
     * @param param
     * @return
     */
    GuancetaiQuotaInfo getPageStartupUsersInfo(GuancetaiQuotaParam param);

    /**
     * 获取业务线总的停留时长趋势图
     * @param param
     * @return
     */
    List<GuancetaiQuotaTrendItem> getBusinessOnlineTimeTrend(GuancetaiPageQuotaParam param);

    /**
     * 获取页面访问用户数趋势图
     * @param param
     * @return
     */
    List<GuancetaiQuotaTrendItem> getPageStartupUsersInfoTrend(GuancetaiPageQuotaParam param);

    /**
     * 获取页面访问次数趋势图
     * @param param
     * @return
     */
    List<GuancetaiQuotaTrendItem> getPageStartupTimesInfoTrend(GuancetaiPageQuotaParam param);

    /**
     * 获取页面访问汇总统计列表
     * @param param
     * @return
     */
    List<GuancetaiPageDetail> getGuancetaiPageDetailHuiZongList(GuancetaiPageQuotaParam param) throws Exception;

    /**
     * 获取页面访问详情统计列表
     * @param param
     * @return
     */
    List<GuancetaiPageDetail> getGuancetaiPageDetailList(GuancetaiPageQuotaParam param) throws Exception;
}
