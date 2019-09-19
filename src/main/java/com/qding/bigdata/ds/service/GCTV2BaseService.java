package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.GCTBaseV2Param;
import com.qding.bigdata.ds.model.GuancetaiQuotaInfo;
import com.qding.bigdata.ds.util.DateUtil;
import com.qding.bigdata.ds.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author yanpf
 * @Date 19-8-15 下午5:54
 * @Description
 **/

public interface GCTV2BaseService {

    String GCT_BASE = "gct_base.";
    String GCT_PAGE = "gct_page.";
    String GCT_EVENT = "gct_event.";

    String COMMON_KEY = "key";
    String COMMON_VALUE = "value";

    String ENTRY = "entry";
    String PENTRY = "pEntry";
    String TABLE = "table";
    String TYPE = "type";

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    default String getEsIndex(String prefix, Date date) {
        return prefix + dateFormat.format(date);
    }

    default void mergeResult(Map<String, Object> current, Map<String, Object> last, Map<String, GuancetaiQuotaInfo> result) {
        for (String key : current.keySet()) {
            GuancetaiQuotaInfo item = new GuancetaiQuotaInfo();
            item.setCurrent(Double.valueOf(current.get(key) == null ? "0" : current.get(key).toString()));
            item.setLast(Double.valueOf(last.get(key) == null ? "0" : last.get(key).toString()));
            item.setIncreasementNoPercent();
            item.setTitle(key);
            result.put(key, item);
        }
    }

    default void mergeListResult(List<Map<String, Object>> current, List<Map<String, Object>> last, Map<String, GuancetaiQuotaInfo> result) {
        Map<String, Object> lastMap = new HashMap<>();
        Map<String, Object> currentMap = new HashMap<>();
        last.forEach(item -> lastMap.put(item.get(COMMON_KEY).toString(), item.get(COMMON_VALUE)));
        current.forEach(item -> currentMap.put(item.get(COMMON_KEY).toString(), item.get(COMMON_VALUE)));
        mergeResult(currentMap, lastMap, result);
    }

    default void convertToTable(List<Map<String, Object>> baseRealTimeTable, Map<String, List<Map<String, Object>>> result) {
        this.convertToTable("hour", baseRealTimeTable, result);
    }

    default void convertToTable(String baseKey, List<Map<String, Object>> baseRealTimeTable, Map<String, List<Map<String, Object>>> result) {
        for (Map<String, Object> map : baseRealTimeTable) {
            for (String key : map.keySet()) {
                if (!baseKey.equalsIgnoreCase(key)) {
                    Map<String, Object> item = new HashMap<>();
                    item.put(COMMON_KEY, map.get(baseKey));
                    item.put(COMMON_VALUE, map.get(key));
                    List<Map<String, Object>> list = result.getOrDefault(key, new ArrayList<>());
                    list.add(item);
                    result.put(key, list);
                }
            }
        }
    }

    default void fillingHour(Map<String, List<Map<String, Object>>> result) {
        List<String> hourList = DateUtil.getUntilHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        for (List<Map<String, Object>> value : result.values()) {
            /*if (value != null && value.size() == hourList.size()) {
                continue;
            }*/
            for (Map<String, Object> map : value) {
                map.put(COMMON_KEY, String.format("%02d", Integer.valueOf(StringUtil.trimZero(map.get(COMMON_KEY).toString()))));
            }
        }
        fillMissingKey(result, hourList);
    }

    default void fillingDate(Map<String, List<Map<String, Object>>> result, String startDate, String endDate) {
        List<String> dateList = DateUtil.getBetweenDate(startDate, endDate);
        fillMissingKey(result, dateList);
    }

    default void fillingDate2(Map<String, List<Map<String, Object>>> result, GCTBaseV2Param param,GCTBaseV2Param preParam) {
        List<String> dateList = DateUtil.getBetweenDate(param.getStartDateStr(), param.getEndDateStr());
        List<String> preDateList = DateUtil.getBetweenDate(preParam.getStartDateStr(), preParam.getEndDateStr());
        fillMissingKey(result, dateList,"entry");
        fillMissingKey(result, preDateList,"pEntry");
    }



    default void fillingMonth(Map<String, List<Map<String, Object>>> result, String startMonth, String endMonth) {
        List<String> dateList = new ArrayList<>();
        try {
            dateList = DateUtil.getMonthBetween(startMonth, endMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        fillMissingKey(result, dateList);
    }

    default void fillingMonth2(Map<String, List<Map<String, Object>>> result, GCTBaseV2Param param,GCTBaseV2Param preParam) {
        List<String> dateList = new ArrayList<>();
        List<String> preDateList = new ArrayList<>();
        try {
            dateList = DateUtil.getMonthBetween(param.getStartDateStr(), param.getEndDateStr());
            preDateList = DateUtil.getMonthBetween(preParam.getStartDateStr(), preParam.getEndDateStr());
        } catch (Exception e) {
            e.printStackTrace();
        }
        fillMissingKey(result, dateList,"entry");
        fillMissingKey(result, preDateList,"pEntry");
    }

    default void fillMissingKey(Map<String, List<Map<String, Object>>> result, List<String> fullKeyList) {
        result.forEach((k, v) -> {
            if (v != null && v.size() == fullKeyList.size()) {
                return;
            }
            label_outter:
            for (String date : fullKeyList) {
                for (Map<String, Object> map : v) {
                    if (date.equals(map.get(COMMON_KEY))) {
                        continue label_outter;
                    }
                }
                Map<String, Object> newItem = new HashMap<>();
                newItem.put(COMMON_KEY, date);
                newItem.put(COMMON_VALUE, 0);
                v.add(newItem);
            }

            Collections.sort(v, Comparator.comparing(o -> o.get(COMMON_KEY).toString()));
        });
    }

    default void fillMissingKey(Map<String,List<Map<String,Object>>> result, List<String> dateList, String key){
        result.forEach((k, v) -> {
            if (v != null && v.size() == dateList.size()) {
                return;
            }
            if(k.contains(key)){
                label_outter:
                for (String date : dateList) {
                    for (Map<String, Object> map : v) {
                        if (map!=null && date.equals(map.get(COMMON_KEY))) {
                            continue label_outter;
                        }
                    }
                    Map<String, Object> newItem = new HashMap<>();
                    newItem.put(COMMON_KEY, date);
                    newItem.put(COMMON_VALUE, 0);
                    v.add(newItem);
                }
                Collections.sort(v, Comparator.comparing(o ->
                        o.get(COMMON_KEY).toString()));
            }
        });
    }

    /**
     * 将实时数据合并到总数据中
     * @param analysisOverView
     * @param map
     * @param dateStr
     */
    default void mergeToTable(Map<String, Object> analysisOverView, Map<String, List<Map<String, Object>>> map, String dateStr) {
        if (analysisOverView != null) {
            label_outter:
            for (Map.Entry<String, Object> entry : analysisOverView.entrySet()) {
                List<Map<String, Object>> list = map.getOrDefault(entry.getKey(),new ArrayList<>());
                for (Map<String, Object> item : list) {
                    if (dateStr.equals(item.get(COMMON_KEY))) {
                        Object o = item.get(COMMON_VALUE);
                        if (o instanceof Double) {
                            item.put(COMMON_VALUE, (Double) o + Double.valueOf(entry.getValue().toString()));
                        } else if (o instanceof Integer) {
                            item.put(COMMON_VALUE, (Integer) o + Integer.valueOf(entry.getValue().toString()));
                        } else if (o instanceof Long) {
                            item.put(COMMON_VALUE, (Long) o + Long.valueOf(entry.getValue().toString()));
                        }
                        continue label_outter;
                    }
                }
                Map<String, Object> newItem = new HashMap<>();
                newItem.put(COMMON_KEY, dateStr);
                newItem.put(COMMON_VALUE, entry.getValue());
                list.add(newItem);
            }
        }
    }

    default void mergeDayData(Map<String, List<Map<String, Object>>> realTime, Map<String, List<Map<String, Object>>> ret, String dateStr) {
        if(realTime!=null && ret!= null){
            List<Map<String, Object>> table = realTime.get("table");
            if (ret.get("table")!=null){
                List<Map<String, Object>> value = ret.get("table");
                if (CollectionUtils.isNotEmpty(table)) {
                    table.get(0).put(COMMON_KEY, dateStr);
                    value.add(table.get(0));
                }
            }
            for(Map.Entry<String,List<Map<String, Object>>> rel : realTime.entrySet()) {
                for (Map.Entry<String, List<Map<String, Object>>> item : ret.entrySet()) {
                    if (item.getKey().equals(rel.getKey()) && item.getKey().contains("entry")) {
                        final List<Map<String, Object>> entry = rel.getValue();
                        if (CollectionUtils.isNotEmpty(entry)) {
                            Map<String, Object> today = entry.get(0);
                            today.put(COMMON_KEY, dateStr);
                            List<Map<String, Object>> value = item.getValue();
                            value.add(today);
                            break;
                        }
                    }

                }
            }

        }
    }
    default void mergeMonthData(Map<String, List<Map<String, Object>>> realTime, Map<String, List<Map<String, Object>>> ret, String monthStr) {
        if(realTime!=null && ret!= null){
            for(Map.Entry<String,List<Map<String, Object>>> rel : realTime.entrySet()) {
                for (Map.Entry<String, List<Map<String, Object>>> item : ret.entrySet()) {
                    if (item.getKey().equals(rel.getKey()) && item.getKey().contains("entry")){
                        List<Map<String, Object>> valueArray = item.getValue();
                        valueArray.forEach(sqlData -> sqlData.forEach((k, v) -> {
                            if (monthStr.equals(v)) {
                                Map<String, Object> finalToday = new HashMap<>();
                                if(rel.getValue().size()>0){
                                    finalToday = rel.getValue().get(0);
                                }
                                Object valueNum = sqlData.get(COMMON_VALUE);
                                if (valueNum instanceof Double) {
                                    valueNum = ((Double) valueNum + Double.valueOf(finalToday.get(COMMON_VALUE).toString()));
                                } else if (valueNum instanceof Integer) {
                                    valueNum = (Integer) valueNum + Integer.valueOf(finalToday.get(COMMON_VALUE).toString());
                                } else if (v instanceof Long) {
                                    valueNum = (Long) valueNum + Long.valueOf(finalToday.get(COMMON_VALUE).toString());
                                }
                                sqlData.put(COMMON_VALUE, valueNum);
                            }
                        }));
                    }
                }
            }
        }
    }

    default List<Map<String, Object>> subTopNList(List<Map<String, Object>> list, int topN) {
        Collections.sort(list, (o1, o2) -> {
            double value2 = Double.valueOf(o2.get(COMMON_VALUE).toString()).doubleValue();
            double value1 = Double.valueOf(o1.get(COMMON_VALUE).toString()).doubleValue();
            return value2 == value1 ? 0 : (value2 > value1 ? 1 : -1);
        });
        if(list.size() < topN){
            return list;
        }
        return list.subList(0, topN);
    }

    default boolean isCurrentDate(GCTBaseV2Param param){
        Date currentDate = new Date();
        return ("day".equals(param.getDateType()) && DateUtil.compareDate(currentDate, param.getEndDate()) == 0
                && DateUtil.compareDate(currentDate, param.getStartDate()) == 0);

    }

    default boolean isYestarDay(GCTBaseV2Param param){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return (DateUtil.compareDate(calendar.getTime(), param.getEndDate()) == 0
                && DateUtil.compareDate(calendar.getTime(), param.getStartDate()) == 0);
    }
}
