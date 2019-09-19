package com.qding.bigdata.ds.component;

import com.qding.bigdata.ds.model.TrackMsg;
import com.qding.bigdata.ds.model.TrackParam;
import com.qding.bigdata.ds.service.TrackService;
import com.qding.bigdata.ds.service.impl.TrackServiceImpl;
import com.qding.bigdata.ds.util.EsInstance;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author yanpf
 * @date 2018/7/17 11:50
 * @description  查询活跃用户
 */

@Component
public class ActiveUserResultFilter implements ResultFilter<List<TrackMsg>, List<TrackMsg>, TrackParam> {



    @Override
    public List<TrackMsg> doAfter(List<TrackMsg> trackMsgList, TrackParam param) {
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(TrackService.INDEX);

        //查询event事件
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(QueryBuilders.matchQuery("event", TrackService.EVENT_LOGIN));

        //查询时间必须是今天的
        RangeQueryBuilder rangequerybuilder = QueryBuilders
                .rangeQuery("time");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 10);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        System.out.println(TrackService.dateFormat.format(zero));
        rangequerybuilder.from(TrackService.dateFormat.format(zero));
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (TrackMsg trackMsg : trackMsgList) {
            MatchQueryBuilder firSource = QueryBuilders.matchQuery("fir_source", trackMsg.getFirSource());
            MatchQueryBuilder secSource = QueryBuilders.matchQuery("sec_source", trackMsg.getSecSource());
            boolQueryBuilder.should(QueryBuilders.boolQuery().must(firSource).must(secSource));

        }

        //获取每个模块访问用户数量
        TermsAggregationBuilder teamFirstSource = AggregationBuilders.terms("firSource").field("fir_source").size(Integer.MAX_VALUE);
        TermsAggregationBuilder teamSecSource = AggregationBuilders.terms("secSource").field("sec_source").size(Integer.MAX_VALUE);
        TermsAggregationBuilder teamUser = AggregationBuilders.terms("user").field("user");
        ValueCountAggregationBuilder termEvent = AggregationBuilders.count("pv").field("event");

        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder)
                .must(rangequerybuilder)
                .must(boolQueryBuilder))
                .addAggregation(teamFirstSource.subAggregation(teamSecSource.subAggregation(teamUser.subAggregation(termEvent))))
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

        Map<String, Long> activeUsers = new HashMap<String, Long>();
        for (TrackMsg trackMsg : result) {
            if(trackMsg.getPv() >= 3){
                String key = trackMsg.getFirSource() + "$" + trackMsg.getSecSource();
                if(activeUsers.get(key) != null){
                    activeUsers.put(key, activeUsers.get(key) + 1);
                }else {
                    activeUsers.put(key, 1L);
                }
            }
        }

        for (TrackMsg trackMsg : trackMsgList) {
            String key = trackMsg.getFirSource() + "$" + trackMsg.getSecSource();
            if(activeUsers.get(key) != null){
                trackMsg.setActiveUsers(activeUsers.get(key));
            }else {
                trackMsg.setActiveUsers(0L);
            }
        }

        return trackMsgList;
    }
}
