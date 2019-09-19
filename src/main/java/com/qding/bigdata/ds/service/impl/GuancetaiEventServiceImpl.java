package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.annotation.FilterResultAnno;
import com.qding.bigdata.ds.component.GuancetaiEventTypeHandler;
import com.qding.bigdata.ds.component.GuancetaiPageHandlerResultViewFilter;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.service.GuancetaiEventService;
import com.qding.bigdata.ds.util.EsInstance;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.InternalNumericMetricsAggregation;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author yanpf
 * @date 2018/12/27 18:28
 * @description
 */

@Service
public class GuancetaiEventServiceImpl extends GuancetaiServiceImpl implements GuancetaiEventService {

    private Map<String, Field> fieldCache = new HashMap<String, Field>() {
        {
            GuancetaiEventServiceImpl.getDeclaredFieldsIterator(GuancetaiEventDetail.class, this);
        }
    };

    @Override
    public GuancetaiQuotaInfo getEventStartupTimesInfo(GuancetaiQuotaParam param) {
        GuancetaiQuotaInfo result = new GuancetaiQuotaInfo();
        Calendar calendar = Calendar.getInstance();
        Double current = this.getEventStartupTimesInfo(param, calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        Double last = this.getEventStartupTimesInfo(param, calendar.getTime());
        result.setCurrent(current);
        result.setLast(last);
        result.setIncreasement();
        return result;
    }

    private Double getEventStartupTimesInfo(GuancetaiQuotaParam param, Date date) {
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(INDEX_GCT_EVENT + dateformat.format(date));
        //查询source
        param.setQueryDate(date);
        BoolQueryBuilder boolBuilder = getBooleanQueryBuilder(param);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        boolBuilder.must(QueryBuilders.rangeQuery("hour").lte(hour));

        SumAggregationBuilder teamSum = AggregationBuilders.sum("visit_count").field("visit_count");
        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder))
                .addAggregation(teamSum)
                .execute()
                .actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        InternalSum internalSum = (InternalSum) aggregationMap.get("visit_count");
        Double value = internalSum.getValue();
        return value;
    }

    @Override
    public GuancetaiQuotaInfo getEventStartupUsersInfo(GuancetaiQuotaParam param) {
        return this.getStartupUsersInfo(param, INDEX_GCT_EVENT);
    }

    @Override
    public List<GuancetaiQuotaTrendItem> getEventStartupTimesInfoTrend(GuancetaiEventQuotaParam param) {
        List<GuancetaiQuotaTrendItem> result = new ArrayList<GuancetaiQuotaTrendItem>();
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(INDEX_GCT_EVENT + dateformat.format(param.getQueryDate()));
        //查询source
        BoolQueryBuilder boolBuilder = getBooleanQueryBuilder(param);

        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("hour").field("hour").size(Integer.MAX_VALUE);
        SumAggregationBuilder teamSum = AggregationBuilders.sum("visit_count").field("visit_count");
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
                InternalNumericMetricsAggregation.SingleValue visitCount = (InternalNumericMetricsAggregation.SingleValue) stringAggregationMap.get("visit_count");
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
    public List<GuancetaiQuotaTrendItem> getEventStartupUsersInfoTrend(GuancetaiEventQuotaParam param) {
        return this.getStartupUsersInfoTrend(param, INDEX_GCT_EVENT);
    }

    @Override
    @FilterResultAnno({GuancetaiEventTypeHandler.class, GuancetaiPageHandlerResultViewFilter.class})
    public List<GuancetaiEventDetail> getGuancetaiEventDetailHuiZongList(GuancetaiEventQuotaParam param) throws Exception {
        if (param.getDeviceStatus() == null || param.getDeviceStatus() == 1) {
            return this.getGuancetaiEventDetailHuiZongListInner(param);
        }
        param.setDeviceStatus(null);
        List<GuancetaiEventDetail> all = this.getGuancetaiEventDetailHuiZongListInner(param);
        param.setDeviceStatus(1);
        List<GuancetaiEventDetail> on = this.getGuancetaiEventDetailHuiZongListInner(param);
        List<GuancetaiEventDetail> result = new ArrayList<GuancetaiEventDetail>();
        this.mergeGuancetaiEventDetailResult(all, on, result);
        return result;
    }

    private List<GuancetaiEventDetail> getGuancetaiEventDetailHuiZongListInner(GuancetaiEventQuotaParam param) throws Exception {
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(INDEX_GCT_EVENT + dateformat.format(param.getQueryDate()));
        BoolQueryBuilder boolBuilder = getBooleanQueryBuilder(param);
        boolBuilder.mustNot(QueryBuilders.termQuery("eventname", "")).mustNot(QueryBuilders.termQuery("pagename", ""));
        TermsAggregationBuilder sourceAggregation = AggregationBuilders.terms("source").field("source").size(Integer.MAX_VALUE);
        TermsAggregationBuilder eventIdAggregation = AggregationBuilders.terms("eventId").field("eventid").size(Integer.MAX_VALUE);
        TermsAggregationBuilder eventNameAggregation = AggregationBuilders.terms("eventName").field("eventname").size(Integer.MAX_VALUE);
        TermsAggregationBuilder pageNameAggregation = AggregationBuilders.terms("pageName").field("pagename").size(Integer.MAX_VALUE);
        SumAggregationBuilder visitSumAggregation = AggregationBuilders.sum("visitCount").field("visit_count");
        CardinalityAggregationBuilder deviceAggregation = AggregationBuilders.cardinality("visitUsers")
                .field("deviceid").precisionThreshold(PRECISION_THRESHOLD);

        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder))
                .addAggregation(sourceAggregation
                        .subAggregation(pageNameAggregation
                                .subAggregation(eventIdAggregation
                                        .subAggregation(eventNameAggregation
                                                .subAggregation(visitSumAggregation).subAggregation(deviceAggregation)))))
                .execute()
                .actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        List<GuancetaiEventDetail> result = new ArrayList<GuancetaiEventDetail>();
        helper2(aggregationMap, fieldCache, new GuancetaiEventDetail(), result, "eventId");
        Collections.sort(result);
        return result;
    }

    @Override
    @FilterResultAnno({GuancetaiEventTypeHandler.class, GuancetaiPageHandlerResultViewFilter.class})
    public List<GuancetaiEventDetail> getGuancetaiEventDetailList(GuancetaiEventQuotaParam param) throws Exception {
        if (param.getDeviceStatus() == null || param.getDeviceStatus() == 1) {
            return this.getGuancetaiEventDetailListInner(param);
        }
        param.setDeviceStatus(null);
        List<GuancetaiEventDetail> all = this.getGuancetaiEventDetailListInner(param);
        param.setDeviceStatus(1);
        List<GuancetaiEventDetail> on = this.getGuancetaiEventDetailListInner(param);
        List<GuancetaiEventDetail> result = new ArrayList<GuancetaiEventDetail>();
        this.mergeGuancetaiEventDetailResult(all, on, result);
        return result;
    }

    private List<GuancetaiEventDetail> getGuancetaiEventDetailListInner(GuancetaiEventQuotaParam param) throws Exception {
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(INDEX_GCT_EVENT + dateformat.format(param.getQueryDate()));
        BoolQueryBuilder boolBuilder = getBooleanQueryBuilder(param);
        boolBuilder.mustNot(QueryBuilders.termQuery("eventname", "")).mustNot(QueryBuilders.termQuery("pagename", ""));
        //TermsAggregationBuilder hourAggregation = AggregationBuilders.terms("time").field("hour").size(Integer.MAX_VALUE);
        TermsAggregationBuilder sourceAggregation = AggregationBuilders.terms("source").field("source").size(Integer.MAX_VALUE);
        TermsAggregationBuilder eventIdAggregation = AggregationBuilders.terms("eventId").field("eventid").size(Integer.MAX_VALUE);
        TermsAggregationBuilder eventNameAggregation = AggregationBuilders.terms("eventName").field("eventname").size(Integer.MAX_VALUE);
        TermsAggregationBuilder pageNameAggregation = AggregationBuilders.terms("pageName").field("pagename").size(Integer.MAX_VALUE);
        SumAggregationBuilder visitSumAggregation = AggregationBuilders.sum("visitCount").field("page_visit_num");
        CardinalityAggregationBuilder deviceAggregation = AggregationBuilders.cardinality("visitUsers")
                .field("deviceid").precisionThreshold(PRECISION_THRESHOLD);

        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder))
                .addAggregation(sourceAggregation
                                .subAggregation(pageNameAggregation
                                        .subAggregation(eventIdAggregation
                                                .subAggregation(eventNameAggregation
                                                        .subAggregation(visitSumAggregation).subAggregation(deviceAggregation)))))
                .execute()
                .actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        List<GuancetaiEventDetail> result = new ArrayList<GuancetaiEventDetail>();
        helper2(aggregationMap, fieldCache, new GuancetaiEventDetail(), result, "eventId");
        for (GuancetaiEventDetail detail : result) {
            detail.setTime(dateformat.format(param.getQueryDate()));
        }
        Collections.sort(result);
        return result;
    }

}
