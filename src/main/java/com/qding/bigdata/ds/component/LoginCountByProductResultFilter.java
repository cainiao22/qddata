package com.qding.bigdata.ds.component;

import com.qding.bigdata.ds.model.TrackMsg;
import com.qding.bigdata.ds.model.TrackParam;
import com.qding.bigdata.ds.service.impl.TrackServiceImpl;
import com.qding.bigdata.ds.util.EsInstance;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qding.bigdata.ds.service.TrackService.EVENT_LOGIN;
import static com.qding.bigdata.ds.service.TrackService.INDEX;
import static com.qding.bigdata.ds.service.TrackService.dateFormat;

/**
 * @author yanpf
 * @date 2018/8/3 14:31
 * @description
 */

@Component
public class LoginCountByProductResultFilter implements ResultFilter<List<TrackMsg>, List<TrackMsg>, TrackParam> {
    @Override
    public List<TrackMsg> doAfter(List<TrackMsg> trackMsgList, TrackParam param) {
        TransportClient client = EsInstance.getInstance();

        SearchRequestBuilder srb = client.prepareSearch(INDEX);
        //查询source
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
      /*  if (param != null && !StringUtils.isEmpty(param.getSecondSource())) {
            boolBuilder.must(QueryBuilders.matchQuery("sec_source", param.getSecondSource()));
        }*/

        //过滤产品
        for (TrackMsg trackMsg : trackMsgList) {
            BoolQueryBuilder produceBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("sec_source", trackMsg.getSecSource()));
            boolBuilder.should(produceBuilder);

        }

        BoolQueryBuilder eventBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("event", EVENT_LOGIN));

        //查询时间
        RangeQueryBuilder rangequerybuilder = QueryBuilders
                .rangeQuery("time");

        if (param != null && param.getStartDate() != null) {
            rangequerybuilder.from(dateFormat.format(param.getStartDate()));
        }
        if (param != null && param.getEndDate() != null) {
            rangequerybuilder.to(dateFormat.format(param.getEndDate()));
        }

        TermsAggregationBuilder teamFirstSource = AggregationBuilders.terms("firSource").field("fir_source").size(Integer.MAX_VALUE);
        TermsAggregationBuilder teamSecSource = AggregationBuilders.terms("secSource").field("sec_source").size(Integer.MAX_VALUE);
        ValueCountAggregationBuilder teamCount = AggregationBuilders.count("loginCount").field("event");

        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder)
                .must(eventBuilder)
                .must(rangequerybuilder))
                .addAggregation(teamFirstSource.subAggregation(teamSecSource.subAggregation(teamCount)))
                .execute()
                .actionGet();

        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        TrackMsg cache = new TrackMsg();
        List<TrackMsg> result = new ArrayList<TrackMsg>();
        Map<String, Field> map = new HashMap<String, Field>();

        for (Field field : TrackMsg.class.getDeclaredFields()) {
            map.put(field.getName(), field);
        }
        try {
            TrackServiceImpl.helper(aggregationMap, cache, map, result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Map<String, Long> loginCountMap = new HashMap<String, Long>(result.size());
        for (TrackMsg trackMsg : result) {
            loginCountMap.put(trackMsg.getFirSource() + "#" + trackMsg.getSecSource(), trackMsg.getLoginCount());
        }

        for (TrackMsg trackMsg : trackMsgList) {
            Long loginCount = loginCountMap.get(trackMsg.getFirSource() + "#" + trackMsg.getSecSource());
            if(loginCount != null){
                trackMsg.setLoginCount(loginCount);
            }else {
                trackMsg.setLoginCount(0L);
            }

            trackMsg.setPv(trackMsg.getLoginCount());
        }

        return trackMsgList;
    }
}
