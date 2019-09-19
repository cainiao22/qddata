package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.*;

import java.util.List;

/**
 * @author yanpf
 * @date 2018/12/27 18:28
 * @description
 */
public interface GuancetaiEventService {
    String INDEX_GCT_EVENT = "gct_event.";

    /**
     * 获取事件发生次数
     * @param param
     * @return
     */
    GuancetaiQuotaInfo getEventStartupTimesInfo(GuancetaiQuotaParam param);

    /**
     * 获取事件发生人数
     * @param param
     * @return
     */
    GuancetaiQuotaInfo getEventStartupUsersInfo(GuancetaiQuotaParam param);

    /**
     * 获取事件发生次数趋势图
     * @param param
     * @return
     */
    List<GuancetaiQuotaTrendItem> getEventStartupTimesInfoTrend(GuancetaiEventQuotaParam param);

    /**
     * 获取事件发生人数趋势图
     * @param param
     * @return
     */
    List<GuancetaiQuotaTrendItem> getEventStartupUsersInfoTrend(GuancetaiEventQuotaParam param);

    /**
     * 获取事件访问汇总统计列表
     * @param param
     * @return
     */
    List<GuancetaiEventDetail> getGuancetaiEventDetailHuiZongList(GuancetaiEventQuotaParam param) throws Exception;

    /**
     * 获取事件访问详情统计列表
     * @param param
     * @return
     */
    List<GuancetaiEventDetail> getGuancetaiEventDetailList(GuancetaiEventQuotaParam param) throws Exception;
}
