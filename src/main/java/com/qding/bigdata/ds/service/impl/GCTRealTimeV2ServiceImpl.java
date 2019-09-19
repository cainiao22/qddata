package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.annotation.DynamicDataSource;
import com.qding.bigdata.ds.annotation.FilterResultAnno;
import com.qding.bigdata.ds.component.GCTBaseV2HandlerForAop;
import com.qding.bigdata.ds.component.GCTEventV2HandlerForAop;
import com.qding.bigdata.ds.component.GCTPageV2HandlerForAop;
import com.qding.bigdata.ds.component.GuancetaiQuotaInfoSortResultFilter;
import com.qding.bigdata.ds.dao.*;
import com.qding.bigdata.ds.model.DimCompanyMappingEntity;
import com.qding.bigdata.ds.model.GCTBaseV2Param;
import com.qding.bigdata.ds.model.GCTOverallV2Param;
import com.qding.bigdata.ds.model.GuancetaiQuotaInfo;
import com.qding.bigdata.ds.service.GCTRealTimeV2Service;
import com.qding.bigdata.ds.service.PropertyCompanyMappingService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author yanpf
 * @Date 19-8-15 下午5:30
 * @Description
 **/

@Service
public class GCTRealTimeV2ServiceImpl implements GCTRealTimeV2Service {

    @Autowired
    GCTBaseV2HandlerForAop gctBaseV2HandlerForAop;

    @Autowired
    GCTPageV2HandlerForAop gctPageV2HandlerForAop;

    @Autowired
    GCTEventV2HandlerForAop gctEventV2HandlerForAop;

    @Autowired
    PropertyCompanyMappingService propertyCompanyMappingService;


    GCTOverallV2Dao gctOverallV2Dao;

    GCTBaseV2Dao gctBaseV2Dao;

    GCTPageV2Dao gctPageV2Dao;

    GCTEventV2Dao gctEventV2Dao;

    PCompanyPersonInfoDao pCompanyPersonInfoDao;

    private static final int TOP_5 = 5;

    private static final int TOP_10 = 10;

/*    DimCompanyMappingEntityMapper companyMappingEntityMapper;

    @Resource(name = "dynamicSqlSession")
    public void initDao(SqlSession sqlSession) {
        DataSourceContextHolder.setDataSource("databus");
        this.companyMappingEntityMapper = sqlSession.getMapper(DimCompanyMappingEntityMapper.class);
        DataSourceContextHolder.clearDataSource();
    }*/

    @Resource(name = "esSqlSession")
    public void initESSqlDao(SqlSession sqlSession) {
        this.gctOverallV2Dao = sqlSession.getMapper(GCTOverallV2Dao.class);
        this.gctBaseV2Dao = sqlSession.getMapper(GCTBaseV2Dao.class);
        this.gctPageV2Dao = sqlSession.getMapper(GCTPageV2Dao.class);
        this.gctEventV2Dao = sqlSession.getMapper(GCTEventV2Dao.class);
        this.pCompanyPersonInfoDao = sqlSession.getMapper(PCompanyPersonInfoDao.class);
    }

    @Override
    public Map<String, GuancetaiQuotaInfo> getRealTimeAnalysis(GCTOverallV2Param param) {
        param.setStartDate(new Date());
        param.setEsIndex(getEsIndex(GCT_BASE, param.getStartDate()));
        Map<String, Object> current = gctOverallV2Dao.getBaseRealTimeAnalysisOverview(param);
        GCTOverallV2Param dayBefore = GCTBaseV2Param.getDayBefore(param);
        dayBefore.setEsIndex(getEsIndex(GCT_BASE, dayBefore.getStartDate()));
        dayBefore.setHour();
        Map<String, Object> last = gctOverallV2Dao.getBaseRealTimeAnalysisOverview(dayBefore);
        Map<String, GuancetaiQuotaInfo> result = new HashMap<>();
        mergeResult(current, last, result);

        param.setEsIndex(getEsIndex(GCT_PAGE, param.getStartDate()));
        Map<String, Object> currentPageOverview = gctOverallV2Dao.getPageRealTimeAnalysisOverview(param);
        GCTOverallV2Param dayBeforePageParam = GCTBaseV2Param.getDayBefore(param);
        dayBeforePageParam.setHour();
        dayBeforePageParam.setEsIndex(getEsIndex(GCT_PAGE, dayBeforePageParam.getStartDate()));
        Map<String, Object> lastPageOverview = gctOverallV2Dao.getPageRealTimeAnalysisOverview(dayBeforePageParam);
        mergeResult(currentPageOverview, lastPageOverview, result);

        param.setEsIndex(getEsIndex(GCT_EVENT, param.getStartDate()));
        Map<String, Object> currentEventOverview = gctOverallV2Dao.getEventRealTimeAnalysisOverview(param);
        GCTOverallV2Param dayBeforeEventParam = GCTBaseV2Param.getDayBefore(param);
        dayBeforeEventParam.setHour();
        dayBeforeEventParam.setEsIndex(getEsIndex(GCT_EVENT, dayBeforeEventParam.getStartDate()));
        Map<String, Object> lastEventOverview = gctOverallV2Dao.getEventRealTimeAnalysisOverview(dayBeforeEventParam);
        mergeResult(currentEventOverview, lastEventOverview, result);

        Double allPerson = pCompanyPersonInfoDao.queryAllPerson();
        Double allRegPerson = pCompanyPersonInfoDao.queryAllRegPerson() + Double.valueOf(current.get("reg_new").toString());

        //累计注册用户
        GuancetaiQuotaInfo info = new GuancetaiQuotaInfo();
        info.setCurrent(allRegPerson.doubleValue());
        info.setLast(allRegPerson);
        info.setTitle("total");
        info.setIncreasement(allRegPerson / allPerson);
        result.put("total", info);

        mergeResult(currentEventOverview, lastEventOverview, result);

        return result;
    }

    @Override
    public Map<String, List<Map<String, Object>>> getRealTimeTableAnalysis(GCTOverallV2Param param) {
        param.setStartDate(new Date());
        param.setEsIndex(getEsIndex(GCT_BASE, param.getStartDate()));
        List<Map<String, Object>> baseRealTimeTable = gctOverallV2Dao.getBaseRealTimeTableAnalysis(param);
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        convertToTable(baseRealTimeTable, result);

        param.setEsIndex(getEsIndex(GCT_PAGE, param.getStartDate()));
        List<Map<String, Object>> pageRealTimeTable = gctOverallV2Dao.getPageRealTimeTableAnalysis(param);
        convertToTable(pageRealTimeTable, result);

        param.setEsIndex(getEsIndex(GCT_EVENT, param.getStartDate()));
        List<Map<String, Object>> evnetRealTimeTable = gctOverallV2Dao.getEventRealTimeTableAnalysis(param);
        convertToTable(evnetRealTimeTable, result);

        //累计注册用户
        Double allRegPerson = pCompanyPersonInfoDao.queryAllRegPerson();
        List<Map<String, Object>> reg_new = result.get("reg_new");
        double sum = 0;
        List<Map<String, Object>> total = new ArrayList<>();
        for (Map<String, Object> map : reg_new) {
            Map<String, Object> totalMap = new HashMap<>();
            totalMap.put(COMMON_KEY, map.get(COMMON_KEY));
            sum += Double.valueOf(map.get(COMMON_VALUE).toString());
            totalMap.put(COMMON_VALUE, allRegPerson + sum);
            total.add(totalMap);
        }
        if (!CollectionUtils.isEmpty(total)) {
            for (int i = 0; i < total.size(); i++) {
                if (i > 0 && Double.valueOf(total.get(i).get(COMMON_VALUE).toString()) == 0.0) {
                    total.get(i).put(COMMON_VALUE, total.get(i - 1).get(COMMON_VALUE));
                }
            }
        }
        result.put("total", total);
        fillingHour(result);
        return result;
    }

    @Override
    @DynamicDataSource("databus")
    public Map<String, List<Map<String, Object>>> getRealTimeTopAnalysis(GCTOverallV2Param param) {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        param.setStartDate(new Date());

        //注册占比top10物业公司
        List<Map<String, Object>> allPersion = pCompanyPersonInfoDao.queryAllPersonByCompany();
        List<Map<String, Object>> allPersionReg = pCompanyPersonInfoDao.queryAllRegPersonByCompany();
        Map<String, Double> allPersonRegMap = new HashMap<>();
        Map<String, Double> allPersonMap = new HashMap<>();
        for (Map<String, Object> item : allPersionReg) {
            String key = item.get("company_id").toString();
            Double value = Double.valueOf(item.get("num").toString());
            allPersonRegMap.put(key, value);
            DimCompanyMappingEntity companyEntity = propertyCompanyMappingService.dimCompanyMappingEntityById(item.get("company_id").toString());
            allPersonMap.put(String.format("%s(%s)", companyEntity.getCompanyName(), companyEntity.getQyrCompanyNameList().substring(0, 2)), Double.valueOf(item.get("num").toString()));
        }
        List<Map<String, Object>> regPercent = new ArrayList<>();

        allPersion.forEach(item -> {
            Map<String, Object> top = new HashMap<>();
            DimCompanyMappingEntity companyEntity = propertyCompanyMappingService.dimCompanyMappingEntityById(item.get("company_id").toString());
            //allPersonMap.put(String.format("%s(%s)", companyEntity.getCompanyName(), companyEntity.getQyrCompanyNameList().substring(0, 2)), Double.valueOf(item.get("num").toString()));
            if (companyEntity != null) {
                top.put(COMMON_KEY, String.format("%s(%s)", companyEntity.getCompanyName(), companyEntity.getQyrCompanyNameList().substring(0, 2)));
                if (allPersonRegMap.get(item.get("company_id")) == null) {
                    top.put(COMMON_VALUE, Double.valueOf(item.get("num").toString()) == 0 ? Double.NaN : -1d);
                } else if (Double.valueOf(item.get("num").toString()) == 0) {
                    top.put(COMMON_VALUE, Double.POSITIVE_INFINITY);
                } else {
                    top.put(COMMON_VALUE, Math.round(allPersonRegMap.get(item.get("company_id")) / Double.valueOf(item.get("num").toString()) * 10000) / 10000.0);
                }
                regPercent.add(top);
            } else {
                System.out.println("null company : " + item.get("company_id"));
            }
        });
        result.put("topRegPercentCompany", subTopNList(regPercent, TOP_10));

        //当日活跃top10机型
        List<Map<String, Object>> topActivePhoneType = this.gctBaseV2Dao.getTopActivePhoneType(param);
        result.put("topActivePhoneType", subTopNList(topActivePhoneType, TOP_10));
        //当日活跃top10版本
        List<Map<String, Object>> topActiveVersion = this.gctBaseV2Dao.getTopActiveVersion(param);
        result.put("topActiveVersion", subTopNList(topActiveVersion, TOP_10));

        //当日活跃率top5物业公司
        List<Map<String, Object>> topActiveCompany = this.gctBaseV2HandlerForAop.getTopActiveCompany(param);

        List<Map<String, Object>> activePercent = new ArrayList<>();
        for (Map<String, Object> item : topActiveCompany) {
            Map<String, Object> top = new HashMap<>();
            top.put(COMMON_KEY, item.get(COMMON_KEY));
            if (allPersonMap.get(item.get(COMMON_KEY)) == null) {
                top.put(COMMON_VALUE, Double.valueOf(item.get(COMMON_VALUE).toString()) == 0 ? Double.NaN : -1d);
            } else if (Double.valueOf(item.get(COMMON_VALUE).toString()) == 0) {
                top.put(COMMON_VALUE, Double.POSITIVE_INFINITY);
            } else {
                top.put(COMMON_VALUE, Math.round(Double.valueOf(item.get(COMMON_VALUE).toString())
                        / Double.valueOf(allPersonMap.get(item.get(COMMON_KEY)).toString()) * 10000) / 10000.0);
            }
            activePercent.add(top);
        }
        Collections.sort(activePercent, (o1, o2) -> (int) (Double.parseDouble(o2.get(COMMON_VALUE).toString()) - Double.parseDouble(o1.get(COMMON_VALUE).toString())));
        result.put("topActiveCompany", subTopNList(activePercent, TOP_5));
        //当日访问top5页面
        List<Map<String, Object>> topVisitPage = this.gctPageV2Dao.getTopVisitPage(param);
        topVisitPage.forEach(map -> {
            if(map.get("key") == null || StringUtils.isEmpty(map.get("key").toString())){
                map.put("key", map.get("pageid"));
            }
        });
        Collections.sort(topVisitPage, (o1, o2) -> (int) (Double.parseDouble(o2.get(COMMON_VALUE).toString()) - Double.parseDouble(o1.get(COMMON_VALUE).toString())));
        result.put("topVisitPage", subTopNList(topVisitPage, TOP_5));
        //当日点击top5事件
        List<Map<String, Object>> topVisitEvent = this.gctEventV2Dao.getTopVisitEvent(param);
        Collections.sort(topVisitEvent, (o1, o2) -> (int) (Double.parseDouble(o2.get(COMMON_VALUE).toString()) - Double.parseDouble(o1.get(COMMON_VALUE).toString())));
        result.put("topVisitEvent", subTopNList(topVisitEvent, TOP_5));

        return result;
    }

    /**
     * 按分公司查询,按公司汇总
     *
     * @param param
     * @return
     */
    @Override
    @FilterResultAnno(GuancetaiQuotaInfoSortResultFilter.class)
    public Map<String, GuancetaiQuotaInfo> getRealTimeAnalysisByCompany(GCTOverallV2Param param) {
        Map<String, GuancetaiQuotaInfo> result = new LinkedHashMap<>();
        param.setStartDate(new Date());
        switch (param.getAction()) {
            case "total":
                List<Map<String, Object>> all = pCompanyPersonInfoDao.queryPersonByCompany(param.getQRYcompanyId());
                Map<String, Double> allForMap = new HashMap<>();
                for (Map<String, Object> map : all) {
                    allForMap.put(map.get("company_name").toString(), Double.valueOf(map.get("num").toString()));
                }
                List<Map<String, Object>> reg = pCompanyPersonInfoDao.queryRegPersonByCompany(param.getQRYcompanyId());
                Collections.sort(reg, (o1, o2) -> (int) (Double.parseDouble(o2.get("num").toString()) - Double.parseDouble(o1.get("num").toString())));
                for (Map<String, Object> map : reg) {
                    String companyName = map.get("company_name").toString();
                    Double total = allForMap.get(companyName);
                    Double regNum = Double.valueOf(map.get("num").toString());
                    GuancetaiQuotaInfo info = new GuancetaiQuotaInfo();
                    info.setCurrent(regNum);
                    info.setIncreasement(regNum / total);
                    info.setTitle(companyName);
                    info.setCompanyId(String.valueOf(map.get("company_id")));
                    result.put(companyName, info);
                }
                break;
            case "start_up":
            case "reg_new":
            case "active":
                List<Map<String, Object>> current = gctBaseV2HandlerForAop.getAnalysisOverviewByCompany(param, param.getAction());
                GCTOverallV2Param dayBefore = GCTBaseV2Param.getDayBefore(param);
                List<Map<String, Object>> last = gctBaseV2HandlerForAop.getAnalysisOverviewByCompany(dayBefore, dayBefore.getAction());
                mergeListResult(current, last, result);
                Map<String, String> companyIdMap = new HashMap<>();
                for (Map<String, Object> map : current) {
                    companyIdMap.put(map.get(COMMON_KEY).toString(), String.valueOf(map.get("companyId")));
                }
                result.forEach((k, v) -> {
                    v.setCompanyId(companyIdMap.get(v.getTitle()));
                });
                break;
            case "page_visit_time":
                List<Map<String, Object>> currentPage = gctPageV2HandlerForAop.getAnalysisOverviewByCompany(param, param.getAction());
                GCTOverallV2Param dayBeforePageParam = GCTBaseV2Param.getDayBefore(param);
                List<Map<String, Object>> lastPage = gctPageV2HandlerForAop.getAnalysisOverviewByCompany(dayBeforePageParam, dayBeforePageParam.getAction());
                mergeListResult(currentPage, lastPage, result);
                break;
            case "event_visit_time":
                List<Map<String, Object>> currentEvent = gctEventV2HandlerForAop.getAnalysisOverviewByCompany(param, param.getAction());
                GCTOverallV2Param dayBeforeEventParam = GCTBaseV2Param.getDayBefore(param);
                List<Map<String, Object>> lastEvent = gctEventV2HandlerForAop.getAnalysisOverviewByCompany(dayBeforeEventParam, dayBeforeEventParam.getAction());
                mergeListResult(currentEvent, lastEvent, result);
                break;

        }

        return result;
    }

    @Override
    @DynamicDataSource("databus")
    @FilterResultAnno(GuancetaiQuotaInfoSortResultFilter.class)
    public Map<String, GuancetaiQuotaInfo> getRealTimeAnalysisByQyrCompany(GCTOverallV2Param param) {
        Map<String, GuancetaiQuotaInfo> result = new HashMap<>();
        List<DimCompanyMappingEntity> allQyrCompany = propertyCompanyMappingService.findAllQyrCompany();
        param.setStartDate(new Date());
        allQyrCompany.forEach(qyrCompany -> {
            param.setQRYcompanyId(qyrCompany.getQyrCompanyBinaryList());
            GuancetaiQuotaInfo item = new GuancetaiQuotaInfo();
            item.setTitle(qyrCompany.getQyrCompanyBinaryList());

            switch (param.getAction()) {
                case "total":
                    Long all = pCompanyPersonInfoDao.queryAllPersonByQryCompany(qyrCompany.getQyrCompanyBinaryList());
                    Long reg = pCompanyPersonInfoDao.queryAllRegPersonByQryCompany(qyrCompany.getQyrCompanyBinaryList());
                    item.setCurrent(reg.doubleValue());
                    item.setIncreasement(reg / all.doubleValue());
                    break;
                case "start_up":
                case "reg_new":
                case "active":
                    Long current = gctBaseV2Dao.getAnalysisOverviewByQyrCompany(param);
                    GCTOverallV2Param dayBeforeBaseParam = GCTBaseV2Param.getDayBefore(param);
                    Long last = gctBaseV2Dao.getAnalysisOverviewByQyrCompany(dayBeforeBaseParam);
                    item.setTitle(qyrCompany.getQyrCompanyBinaryList());
                    item.setCurrent(current.doubleValue());
                    item.setLast(last.doubleValue());
                    item.setIncreasementNoPercent();
                    break;
                case "page_visit_time":
                    Long currentPage = gctPageV2Dao.getAnalysisOverviewByQyrCompany(param);
                    GCTOverallV2Param dayBeforePageParam = GCTBaseV2Param.getDayBefore(param);
                    Long lastPage = gctPageV2Dao.getAnalysisOverviewByQyrCompany(dayBeforePageParam);
                    item.setCurrent(currentPage.doubleValue());
                    item.setLast(lastPage.doubleValue());
                    item.setIncreasementNoPercent();
                    break;
                case "event_visit_time":
                    Long currentEvent = gctEventV2Dao.getAnalysisOverviewByQyrCompany(param);
                    GCTOverallV2Param dayBeforeEventParam = GCTBaseV2Param.getDayBefore(param);
                    Long lastEvent = gctEventV2Dao.getAnalysisOverviewByQyrCompany(dayBeforeEventParam);
                    item.setCurrent(currentEvent.doubleValue());
                    item.setLast(lastEvent.doubleValue());
                    item.setIncreasementNoPercent();
            }

            result.put(qyrCompany.getQyrCompanyNameList(), item);
        });
        return result;
    }


}
