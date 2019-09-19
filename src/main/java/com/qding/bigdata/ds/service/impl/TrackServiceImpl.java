package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.annotation.FilterResultAnno;
import com.qding.bigdata.ds.component.*;
import com.qding.bigdata.ds.model.TrackMsg;
import com.qding.bigdata.ds.model.TrackParam;
import com.qding.bigdata.ds.service.TrackService;
import com.qding.bigdata.ds.util.BeanMapper;
import com.qding.bigdata.ds.util.EsInstance;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.InternalNumericMetricsAggregation;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author yanpf
 * @date 2018/7/13 14:46
 * @description
 */

@Service
public class TrackServiceImpl implements TrackService {



    /**
     * 递归的解析聚合后的数据
     * @param aggregationMap
     * @param cache
     * @param map
     * @param result
     * @throws IllegalAccessException
     */
    public static void helper(Map<String, Aggregation> aggregationMap, TrackMsg cache, Map<String, Field> map, List<TrackMsg> result) throws IllegalAccessException {

        if (CollectionUtils.isEmpty(aggregationMap)) {
            result.add(BeanMapper.map(cache, TrackMsg.class));
            return;
        }
        Set<Map.Entry<String, Aggregation>> entrySet = aggregationMap.entrySet();
        boolean hasBucket = false;
        for (Map.Entry<String, Aggregation> entry : entrySet) {
            String key = entry.getKey();
            Aggregation aggregation = entry.getValue();
            Field field = map.get(key);
            field.setAccessible(true);
            if (aggregation instanceof StringTerms) {
                hasBucket = true;
                StringTerms stringTerms = ((StringTerms) aggregation);
                System.out.println(stringTerms.getName());
                for (Terms.Bucket bucket : stringTerms.getBuckets()) {
                    String bucketVal = bucket.getKeyAsString();
                    if (field != null && bucket != null) {
                        setValue(bucketVal, field, cache);
                    }
                    helper(bucket.getAggregations().asMap(), cache, map, result);
                }
            } else if (aggregation instanceof InternalNumericMetricsAggregation.SingleValue) {
                InternalNumericMetricsAggregation.SingleValue internalSum = ((InternalNumericMetricsAggregation.SingleValue) aggregation);
                Double value = internalSum.value();
                if (value != null && field != null) {
                    setValue(value.longValue(), field, cache);
                }
            }
        }

        if (!hasBucket) {
            result.add(BeanMapper.map(cache, TrackMsg.class));
        }
    }

    public static void setValue(Object value, Field field, Object ref) throws IllegalAccessException {
        Class<?> type = field.getType();
        if(type == String.class){
            field.set(ref, value.toString());
        }else if(type == long.class || type == Long.class){
            if(value instanceof Long){
                field.set(ref, value);
            }else {
                field.set(ref, Long.valueOf(value.toString()));
            }
        }else {
            field.set(ref, value);
        }
    }

    @Override
    @FilterResultAnno({LoginCountResultFilter.class, TrackResultViewFilter.class, PageHandlerResultViewFilter.class})
    public List<TrackMsg> queryTrackDetails(TrackParam param) throws IllegalAccessException {
        TransportClient client = EsInstance.getInstance();

        SearchRequestBuilder srb = client.prepareSearch(INDEX);
        //查询source
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        if (param != null && !StringUtils.isEmpty(param.getSecondSource())) {
            boolBuilder.must(QueryBuilders.matchQuery("sec_source", param.getSecondSource()));
        }

        //查询时间
        RangeQueryBuilder rangequerybuilder = QueryBuilders
                .rangeQuery("time");

        if (param != null && param.getStartDate() != null) {
            rangequerybuilder.from(dateFormat.format(param.getStartDate()));
        }
        if (param != null && param.getEndDate() != null) {
            setEndDate(param);
            rangequerybuilder.to(dateFormat.format(param.getEndDate()));
        }

        TermsAggregationBuilder teamUserName = AggregationBuilders.terms("userName").field("user_name").size(Integer.MAX_VALUE);
        TermsAggregationBuilder teamUser = AggregationBuilders.terms("user").field("user").size(Integer.MAX_VALUE);
        //TermsAggregationBuilder teamRefEvent = AggregationBuilders.terms("refEvent").field("ref_event");
        TermsAggregationBuilder teamFirstSource = AggregationBuilders.terms("firSource").field("fir_source").size(Integer.MAX_VALUE);
        TermsAggregationBuilder teamSecSource = AggregationBuilders.terms("secSource").field("sec_source").order(BucketOrder.aggregation("onlineTime", true)).size(Integer.MAX_VALUE);
        SumAggregationBuilder teamSum = AggregationBuilders.sum("onlineTime").field("online_time");
        ValueCountAggregationBuilder teamCount = AggregationBuilders.count("eventCount").field("event");
        ValueCountAggregationBuilder pvCount = AggregationBuilders.count("pv").field("sec_source");

       /* if(param != null){
             srb.setFrom(param.getOffset())
                    .setSize(param.getPageCount());
        }*/

        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder)
                .must(rangequerybuilder))
                .addAggregation(teamUser.subAggregation(teamUserName.subAggregation(teamFirstSource.subAggregation(teamSecSource.subAggregation(teamSum).subAggregation(teamCount).subAggregation(pvCount)))))
                .execute()
                .actionGet();

        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        TrackMsg cache = new TrackMsg();
        List<TrackMsg> result = new ArrayList<TrackMsg>();
        Map<String, Field> map = new HashMap<String, Field>();

        for (Field field : TrackMsg.class.getDeclaredFields()) {
           map.put(field.getName(), field);
        }
        helper(aggregationMap, cache, map, result);

        return result;
    }

    private void setEndDate(TrackParam param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(param.getEndDate());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        param.setEndDate(calendar.getTime());
    }

    /**
     * 从产品维度查询
     * @param param
     * @return
     * @throws IllegalAccessException
     */
    @Override
    @FilterResultAnno({/*ActiveUserResultFilter.class, */ LoginCountByProductResultFilter.class, TrackResultViewFilter.class, PageHandlerResultViewFilter.class})
    public List<TrackMsg> queryTrackSummary(TrackParam param) throws IllegalAccessException {
        TransportClient client = EsInstance.getInstance();

        SearchRequestBuilder srb = client.prepareSearch(INDEX);

        //查询source
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        if (param != null && !StringUtils.isEmpty(param.getSecondSource())) {
            boolBuilder.must(QueryBuilders.termQuery("sec_source", param.getSecondSource()));
        }

        //查询时间
        RangeQueryBuilder rangequerybuilder = QueryBuilders
                .rangeQuery("time");
        if (param != null && param.getStartDate() != null) {
            rangequerybuilder.from(dateFormat.format(param.getStartDate()));
        }
        if (param != null && param.getEndDate() != null) {
            setEndDate(param);
            rangequerybuilder.to(dateFormat.format(param.getEndDate()));
        }

        TermsAggregationBuilder teamFirstSource = AggregationBuilders.terms("firSource").field("fir_source").size(Integer.MAX_VALUE);
        TermsAggregationBuilder teamSecSource = AggregationBuilders.terms("secSource").field("sec_source").size(Integer.MAX_VALUE);
        ValueCountAggregationBuilder termpv = AggregationBuilders.count("pv").field("user");
        CardinalityAggregationBuilder termuv = AggregationBuilders.cardinality("uv").field("user");
        /*if (param != null){
            srb.setFrom(param.getOffset())
                    .setSize(param.getPageCount());
        }*/
        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder)
                .must(rangequerybuilder))
                .addAggregation(teamFirstSource.subAggregation(teamSecSource.subAggregation(termpv).subAggregation(termuv)))
                .execute()
                .actionGet();

        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        TrackMsg cache = new TrackMsg();
        List<TrackMsg> result = new ArrayList<TrackMsg>();
        Map<String, Field> map = new HashMap<String, Field>();

        for (Field field : TrackMsg.class.getDeclaredFields()) {
            map.put(field.getName(), field);
        }
        helper(aggregationMap, cache, map, result);

        return result;
    }
}
