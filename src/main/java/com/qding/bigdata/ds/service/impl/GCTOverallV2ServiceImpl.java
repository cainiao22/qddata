package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.GCTApplicationInfoV2CubeDao;
import com.qding.bigdata.ds.dao.GCTOverallV2Dao;
import com.qding.bigdata.ds.model.GCTBaseV2Param;
import com.qding.bigdata.ds.model.GCTOverallV2Param;
import com.qding.bigdata.ds.service.GCTOverallV2Service;
import com.qding.bigdata.ds.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/13 下午8:55
 */
@Service
public class GCTOverallV2ServiceImpl implements GCTOverallV2Service {
    DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
    String INDEX_GCT_BASE = "gct_base.";

    GCTOverallV2Dao gctOverallV2Dao;
    GCTApplicationInfoV2CubeDao gctApplicationInfoCubeDao;

    @Resource(name = "esSqlSession")
    public void initExecutorDao(SqlSession sqlSession) {
        this.gctOverallV2Dao = sqlSession.getMapper(GCTOverallV2Dao.class);
        this.gctApplicationInfoCubeDao = sqlSession.getMapper(GCTApplicationInfoV2CubeDao.class);
    }

    @Override
    public Map<String, List<Map<String, Object>>> getOverallAnalysis(List<GCTOverallV2Param> paramList) {
        Map<String, List<Map<String, Object>>> ret = new HashMap<>();
        Date currentDate = new Date();
        if (paramList != null) {
            GCTBaseV2Param param = paramList.get(0);
            switch (param.getDateType()) {
                case "day":
                    //仅包含实时
                    if (DateUtil.compareDate(currentDate, param.getEndDate()) == 0 && DateUtil.compareDate(currentDate, param.getStartDate()) == 0) {
                       return getOverallAnalysisByRealTime(ret,paramList);
                    }

                    //离线
                    if (DateUtil.compareDate(currentDate, param.getStartDate()) > 0) {
                        ret = getOverallAnalysisByOffline(ret,paramList);
                    }

                    //包含实时
                    if (DateUtil.compareDate(currentDate, param.getEndDate()) <= 0) {
                        Map<String, List<Map<String, Object>>> overallAnalysisByRealTime = getOverallAnalysisByCurDay(paramList);
                        mergeDayData(overallAnalysisByRealTime, ret,LocalDate.now().toString());
                    }
                    fillingDate2(ret, param,GCTOverallV2Param.getPreviousDay(param));
                    break;
                case "month":
                    ret = getOverallAnalysisByOffline(ret,paramList);
                    fillingMonth2(ret, param,GCTOverallV2Param.getPreviousMonth(param));
                    break;
            }
            //前端需要的type参数用户小时展示确定展示逻辑
            List<Map<String, Object>> type = new ArrayList<>();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("type", false);
            type.add(hashMap);
            ret.put("type", type);
        }
        return ret;
    }


    private Map<String, List<Map<String, Object>>> getOverallAnalysisByCurDay( List<GCTOverallV2Param> paramList) {
        Map<String, List<Map<String, Object>>> ret = new HashMap<>();
        for (int i = 0; i < paramList.size(); i++) {
            GCTOverallV2Param gctOverallV2Param = paramList.get(i);
            Date start = gctOverallV2Param.getStartDate();
            Date end = gctOverallV2Param.getEndDate();
            gctOverallV2Param.setStartDate(new Date());
            gctOverallV2Param.setEndDate(new Date());
            gctOverallV2Param.setEsIndex(getEsIndex(GCT_BASE, new Date()));
            List<Map<String, Object>> entryList = new ArrayList<>();
            switch (gctOverallV2Param.getAction()) {
                case "ding_reg_new":
                    entryList = gctOverallV2Dao.getRegAnalysisCur(gctOverallV2Param);
                    break;
                case "active":
                    entryList = gctOverallV2Dao.getActiveAnalysisCur(gctOverallV2Param);
                    break;
                case "app_startup":
                    entryList = gctOverallV2Dao.getStartupAnalysisCur(gctOverallV2Param);
                    break;
                case "per_start":
                    entryList = gctOverallV2Dao.getPreStartupAnalysisCur(gctOverallV2Param);
                    break;
                case "per_often":
                    entryList = gctOverallV2Dao.getPreVisitAnalysisCur(gctOverallV2Param);
                    break;
                default:
            }
            //表格对比只展示第一个
            if (!ret.containsKey("table")) {
                final List<Map<String, Object>> rettable = gctOverallV2Dao.getTableAnalysisCur(gctOverallV2Param);
                if(CollectionUtils.isNotEmpty(rettable)){
                    ret.put("table", rettable);
                }
            }

            if(paramList.size()>1){
                String entry = "entry"+i;
                ret.put(entry, entryList);
            }else{
                ret.put("entry", entryList);
            }
            gctOverallV2Param.setStartDate(start);
            gctOverallV2Param.setEndDate(end);
        }
        return ret;
    }



    /**
     * 处理选定时间范围的多个查询，和环比上次个时间范围
     * @param ret
     * @param paramList
     * @return
     */
    private Map<String, List<Map<String, Object>>> getOverallAnalysisByOffline(Map<String, List<Map<String, Object>>> ret,List<GCTOverallV2Param> paramList) {
        for (int i = 0; i < paramList.size(); i++) {
            GCTOverallV2Param gctOverallV2Param = paramList.get(i);
            GCTOverallV2Param previousParam = null;
            if("day".equals(gctOverallV2Param.getDateType())){
                previousParam =  GCTOverallV2Param.getPreviousDay(gctOverallV2Param);
            }else if("month".equals(gctOverallV2Param.getDateType())){
                previousParam =  GCTOverallV2Param.getPreviousMonth(gctOverallV2Param);
            }
            String entry = "entry";
            entry = entry + i;
            List<Map<String, Object>> entryList = new ArrayList<>();
            List<Map<String, Object>> pEntryList = new ArrayList<>();
            switch (gctOverallV2Param.getAction()) {
                case "ding_reg_new":
                    entryList = gctApplicationInfoCubeDao.getRegAnalysis(gctOverallV2Param);
                    if(paramList.size() < 2){
                        pEntryList = gctApplicationInfoCubeDao.getRegAnalysis(previousParam);
                    }
                    break;
                case "active":
                    entryList = gctApplicationInfoCubeDao.getActiveAnalysis(gctOverallV2Param);
                    if(paramList.size() < 2){
                        pEntryList = gctApplicationInfoCubeDao.getActiveAnalysis(previousParam);
                    }

                    break;
                case "app_startup":
                    entryList = gctApplicationInfoCubeDao.getStartupAnalysis(gctOverallV2Param);
                    if(paramList.size() < 2){
                        pEntryList = gctApplicationInfoCubeDao.getStartupAnalysis(previousParam);
                    }
                    break;
                case "per_start":
                    entryList = gctApplicationInfoCubeDao.getPreStartupAnalysis(gctOverallV2Param);
                    if(paramList.size() < 2){
                        pEntryList = gctApplicationInfoCubeDao.getPreStartupAnalysis(previousParam);
                    }
                    break;
                case "per_often":
                    entryList = gctApplicationInfoCubeDao.getPreVisitAnalysis(gctOverallV2Param);
                    if(paramList.size() < 2){
                        pEntryList = gctApplicationInfoCubeDao.getPreVisitAnalysis(previousParam);
                    }
                    break;
                default:
            }
            //表格
            if (!ret.containsKey("table")) {
                final List<Map<String, Object>> rettable = gctApplicationInfoCubeDao.getTableAnalysis(gctOverallV2Param);
                ret.put("table", rettable);
            }
            if(paramList.size() == 1){
                ret.put("entry", entryList);
                ret.put("pEntry", pEntryList);
            }else{
                ret.put(entry, entryList);
            }
        }
        return ret;
    }

    /*private Map<String, List<Map<String, Object>>> switchMethod(GCTOverallV2Param gctOverallV2Param,GCTOverallV2Param previousParam,List<Map<String, Object>> entryList,List<Map<String, Object>> pEntryList){
        switch (gctOverallV2Param.getAction()) {
            case "ding_reg_new":
                entryList = gctApplicationInfoCubeDao.getRegAnalysis(gctOverallV2Param);
                if(paramList.size() < 2){
                    pEntryList = gctApplicationInfoCubeDao.getRegAnalysis(previousParam);
                }
                break;
            case "active":
                entryList = gctApplicationInfoCubeDao.getActiveAnalysis(gctOverallV2Param);
                if(paramList.size() < 2){
                    pEntryList = gctApplicationInfoCubeDao.getActiveAnalysis(previousParam);
                }

                break;
            case "app_startup":
                entryList = gctApplicationInfoCubeDao.getStartupAnalysis(gctOverallV2Param);
                if(paramList.size() < 2){
                    pEntryList = gctApplicationInfoCubeDao.getStartupAnalysis(previousParam);
                }
                break;
            case "per_start":
                entryList = gctApplicationInfoCubeDao.getPreStartupAnalysis(gctOverallV2Param);
                if(paramList.size() < 2){
                    pEntryList = gctApplicationInfoCubeDao.getPreStartupAnalysis(previousParam);
                }
                break;
            case "per_often":
                entryList = gctApplicationInfoCubeDao.getPreVisitAnalysis(gctOverallV2Param);
                if(paramList.size() < 2){
                    pEntryList = gctApplicationInfoCubeDao.getPreVisitAnalysis(previousParam);
                }
                break;
            default:
        }
    }*/

    private Map<String, List<Map<String, Object>>> getOverallAnalysisByRealTime(Map<String, List<Map<String, Object>>> ret, List<GCTOverallV2Param> paramList) {
        for (int i = 0; i < paramList.size(); i++) {
            GCTOverallV2Param gctOverallV2Param = paramList.get(i);
            gctOverallV2Param.setEsIndex(getEsIndex(GCT_BASE, gctOverallV2Param.getStartDate()));
            GCTOverallV2Param dayBefore = GCTOverallV2Param.getDayBefore(gctOverallV2Param);
            dayBefore.setEsIndex(getEsIndex(GCT_BASE, dayBefore.getStartDate()));
            //判断是当天走实时接口按小时返回数据，其余走离线加实时拼接 按天返回数据，没数据天需要补空字符串
            final String endDate = dateformat.format(gctOverallV2Param.getEndDate());
            //前端需要的type参数用户小时展示确定展示逻辑
            List<Map<String, Object>> type = new ArrayList<>();
            String todayKey = "entry";
            List<Map<String, Object>> dayres = new ArrayList<>();
            List<Map<String, Object>> ydayres = new ArrayList<>();

            if (paramList.size() > 1) {
                todayKey = todayKey + i;
            }
            switch (gctOverallV2Param.getAction()) {
                case "ding_reg_new":
                    dayres = gctOverallV2Dao.getRegAnalysis(gctOverallV2Param);
                    //对比组不需要昨天的数据
                    if (paramList.size() < 2) {
                        ydayres = gctOverallV2Dao.getRegAnalysis(dayBefore);
                    }
                    break;
                case "active":
                    dayres = gctOverallV2Dao.getActiveAnalysis(gctOverallV2Param);
                    //对比组不需要昨天的数据
                    if (paramList.size() < 2) {
                        ydayres = gctOverallV2Dao.getActiveAnalysis(dayBefore);
                    }
                    break;
                case "app_startup":
                    dayres = gctOverallV2Dao.getStartupAnalysis(gctOverallV2Param);
                    //对比组不需要昨天的数据
                    if (paramList.size() < 2) {
                        ydayres = gctOverallV2Dao.getStartupAnalysis(dayBefore);
                    }
                    break;
                case "per_start":
                    dayres = gctOverallV2Dao.getPreStartupAnalysis(gctOverallV2Param);
                    //对比组不需要昨天的数据
                    if (paramList.size() < 2) {
                        ydayres = gctOverallV2Dao.getPreStartupAnalysis(dayBefore);
                    }
                    break;
                case "per_often":
                    dayres = gctOverallV2Dao.getPreVisitAnalysis(gctOverallV2Param);
                    //对比组不需要昨天的数据
                    if (paramList.size() < 2) {
                        ydayres = gctOverallV2Dao.getPreVisitAnalysis(dayBefore);
                    }
                    break;
                default:
            }
            //表格
            if (!ret.containsKey("table")) {
                final List<Map<String, Object>> rettable = gctOverallV2Dao.getTableAnalysis(gctOverallV2Param);
                ret.put("table", rettable);
            }
            if(dayres != null){
                ret.put(todayKey, dayres);
            }
            if (ydayres != null) {
                ret.put("pEntry", ydayres);
            }
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("type", true);
            type.add(hashMap);
            ret.put("type", type);
        }
        return ret;
    }


}
