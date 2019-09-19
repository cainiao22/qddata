package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.*;
import com.qding.bigdata.ds.model.GCTBaseV2Param;
import com.qding.bigdata.ds.model.GCTOverallV2Param;
import com.qding.bigdata.ds.service.GCTUserActiveV2Service;
import com.qding.bigdata.ds.util.BeanMapper;
import com.qding.bigdata.ds.util.DateUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author yanpf
 * @Date 19-8-21 下午3:59
 * @Description
 **/

@Service
public class GCTUserActiveV2ServiceImpl implements GCTUserActiveV2Service {


    GCTBaseV2Dao gctBaseV2Dao;

    GCTUserActiveV2CubeDao gctUserActiveV2CubeDao;

    PCompanyPersonInfoDao pCompanyPersonInfoDao;

    @Resource(name = "esSqlSession")
    public void initESSqlDao(SqlSession sqlSession) {
        this.gctBaseV2Dao = sqlSession.getMapper(GCTBaseV2Dao.class);
        this.gctUserActiveV2CubeDao = sqlSession.getMapper(GCTUserActiveV2CubeDao.class);
        this.pCompanyPersonInfoDao = sqlSession.getMapper(PCompanyPersonInfoDao.class);
    }


    private Map<String, List<Map<String, Object>>> getTableAnalysis(GCTOverallV2Param param, boolean pEntry) {
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        map.put(param.getAction(), new ArrayList<>());
        Date currentDate = new Date();
        switch (param.getDateType()) {
            case "day":
                //仅包含实时
                if (isCurrentDate(param) || (isYestarDay(param) && pEntry)) {
                    List<Map<String, Object>> activeUserCount = gctBaseV2Dao.getActiveUserCount(param);
                    convertToTable(activeUserCount, map);
                    param.setVersion("all");
                    param.setDeviceType("all");
                    List<Map<String, Object>> activeTableAnalysis = gctBaseV2Dao.getActiveUserCount(param);
                    Double regPersonTotal = pCompanyPersonInfoDao.queryRegPersonByCondition(param.getQRYcompanyId(), param.getCompanyId());
                    for (Map<String, Object> item : activeTableAnalysis) {
                        double value = Double.valueOf(item.get("active_user_count").toString());
                        item.put("active_user_rate", value / regPersonTotal);
                        item.remove("active_user_count");
                    }
                    convertToTable(activeTableAnalysis, map);
                    List<Map<String, Object>> regNewUserCount = gctBaseV2Dao.getRegNewUserCount(param);
                    List<Map<String, Object>> totaUserCount = gctBaseV2Dao.getActiveUserCount(param);
                    List<Map<String, Object>> regNewUserRate = new ArrayList<>();
                    int i, j;
                    for (i = 0, j = 0; i < regNewUserCount.size() && j < totaUserCount.size(); ) {
                        Map<String, Object> item = new HashMap<>();
                        if (regNewUserCount.get(i).get("hour").equals(totaUserCount.get(j).get("hour"))) {
                            item.put("hour", regNewUserCount.get(i).get("hour"));
                            item.put("new_user_rate",
                                    Double.valueOf(regNewUserCount.get(i).get("ding_reg_new").toString())
                                            / Double.valueOf(totaUserCount.get(j).get("active_user_count").toString()));
                            regNewUserRate.add(item);
                            i++;
                            j++;
                        } else if (regNewUserCount.get(i).get("hour").toString().compareTo(totaUserCount.get(j).get("hour").toString()) < 0) {
                            item.put("hour", regNewUserCount.get(i).get("hour"));
                            item.put("new_user_rate", "");
                            i++;
                        } else {
                            item.put("hour", totaUserCount.get(i).get("hour"));
                            item.put("new_user_rate", 1.0d);
                            j++;
                        }
                        regNewUserRate.add(item);
                    }
                    while (i < regNewUserCount.size()) {
                        Map<String, Object> item = new HashMap<>();
                        item.put("hour", regNewUserCount.get(i).get("hour"));
                        item.put("new_user_rate", "");
                        i++;
                        regNewUserRate.add(item);
                    }

                    while (j < totaUserCount.size()) {
                        Map<String, Object> item = new HashMap<>();
                        item.put("hour", totaUserCount.get(i).get("hour"));
                        item.put("new_user_rate", 1.0d);
                        j++;
                        regNewUserRate.add(item);
                    }
                    convertToTable(regNewUserRate, map);

                    return map;
                }

                //离线
                if (DateUtil.compareDate(currentDate, param.getStartDate()) > 0) {
                    List<Map<String, Object>> tableAnalysis = this.gctUserActiveV2CubeDao.getTableAnalysis(param);
                    convertToTable("date_value", tableAnalysis, map);
                }

                //包含实时
                if (DateUtil.compareDate(currentDate, param.getEndDate()) <= 0) {
                    Date start = param.getStartDate();
                    param.setStartDate(currentDate);
                    Map<String, Object> activeUserCountOverView = gctBaseV2Dao.getActiveUserCountOverView(param);
                    param.setVersion("all");
                    param.setDeviceType("all");
                    Double regPersonTotal = pCompanyPersonInfoDao.queryRegPersonByCondition(param.getQRYcompanyId(), param.getCompanyId());
                    Map<String, Object> activeUserCountOverViewTable = gctBaseV2Dao.getActiveUserCountOverView(param);
                    activeUserCountOverViewTable.put("active_user_rate", Double.valueOf(activeUserCountOverViewTable.get("active_user_count").toString()) / regPersonTotal);
                    activeUserCountOverViewTable.remove("active_user_count");
                    mergeToTable(activeUserCountOverView, map, param.getStartDateStr());
                    mergeToTable(activeUserCountOverViewTable, map, param.getStartDateStr());

                    Map<String, Object> regNewUserCountOverView = gctBaseV2Dao.getRegNewUserCountOverView(param);
                    Map<String, Object> totalUserOverView = gctBaseV2Dao.getActiveUserCountOverView(param);
                    Map<String, Object> regUserNewRate = new HashMap<>();
                    regUserNewRate.put("new_user_rate", Double.valueOf(regNewUserCountOverView.get("ding_reg_new").toString()) / Double.valueOf(totalUserOverView.get("active_user_count").toString()));
                    mergeToTable(regNewUserCountOverView, map, param.getStartDateStr());

                    param.setStartDate(start);
                }
                fillingDate(map, param.getStartDateStr(), param.getEndDateStr());
                break;
            case "month":
                //离线
                if (DateUtil.compareMonth(currentDate, param.getStartDate()) >= 0) {
                    List<Map<String, Object>> tableAnalysis = this.gctUserActiveV2CubeDao.getTableAnalysis(param);
                    convertToTable("date_value", tableAnalysis, map);
                }

                //包含实时
               /* if (DateUtil.compareMonth(currentDate, param.getEndDate()) <= 0) {
                    Date start = param.getStartDate();
                    param.setStartDate(currentDate);
                    Map<String, Object> analysisOverView = gctEventV2Dao.getAnalysisOverView(param);
                    mergeToTable(analysisOverView, map, param.getStartMonthStr());
                    param.setStartDate(start);
                }*/

                fillingMonth(map, param.getStartMonthStr(), param.getEndMonthStr());

        }
        return map;
    }


    @Override
    public Map<String, List<Map<String, Object>>> getTableAnalysis(List<GCTOverallV2Param> params) {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        List<Map<String, Object>> tables = new ArrayList<>();
        result.put(TYPE, new ArrayList<>());
        for (int i = 0; i < params.size(); i++) {
            GCTOverallV2Param param = BeanMapper.map(params.get(i), GCTOverallV2Param.class);
            Map<String, List<Map<String, Object>>> item = this.getTableAnalysis(param, false);
            result.put(ENTRY + i, item.getOrDefault(params.get(i).getAction(), Collections.emptyList()));
            item.forEach((k, list) -> {
                label_outter:
                for (Map<String, Object> map : list) {
                    for (Map<String, Object> table : tables) {
                        if (table.get(COMMON_KEY).equals(map.get(COMMON_KEY))) {
                            table.put(k, map.get(COMMON_VALUE));
                            continue label_outter;
                        }
                    }
                    Map<String, Object> data = new HashMap<>();
                    data.put(COMMON_KEY, map.get(COMMON_KEY));
                    data.put(k, map.get(COMMON_VALUE));
                    tables.add(data);
                }
            });
            Map<String, Object> type = new HashMap<>();
            type.put(TYPE, isCurrentDate(params.get(i)));
            result.get(TYPE).add(type);
        }
        result.put(TABLE, tables);

        if (params.size() == 1) {
            GCTOverallV2Param previousDay;
            GCTOverallV2Param param = params.get(0);
            if ("day".equals(param.getDateType())) {
                previousDay = GCTBaseV2Param.getPreviousDay(param);
            } else {
                previousDay = GCTBaseV2Param.getPreviousMonth(param);
            }
            result.put(ENTRY, result.get(ENTRY + 0));
            result.remove(ENTRY + 0);
            Map<String, List<Map<String, Object>>> item = this.getTableAnalysis(previousDay, true);
            result.put(PENTRY, item.getOrDefault(param.getAction(), Collections.emptyList()));
        }
        return result;
    }
}
