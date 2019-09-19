package com.qding.bigdata.ds.scheduled;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qding.bigdata.ds.controller.SalesOpportunitiesController;
import com.qding.bigdata.ds.controller.UserProfileV2Controller;
import com.qding.bigdata.ds.service.MisUserService;
import com.qding.bigdata.ds.service.UserProfileV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class TimingTasks {

    @Autowired
    private MisUserService misUserService;
    @Autowired
    private UserProfileV2Service userProfileV2Service;

    /**
     * 初次同步mis账户至北斗星
     */
    //@Scheduled(cron = "0 0 */13 * * ?")
    public  void addUserByScheduledFirst(){
        Long start = new Date().getTime();
        System.out.println("定时任务执行"+start);
        System.out.println(misUserService.addDsUserByMis());
        Long end = new Date().getTime();
        System.out.println("定时任务执行结束,用时："+(end-start)+"毫秒");
    }

    /**
     * 定时任务同步mis账户至北斗星
     */
    @Scheduled(cron = "0 25 1 * * ?")
    public  void addUserByScheduled(){
        Long start = new Date().getTime();
        System.out.println("定时任务同步mis账户至北斗星执行"+start);
        System.out.println(misUserService.addOrUpdateDsUserByMisScheduled(null,null));
        Long end = new Date().getTime();
        System.out.println("定时任务同步mis账户至北斗星执行结束,用时："+(end-start)+"毫秒");
    }

//    /**
//     * 定时任务同步mis账户至北斗星
//     */
//    @Scheduled(cron = "0 0 10 * * ?")
//    public  void addSalesOpportunitiesByScheduled(){
//        Long start = new Date().getTime();
//        System.out.println("定时任务同步销售易销售机会数据到DS库执行"+start);
//        SalesOpportunitiesController sc = new SalesOpportunitiesController();
//        try{
//            sc.saveCrmOpportunity();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        Long end = new Date().getTime();
//        System.out.println("定时任务同步销售易销售机会数据到DS库执行结束,用时："+(end-start)+"毫秒");
//    }


    /**
     * 每天初始化缓存一次用户画像-概览指标数据
     */
//    @Scheduled(cron = "0 0 1 * * ?")
//    @PostConstruct
//    public  void getUserPortraitSummary(){
//        Long start = new Date().getTime();
//        userProfileV2Service.putCacheAllUserOverview();
////        userProfileV2Service.putCacheCUserOverview();
////        userProfileV2Service.putCachePUserOverview();
//        Long end = new Date().getTime();
//        System.out.println("每天初始化缓存一次用户画像-概览-偏好指标数据执行结束,用时："+(end-start)+"毫秒");
//    }
}
