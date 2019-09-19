package com.qding.bigdata.ds.service.impl;


import com.qding.bigdata.ds.dao.GCTPageInfoCubeV2Dao;
import com.qding.bigdata.ds.dao.GCTPageV2Dao;
import com.qding.bigdata.ds.model.GCTBaseV2Param;
import com.qding.bigdata.ds.model.GCTOverallV2Param;
import com.qding.bigdata.ds.model.GCTPageV2Param;
import com.qding.bigdata.ds.service.GCTPageV2Service;
import com.qding.bigdata.ds.util.BeanMapper;
import com.qding.bigdata.ds.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/13 下午8:55
 */
@Service
public class GCTPageV2ServiceImpl implements GCTPageV2Service {
    GCTPageV2Dao gctPageV2Dao;
    GCTPageInfoCubeV2Dao gctPageInfoCubeV2Dao;
    @Resource(name = "esSqlSession")
    public void initExecutorDao(SqlSession sqlSession) {
        this.gctPageV2Dao = sqlSession.getMapper(GCTPageV2Dao.class);
        this.gctPageInfoCubeV2Dao = sqlSession.getMapper(GCTPageInfoCubeV2Dao.class);
    }
   @Override
   public Map<String, List<Map<String, Object>>> getPageAnalysis(List<GCTPageV2Param> paramList) {
        Map<String, List<Map<String, Object>>> ret = new HashMap<>();
        Date currentDate = new Date();
        if (paramList != null) {
            GCTBaseV2Param param = paramList.get(0);
            switch (param.getDateType()) {
                case "day":
                    //仅包含实时
                    if (DateUtil.compareDate(currentDate, param.getEndDate()) == 0 && DateUtil.compareDate(currentDate, param.getStartDate()) == 0) {
                        return getPageAnalysisByRealTime(ret,paramList);
                    }

                    //离线
                    if (DateUtil.compareDate(currentDate, param.getStartDate()) > 0) {
                        ret = getPageAnalysisByOffline(ret,paramList);
                    }

                    //包含实时
                    if (DateUtil.compareDate(currentDate, param.getEndDate()) <= 0) {
                        Map<String, List<Map<String, Object>>> pageAnalysisByCurDay = getPageAnalysisByCurDay(paramList);
                        mergeDayData(pageAnalysisByCurDay, ret,LocalDate.now().toString());
                    }
                    fillingDate2(ret, param,GCTOverallV2Param.getPreviousDay(param));
                    break;
                case "month":
                    ret = getPageAnalysisByOffline(ret,paramList);
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

    private Map<String, List<Map<String, Object>>> getPageAnalysisByCurDay(List<GCTPageV2Param> paramList) {
        Map<String, List<Map<String, Object>>> ret = new HashMap<>();
        for (int i = 0; i < paramList.size(); i++) {
            GCTPageV2Param gctPageV2Param = paramList.get(i);
            Date start = gctPageV2Param.getStartDate();
            Date end = gctPageV2Param.getEndDate();
            gctPageV2Param.setStartDate(new Date());
            gctPageV2Param.setEndDate(new Date());
            //表格对比只展示第一个
            if (!ret.containsKey("table")) {
                GCTPageV2Param tableParam = BeanMapper.map(gctPageV2Param, GCTPageV2Param.class);
                tableParam.setAction("table");
                final List<Map<String, Object>> rettable = gctPageV2Dao.getPageAnalysisCur(tableParam);
                if(CollectionUtils.isNotEmpty(rettable)){
                    ret.put("table", rettable);
                }
            }
            List<Map<String, Object>>  entryList = new ArrayList<>();
            entryList = gctPageV2Dao.getPageAnalysisCur(gctPageV2Param);
            if(paramList.size()>1){
                String entry = "entry"+i;
                ret.put(entry, entryList);
            }else{
                ret.put("entry", entryList);
            }
            gctPageV2Param.setStartDate(start);
            gctPageV2Param.setEndDate(end);
        }
        return ret;
    }

    private Map<String, List<Map<String, Object>>> getPageAnalysisByOffline(Map<String, List<Map<String, Object>>> ret, List<GCTPageV2Param> paramList) {
        for(int i=0;i<paramList.size();i++){
            GCTPageV2Param gctPageV2Param = paramList.get(i);
            String entry = "entry";
            List<Map<String, Object>> dayres = new ArrayList<>();
            List<Map<String, Object>> ydayres = new ArrayList<>();
            //表格
            if(!ret.containsKey("table")){
                GCTPageV2Param tableParam = BeanMapper.map(gctPageV2Param, GCTPageV2Param.class);
                tableParam.setAction("table");
                final List<Map<String, Object>> rettable = gctPageInfoCubeV2Dao.getPageAnalysis(tableParam);
                ret.put("table",rettable);
            }

            //对比组不需要昨天的数据
            dayres = gctPageInfoCubeV2Dao.getPageAnalysis(gctPageV2Param);
            if(paramList.size()>1){
                entry=entry+i;
                ret.put(entry,dayres);
            }else {
                GCTPageV2Param previousParam = null;
                if("day".equals(gctPageV2Param.getDateType())){
                    previousParam = GCTPageV2Param.getPreviousDay(gctPageV2Param);
                }else if("month".equals(gctPageV2Param.getDateType())){
                    previousParam = GCTPageV2Param.getPreviousMonth(gctPageV2Param);
                }
                ydayres = gctPageInfoCubeV2Dao.getPageAnalysis(previousParam);
                ret.put(entry,dayres);
                ret.put("pEntry",ydayres);
            }
        }
        return ret;
    }

    private Map<String, List<Map<String, Object>>> getPageAnalysisByRealTime(Map<String, List<Map<String, Object>>> ret, List<GCTPageV2Param> paramList) {
        for(int i=0;i<paramList.size();i++){
            GCTPageV2Param gctPageV2Param = paramList.get(i);
            //判断是当天走实时接口按小时返回数据，其余走离线加实时拼接 按天返回数据，没数据天需要补空字符串
            //前端需要的type参数用户小时展示确定展示逻辑
            List<Map<String,Object>> type = new ArrayList<>();
            String entry = "entry";
            List<Map<String, Object>> dayres = new ArrayList<>();
            List<Map<String, Object>> ydayres = new ArrayList<>();

            //表格
            if(!ret.containsKey("table")){
                GCTPageV2Param tableParam = BeanMapper.map(gctPageV2Param, GCTPageV2Param.class);
                tableParam.setAction("table");
                final List<Map<String, Object>> rettable = gctPageV2Dao.getPageAnalysis(tableParam);
                ret.put("table",rettable);
            }

            //对比组不需要昨天的数据
            dayres = gctPageV2Dao.getPageAnalysis(gctPageV2Param);
            if(paramList.size()>1){
                entry=entry+i;
                ret.put(entry,dayres);
            }else {
                GCTPageV2Param dayBefore = GCTPageV2Param.getDayBefore(gctPageV2Param);
                ydayres = gctPageV2Dao.getPageAnalysis(dayBefore);
                ret.put(entry,dayres);
                ret.put("pEntry",ydayres);
            }
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("type",true);
            type.add(hashMap);
            ret.put("type", type);
        }
        return ret;
    }
}
