package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.model.GuancetaiQuotaInfo;
import com.qding.bigdata.ds.model.GuancetaiQuotaParam;
import com.qding.bigdata.ds.model.GuancetaiQuotaTrendItem;
import com.qding.bigdata.ds.service.GuancetaiBaseService;
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
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yanpf
 * @date 2018/12/26 11:52
 * @description
 */

@Service
public class GuancetaiBaseServiceImpl extends GuancetaiServiceImpl implements GuancetaiBaseService {

    private static Logger logger = LoggerFactory.getLogger(GuancetaiBaseServiceImpl.class);

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

    private Double getBusinessOnlineTime(GuancetaiQuotaParam param, Date date) {
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(INDEX_GCT_BASE + dateformat.format(date));
        param.setQueryDate(date);
        BoolQueryBuilder boolBuilder = this.getBooleanQueryBuilder(param);
        //如果做同比 需要 今天和昨天小时是一致的
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        boolBuilder.must(QueryBuilders.rangeQuery("hour").lte(hour));
        SumAggregationBuilder teamSum = AggregationBuilders.sum("appVisitTime").field("app_visit_time");
        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder))
                .addAggregation(teamSum)
                .execute()
                .actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        InternalSum internalSum = (InternalSum) aggregationMap.get("appVisitTime");
        Double value = internalSum.getValue();
        return value;

    }

    @Override
    public List<GuancetaiQuotaTrendItem> getBusinessOnlineTimeTrend(GuancetaiQuotaParam param) {
        TransportClient client = EsInstance.getInstance();
        List<GuancetaiQuotaTrendItem> result = new ArrayList<GuancetaiQuotaTrendItem>();
        SearchRequestBuilder srb = client.prepareSearch(INDEX_GCT_BASE + dateformat.format(param.getQueryDate()));
        //查询source
        BoolQueryBuilder boolBuilder = getBooleanQueryBuilder(param);

        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("hour").field("hour").size(Integer.MAX_VALUE);
        SumAggregationBuilder teamSum = AggregationBuilders.sum("appVisitTime").field("app_visit_time");
        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder))
                .addAggregation(aggregationBuilder.subAggregation(teamSum))
                .execute()
                .actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        if(aggregationMap.get("hour") instanceof InternalMappedTerms) {
            List<InternalTerms.Bucket<LongTerms.Bucket>> buckets = ((InternalMappedTerms) aggregationMap.get("hour")).getBuckets();
            for (Terms.Bucket bucket : buckets) {
                String key = bucket.getKey().toString();
                Map<String, Aggregation> stringAggregationMap = bucket.getAggregations().asMap();
                InternalNumericMetricsAggregation.SingleValue visitCount = (InternalNumericMetricsAggregation.SingleValue) stringAggregationMap.get("appVisitTime");
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
    public GuancetaiQuotaInfo getStartupTimesInfo(GuancetaiQuotaParam param) {

        GuancetaiQuotaInfo result = new GuancetaiQuotaInfo();
        Calendar calendar = Calendar.getInstance();
        Double current = this.getStartupTimesInfo(param, calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        Double last = this.getStartupTimesInfo(param, calendar.getTime());
        result.setCurrent(current);
        result.setLast(last);
        result.setIncreasement();
        return result;
    }

    private Double getStartupTimesInfo(GuancetaiQuotaParam param, Date date) {
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(INDEX_GCT_BASE + dateformat.format(date));
        //查询source
        param.setQueryDate(date);
        BoolQueryBuilder boolBuilder = getBooleanQueryBuilder(param);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        boolBuilder.must(QueryBuilders.rangeQuery("hour").lte(hour));

        SumAggregationBuilder teamSum = AggregationBuilders.sum("startupTimes").field("startup_times");
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

    @Override
    public GuancetaiQuotaInfo getStartupUsersInfo(GuancetaiQuotaParam param) {
       param.setStartupTimes(1);
       return this.getStartupUsersInfo(param, INDEX_GCT_BASE);
    }

    @Override
    public List<GuancetaiQuotaTrendItem> getActiveUsersInfoTrend(GuancetaiQuotaParam param) {
        return this.getStartupUsersInfoTrend(param, INDEX_GCT_BASE);
    }

    @Override
    public List<GuancetaiQuotaTrendItem> getStartupTimesInfoTrend(GuancetaiQuotaParam param) {
        List<GuancetaiQuotaTrendItem> result = new ArrayList<GuancetaiQuotaTrendItem>();
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(INDEX_GCT_BASE + dateformat.format(param.getQueryDate()));
        //查询source
        BoolQueryBuilder boolBuilder = getBooleanQueryBuilder(param);

        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("hour").field("hour").size(Integer.MAX_VALUE);
        SumAggregationBuilder teamSum = AggregationBuilders.sum("startup_times").field("startup_times");
        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder))
                .addAggregation(aggregationBuilder.subAggregation(teamSum))
                .execute()
                .actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        if(aggregationMap.get("hour") instanceof InternalMappedTerms) {
            List<InternalTerms.Bucket<LongTerms.Bucket>> buckets = ((InternalMappedTerms) aggregationMap.get("hour")).getBuckets();
            for (Terms.Bucket bucket : buckets) {
                String key = bucket.getKey().toString();
                Map<String, Aggregation> stringAggregationMap = bucket.getAggregations().asMap();
                InternalNumericMetricsAggregation.SingleValue visitCount = (InternalNumericMetricsAggregation.SingleValue) stringAggregationMap.get("startup_times");
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
    public List<GuancetaiQuotaTrendItem> getStartupUsersInfoTrend(GuancetaiQuotaParam param) {
        return this.getStartupUsersInfoTrend(param, INDEX_GCT_BASE);
    }

}
