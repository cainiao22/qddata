package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.GuancetaiQuotaInfo;
import com.qding.bigdata.ds.model.GuancetaiQuotaParam;
import com.qding.bigdata.ds.model.GuancetaiQuotaTrendItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author yanpf
 * @date 2018/12/26 11:47
 * @description
 */
public interface GuancetaiBaseService {

    String INDEX_GCT_BASE = "gct_base.";

    //String INDEX_GCT_EVENT = "gct_event.";

    /**
     * 获取业务线总的停留时长
     * @param param
     * @return
     */
    GuancetaiQuotaInfo getBusinessOnlineTime(GuancetaiQuotaParam param);

    /**
     * 获取业务线总的停留时长趋势图
     * @param param
     * @return
     */
    List<GuancetaiQuotaTrendItem> getBusinessOnlineTimeTrend(GuancetaiQuotaParam param);

    /**
     * 获取启动次数
     * @return
     * @param param
     */
    GuancetaiQuotaInfo getStartupTimesInfo(GuancetaiQuotaParam param);

    /**
     * 获取启动用户数
     * @param param
     * @return
     */
    GuancetaiQuotaInfo getStartupUsersInfo(GuancetaiQuotaParam param);

    /**
     * 获取活跃用户趋势图
     * @param param
     * @return
     */
    List<GuancetaiQuotaTrendItem> getActiveUsersInfoTrend(GuancetaiQuotaParam param);

    /**
     * 获取启动次数趋势图
     * @param param
     * @return
     */
    List<GuancetaiQuotaTrendItem> getStartupTimesInfoTrend(GuancetaiQuotaParam param);

    /**
     * 获取启动用户数趋势图
     * @param param
     * @return
     */
    List<GuancetaiQuotaTrendItem> getStartupUsersInfoTrend(GuancetaiQuotaParam param);
}
