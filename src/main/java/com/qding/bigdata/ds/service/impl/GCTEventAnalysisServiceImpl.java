package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.GCTEventV2Dao;

import com.qding.bigdata.ds.dao.GCTEventV2InfoCubeDao;
import com.qding.bigdata.ds.model.GCTBaseV2Param;
import com.qding.bigdata.ds.model.GCTEventV2Param;
import com.qding.bigdata.ds.service.GCTEventAnalysisService;
import com.qding.bigdata.ds.util.DateUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author yanpf
 * @Date 19-8-17 上午9:59
 * @Description
 **/

@Service
public class GCTEventAnalysisServiceImpl implements GCTEventAnalysisService {

    GCTEventV2Dao gctEventV2Dao;

    GCTEventV2InfoCubeDao gctEventV2InfoCubeDao;

    @Resource(name = "esSqlSession")
    public void initESSqlDao(SqlSession sqlSession) {
        this.gctEventV2Dao = sqlSession.getMapper(GCTEventV2Dao.class);
        this.gctEventV2InfoCubeDao = sqlSession.getMapper(GCTEventV2InfoCubeDao.class);
    }


    private Map<String, List<Map<String, Object>>> getTableAnalysis(GCTEventV2Param param, boolean pEntry) {
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        map.put(param.getAction(), new ArrayList<>());
        Date currentDate = new Date();
        switch (param.getDateType()) {
            case "day":
                //仅包含实时
                if (isCurrentDate(param) || (isYestarDay(param) && pEntry)) {
                    List<Map<String, Object>> tableAnalysis = gctEventV2Dao.getTableAnalysis(param);
                    convertToTable(tableAnalysis, map);
                    return map;
                }

                //离线
                if (DateUtil.compareDate(currentDate, param.getStartDate()) > 0) {
                    List<Map<String, Object>> tableAnalysis = this.gctEventV2InfoCubeDao.getTableAnalysis(param);
                    convertToTable("date_value", tableAnalysis, map);
                }

                //包含实时
                if (DateUtil.compareDate(currentDate, param.getEndDate()) <= 0) {
                    Date start = param.getStartDate();
                    param.setStartDate(currentDate);
                    Map<String, Object> analysisOverView = gctEventV2Dao.getAnalysisOverView(param);
                    mergeToTable(analysisOverView, map, param.getStartDateStr());
                    param.setStartDate(start);
                }
                fillingDate(map, param.getStartDateStr(), param.getEndDateStr());
                break;
            case "month":
                //离线
                if (DateUtil.compareMonth(currentDate, param.getStartDate()) >= 0) {
                    List<Map<String, Object>> tableAnalysis = this.gctEventV2InfoCubeDao.getTableAnalysis(param);
                    convertToTable("date_value", tableAnalysis, map);
                }

                //包含实时
                if (DateUtil.compareMonth(currentDate, param.getEndDate()) <= 0) {
                    Date start = param.getStartDate();
                    param.setStartDate(currentDate);
                    Map<String, Object> analysisOverView = gctEventV2Dao.getAnalysisOverView(param);
                    mergeToTable(analysisOverView, map, param.getStartMonthStr());
                    param.setStartDate(start);
                }

                fillingMonth(map, param.getStartMonthStr(), param.getEndMonthStr());

        }
        return map;
    }

    @Override
    public Map<String, List<Map<String, Object>>> getTableAnalysis(List<GCTEventV2Param> params) {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        List<Map<String, Object>> tables = new ArrayList<>();
        result.put(TYPE, new ArrayList<>());
        for (int i = 0; i < params.size(); i++) {
            Map<String, List<Map<String, Object>>> item = this.getTableAnalysis(params.get(i), false);
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
            GCTEventV2Param previousDay;
            GCTEventV2Param param = params.get(0);
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
