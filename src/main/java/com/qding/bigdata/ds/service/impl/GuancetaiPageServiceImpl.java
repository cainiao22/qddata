package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.annotation.FilterResultAnno;
import com.qding.bigdata.ds.component.GuanceTaiPageAvgOnlineTimeFilter;
import com.qding.bigdata.ds.component.GuancetaiPageHandlerResultViewFilter;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.service.GuancetaiPageService;
import com.qding.bigdata.ds.util.EsInstance;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.*;
import org.elasticsearch.search.aggregations.metrics.InternalNumericMetricsAggregation;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author yanpf
 * @date 2018/12/27 15:08
 * @description
 */

@Service
public class GuancetaiPageServiceImpl extends GuancetaiServiceImpl implements GuancetaiPageService {

    private Map<String, Field> fieldCache = new HashMap<String, Field>(){
        {
            GuancetaiPageServiceImpl.getDeclaredFields(GuancetaiPageDetail.class, this);
        }
    };


    @Override
    public GuancetaiQuotaInfo getBusinessOnlineTime(GuancetaiQuotaParam param) {
        GuancetaiQuotaInfo result = new GuancetaiQuotaInfo();
        if (param.getDeviceStatus() == null || param.getDeviceStatus() == 1) {
            Calendar calendar = Calendar.getInstance();
            Double current = this.getBusinessOnlineTime(param, calendar.getTime());
            calendar.add(Calendar.DATE, -1);
            Double last = this.getBusinessOnlineTime(param, calendar.getTime());
            result.setCurrent(current);
            result.setLast(last);
            result.setIncreasement();
        } else {
            Calendar calendar = Calendar.getInstance();
            param.setDeviceStatus(null);
            Double currentAll = this.getBusinessOnlineTime(param, calendar.getTime());
            calendar.add(Calendar.DATE, -1);
            Double lastAll = this.getBusinessOnlineTime(param, calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            param.setDeviceStatus(1);
            Double currentOn = this.getBusinessOnlineTime(param, calendar.getTime());
            calendar.add(Calendar.DATE, -1);
            Double lastOn = this.getBusinessOnlineTime(param, calendar.getTime());
            Double current = currentAll - currentOn;
            result.setCurrent(current);
            Double last = lastAll - lastOn;
            result.setLast(last);
            result.setIncreasement();
        }

        return result;
    }

    @Override
    public GuancetaiQuotaInfo getPageStartupTimesInfo(GuancetaiQuotaParam param) {
        GuancetaiQuotaInfo result = new GuancetaiQuotaInfo();
        Calendar calendar = Calendar.getInstance();
        Double current = this.getPageStartupTimesInfo(param, calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        Double last = this.getPageStartupTimesInfo(param, calendar.getTime());
        result.setCurrent(current);
        result.setLast(last);
        result.setIncreasement();
        return result;
    }

    private Double getPageStartupTimesInfo(GuancetaiQuotaParam param, Date date) {
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(INDEX_GCT_PAGE + dateformat.format(date));
        //查询source
        param.setQueryDate(date);
        BoolQueryBuilder boolBuilder = getBooleanQueryBuilder(param);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        boolBuilder.must(QueryBuilders.rangeQuery("hour").lte(hour));

        SumAggregationBuilder teamSum = AggregationBuilders.sum("startupTimes").field("page_visit_num");
        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder))
                .addAggregation(teamSum)
                .execute()
                .actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        InternalSum internalSum = (InternalSum) aggregationMap.get("startupTimes");
        Double value = internalSum.getValue();
        return value;
    }


    private Double getBusinessOnlineTime(GuancetaiQuotaParam param, Date date) {
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(INDEX_GCT_PAGE + dateformat.format(date));
        param.setQueryDate(date);
        BoolQueryBuilder boolBuilder = this.getBooleanQueryBuilder(param);
        //如果做同比 需要 今天和昨天小时是一致的
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        boolBuilder.must(QueryBuilders.rangeQuery("hour").lte(hour));
        SumAggregationBuilder teamSum = AggregationBuilders.sum("pageVisitTime").field("page_visit_time");
        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder))
                .addAggregation(teamSum)
                .execute()
                .actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        InternalSum internalSum = (InternalSum) aggregationMap.get("pageVisitTime");
        Double value = internalSum.getValue();
        return value;

    }

    @Override
    public GuancetaiQuotaInfo getPageStartupUsersInfo(GuancetaiQuotaParam param) {
        return this.getStartupUsersInfo(param, INDEX_GCT_PAGE);
    }


    @Override
    public List<GuancetaiQuotaTrendItem> getBusinessOnlineTimeTrend(GuancetaiPageQuotaParam param) {
        TransportClient client = EsInstance.getInstance();
        List<GuancetaiQuotaTrendItem> result = new ArrayList<GuancetaiQuotaTrendItem>();
        SearchRequestBuilder srb = client.prepareSearch(INDEX_GCT_PAGE + dateformat.format(param.getQueryDate()));
        //查询source
        BoolQueryBuilder boolBuilder = getBooleanQueryBuilder(param);

        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("hour").field("hour").size(Integer.MAX_VALUE);
        SumAggregationBuilder teamSum = AggregationBuilders.sum("startupTimes").field("page_visit_time");
        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder))
                .addAggregation(aggregationBuilder.subAggregation(teamSum))
                .execute()
                .actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        if(aggregationMap.get("hour") instanceof StringTerms) {
            List<StringTerms.Bucket> buckets = ((StringTerms) aggregationMap.get("hour")).getBuckets();
            for (Terms.Bucket bucket : buckets) {
                String key = bucket.getKey().toString();
                Map<String, Aggregation> stringAggregationMap = bucket.getAggregations().asMap();
                InternalNumericMetricsAggregation.SingleValue visitCount = (InternalNumericMetricsAggregation.SingleValue) stringAggregationMap.get("startupTimes");
                Double value = visitCount.value();
                GuancetaiQuotaTrendItem item = new GuancetaiQuotaTrendItem();
                item.setKey(key);
                item.setValue(value);
                result.add(item);
            }
            Collections.sort(result, new Comparator<GuancetaiQuotaTrendItem>() {
                @Override
                public int compare(GuancetaiQuotaTrendItem o1, GuancetaiQuotaTrendItem o2) {
                    return o1.getKey().compareTo(o2.getKey());
                }
            });
        }
        return result;
    }

    @Override
    public List<GuancetaiQuotaTrendItem> getPageStartupUsersInfoTrend(GuancetaiPageQuotaParam param) {
        return this.getStartupUsersInfoTrend(param, INDEX_GCT_PAGE);
    }

    @Override
    public List<GuancetaiQuotaTrendItem> getPageStartupTimesInfoTrend(GuancetaiPageQuotaParam param) {
        List<GuancetaiQuotaTrendItem> result = new ArrayList<GuancetaiQuotaTrendItem>();
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(INDEX_GCT_PAGE + dateformat.format(param.getQueryDate()));
        //查询source
        BoolQueryBuilder boolBuilder = getBooleanQueryBuilder(param);

        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("hour").field("hour").size(Integer.MAX_VALUE);
        SumAggregationBuilder teamSum = AggregationBuilders.sum("page_visit_num").field("page_visit_num");
        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder))
                .addAggregation(aggregationBuilder.subAggregation(teamSum))
                .execute()
                .actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        if(aggregationMap.get("hour") instanceof StringTerms) {
            List<StringTerms.Bucket> buckets = ((StringTerms) aggregationMap.get("hour")).getBuckets();
            for (Terms.Bucket bucket : buckets) {
                String key = bucket.getKey().toString();
                Map<String, Aggregation> stringAggregationMap = bucket.getAggregations().asMap();
                InternalNumericMetricsAggregation.SingleValue visitCount = (InternalNumericMetricsAggregation.SingleValue) stringAggregationMap.get("page_visit_num");
                Double value = visitCount.value();
                GuancetaiQuotaTrendItem item = new GuancetaiQuotaTrendItem();
                item.setKey(key);
                item.setValue(value);
                result.add(item);
            }
            Collections.sort(result, new Comparator<GuancetaiQuotaTrendItem>() {
                @Override
                public int compare(GuancetaiQuotaTrendItem o1, GuancetaiQuotaTrendItem o2) {
                    return o1.getKey().compareTo(o2.getKey());
                }
            });
        }
        return result;
    }

    @Override
    @FilterResultAnno({GuanceTaiPageAvgOnlineTimeFilter.class, GuancetaiPageHandlerResultViewFilter.class})
    public List<GuancetaiPageDetail> getGuancetaiPageDetailHuiZongList(GuancetaiPageQuotaParam param) throws Exception {
       if(param.getDeviceStatus() == null || param.getDeviceStatus() == 1){
           return this.getGuancetaiPageDetailHuiZongListInner(param);
       }
        param.setDeviceStatus(null);
        List<GuancetaiPageDetail> all = this.getGuancetaiPageDetailHuiZongListInner(param);
        param.setDeviceStatus(1);
        List<GuancetaiPageDetail> on = this.getGuancetaiPageDetailHuiZongListInner(param);
        List<GuancetaiPageDetail> result = new ArrayList<GuancetaiPageDetail>();
        this.mergeGuancetaiPageDetailResult(all, on, result);
        return result;
    }

    private List<GuancetaiPageDetail> getGuancetaiPageDetailHuiZongListInner(GuancetaiPageQuotaParam param) throws Exception  {
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(INDEX_GCT_PAGE + dateformat.format(param.getQueryDate()));
        BoolQueryBuilder boolBuilder = getBooleanQueryBuilder(param);
        boolBuilder.mustNot(QueryBuilders.termQuery("page_name", ""));
        TermsAggregationBuilder sourceAggregation = AggregationBuilders.terms("source").field("source").size(Integer.MAX_VALUE);
        TermsAggregationBuilder pageIdAggregation = AggregationBuilders.terms("pageId").field("pageid").size(Integer.MAX_VALUE);
        TermsAggregationBuilder pageNameAggregation = AggregationBuilders.terms("pageName").field("page_name").size(Integer.MAX_VALUE);
        SumAggregationBuilder visitSumAggregation = AggregationBuilders.sum("visitCount").field("page_visit_num");
        SumAggregationBuilder visitTimeSumAggregation = AggregationBuilders.sum("onlineTimeEveryTime").field("page_visit_time");
        CardinalityAggregationBuilder deviceAggregation = AggregationBuilders.cardinality("visitUsers")
                .field("deviceid").precisionThreshold(PRECISION_THRESHOLD);

        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder))
                .addAggregation(sourceAggregation.subAggregation(pageIdAggregation
                        .subAggregation(pageNameAggregation
                                .subAggregation(visitSumAggregation).subAggregation(visitTimeSumAggregation).subAggregation(deviceAggregation))))
                .execute()
                .actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        List<GuancetaiPageDetail> result = new ArrayList<GuancetaiPageDetail>();
        helper2(aggregationMap, fieldCache, new GuancetaiPageDetail(), result, "pageId");
        Collections.sort(result);
        return result;
    }

    @Override
    @FilterResultAnno({GuanceTaiPageAvgOnlineTimeFilter.class, GuancetaiPageHandlerResultViewFilter.class})
    public List<GuancetaiPageDetail> getGuancetaiPageDetailList(GuancetaiPageQuotaParam param) throws Exception {
        List<GuancetaiPageDetail> result = new ArrayList<GuancetaiPageDetail>();
        if(param.getDeviceStatus() == null || param.getDeviceStatus() == 1){
            result = this.getGuancetaiPageDetailListInner(param);
        }else {
            param.setDeviceStatus(null);
            List<GuancetaiPageDetail> all = this.getGuancetaiPageDetailListInner(param);
            param.setDeviceStatus(1);
            List<GuancetaiPageDetail> on = this.getGuancetaiPageDetailListInner(param);
            this.mergeGuancetaiPageDetailResult(all, on, result);
        }
        for (GuancetaiPageDetail detail : result) {
            detail.setTime(dateformat.format(param.getQueryDate()));
        }
        Collections.sort(result, new Comparator<GuancetaiPageDetail>() {
            @Override
            public int compare(GuancetaiPageDetail o1, GuancetaiPageDetail o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });
        return result;
    }

    private List<GuancetaiPageDetail> getGuancetaiPageDetailListInner(final GuancetaiPageQuotaParam param) throws Exception {
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(INDEX_GCT_PAGE + dateformat.format(param.getQueryDate()));
        BoolQueryBuilder boolBuilder = getBooleanQueryBuilder(param);
        boolBuilder.mustNot(QueryBuilders.termQuery("page_name", ""));
        //TermsAggregationBuilder hourAggregation = AggregationBuilders.terms("time").field("hour").size(Integer.MAX_VALUE);
        TermsAggregationBuilder sourceAggregation = AggregationBuilders.terms("source").field("source").size(Integer.MAX_VALUE);
        TermsAggregationBuilder pageIdAggregation = AggregationBuilders.terms("pageId").field("pageid").size(Integer.MAX_VALUE);
        TermsAggregationBuilder pageNameAggregation = AggregationBuilders.terms("pageName").field("page_name").size(Integer.MAX_VALUE);
        SumAggregationBuilder visitSumAggregation = AggregationBuilders.sum("visitCount").field("page_visit_num");
        SumAggregationBuilder visitTimeSumAggregation = AggregationBuilders.sum("onlineTimeEveryTime").field("page_visit_time");
        CardinalityAggregationBuilder deviceAggregation = AggregationBuilders.cardinality("visitUsers")
                .field("deviceid").precisionThreshold(PRECISION_THRESHOLD);

        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder))
                .addAggregation(sourceAggregation
                                .subAggregation(pageIdAggregation
                                        .subAggregation(pageNameAggregation
                                                .subAggregation(deviceAggregation).subAggregation(visitSumAggregation).subAggregation(visitTimeSumAggregation))))
                .execute()
                .actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        List<GuancetaiPageDetail> result = new ArrayList<GuancetaiPageDetail>();
        helper(aggregationMap, fieldCache, new GuancetaiPageDetail(), result, "pageId");
        for (GuancetaiPageDetail detail : result) {
            detail.setTime(dateformat.format(param.getQueryDate()));
        }
        return result;
    }

}
