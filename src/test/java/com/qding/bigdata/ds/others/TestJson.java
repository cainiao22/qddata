package com.qding.bigdata.ds.others;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qding.bigdata.ds.service.TrackService;
import com.qding.bigdata.ds.util.EsInstance;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.io.ObjectInputStream;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.qding.bigdata.ds.service.TrackService.INDEX;

/**
 * @author yanpf
 * @date 2018/7/18 11:16
 * @description
 */
public class TestJson {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        JSONObject jsonObject = JSON.parseObject("{\n" +
                "    \"fir_source\": \"bigdata\",\n" +
                "    \"sec_source\": \"驾驶舱123456\",\n" +
                "    \"online_time\": 307416,\n" +
                "    \"time\": 1531724230307,\n" +
                "    \"ref_event\": {\n" +
                "        \"time\": \"time001\",\n" +
                "        \"city\": [\n" +
                "            \"city002\"\n" +
                "        ],\n" +
                "        \"yetai\": [\n" +
                "            \"formats001\"\n" +
                "        ],\n" +
                "        \"index\": []\n" +
                "    },\n" +
                "    \"event\": {\n" +
                "        \"time\": \"time001\",\n" +
                "        \"city\": [],\n" +
                "        \"yetai\": [\n" +
                "            \"formats001\"\n" +
                "        ],\n" +
                "        \"index\": []\n" +
                "    },\n" +
                "    \"ref_url\": \"http://localhost:2206/#/\",\n" +
                "    \"url\": \"http://localhost:2206/#/\"\n" +
                "}");

        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if(entry.getValue() instanceof JSONObject || entry.getValue() instanceof JSONArray){
                entry.setValue(entry.getValue().toString().replaceAll("\"", "\\\""));
            }
        }
        System.out.println(JSON.toJSONString(jsonObject));

        TransportClient client = EsInstance.getInstance();
        IndexRequestBuilder requestBuilder = client.prepareIndex(TrackService.INDEX, TrackService.INDEX).setSource(JSON.toJSONString(jsonObject));
        IndexResponse response = requestBuilder.execute().get();
        SearchRequestBuilder srb = client.prepareSearch(INDEX);
        BoolQueryBuilder eventBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("_id", response.getId()));
        SearchResponse searchResponse = srb.setQuery(QueryBuilders.boolQuery()
                .must(eventBuilder)).execute().get();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit);
        }
        GetResponse getFields = client.prepareGet(INDEX, INDEX, response.getId()).execute().get();
        //ObjectInputStream.GetField field = getFields.getField("event");
        Object value = null;

        JSONObject parseObject = JSON.parseObject(value.toString());
        System.out.println(parseObject);
        System.out.println(response);

    }
}
