import com.alibaba.fastjson.JSON;
import com.qding.bigdata.ds.model.GCTEventV2Param;
import com.qding.bigdata.ds.util.BeanMapper;
import com.qding.bigdata.ds.util.CommonUtil;
import com.qding.bigdata.ds.util.EsInstance;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

/**
 * Created by QDHL on 2018/7/4.
 */
public class Test1 {
    public static void main1(String[] args){
        /*SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");//设置日期格式
        *//*String a="2018/07/03";*//*
        Date date=new Date();
        Calendar calendar   =   new GregorianCalendar();
        calendar.setTime(date);
        //calendar.add(calendar.YEAR, 1);//把日期往后增加一年.整数往后推,负数往前移动
        //calendar.add(calendar.DAY_OF_MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        //calendar.add(calendar.WEEK_OF_MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
        date=calendar.getTime();   //这个时间就是日期往后推一天的结果
        System.out.println(date.toString());
        //String date = df.format(date);// new Date()为获取当前系统时间，也可使用当前时间戳
       *//* String date=a.replace("/","");*//*
        System.out.println(date);*/


       /* int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day - 1);
        cl = setCalendar(2017, 01, 12);
        getBeforeDay(cl);
        System.out.print("前一天:");
        printCalendar(cl);*/

       /* SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
        String ss=dft.format(date.getTime());
        *//*Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }*//*
        //System.out.println(dft.format(endDate));
        System.out.println(ss);*/

     /*   SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        String ss=df.format(calendar.getTime());
        System.out.println(ss);*/

       /* int i=1;
        double c=3;
        int d= (int) (i+c);*/


//        TransportClient client = EsInstance.getInstance();
//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices("gct_event.2018-12-25").setTypes("data").setQuery(boolQuery).setSize(0);
////        searchRequestBuilder.addAggregation(AggregationBuilders.sum("agg-hour").field("hour"));
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        //3、获取组装结果
//        SearchResponse sr = searchRequestBuilder.get();
//        System.out.println(searchRequestBuilder.toString());
//        map.put("totalHit", sr.getHits().getTotalHits());
//
//        LinkedHashMap<String, Object> aggMap = new LinkedHashMap<String, Object>();
//
//        LinkedHashMap<Object, Long> values = new LinkedHashMap<Object, Long>();
//        Terms aggregation = sr.getAggregations().get("agg_hour" );
//
//        for (Terms.Bucket bucket : aggregation.getBuckets()) {
//            if(bucket.getDocCount() > 0) {
//                String key = StringUtil.toString(bucket.getKey());
//
//                values.put(key, bucket.getDocCount());
//            }
//        }

        TransportClient client = EsInstance.getInstance();
        List<DiscoveryNode> connectedNodes = client.connectedNodes();
        for (DiscoveryNode discoveryNode : connectedNodes) {
            System.err.println(discoveryNode.getHostAddress());
        }
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("gct_page.2018-12-26").setTypes("data");
//        boolQuery.must(QueryBuilders.termQuery("hour",21));

//        TermsAggregationBuilder agg_hour = AggregationBuilders.terms("agg_hour").field("hour").size(Integer.MAX_VALUE);
        TermsAggregationBuilder agg_source = AggregationBuilders.terms("agg_source").field("source").size(Integer.MAX_VALUE);
        TermsAggregationBuilder agg_month = AggregationBuilders.terms("agg_month").field("month").size(Integer.MAX_VALUE);
        TermsAggregationBuilder agg_deviceid = AggregationBuilders.terms("agg_deviceid").field("hour").size(Integer.MAX_VALUE);
        TermsAggregationBuilder agg_day = AggregationBuilders.terms("agg_day").field("day").size(Integer.MAX_VALUE);
        System.out.println(searchRequestBuilder.toString());
        long beginTime = System.currentTimeMillis();
        SearchResponse sr = searchRequestBuilder.setQuery(QueryBuilders.boolQuery().must(boolQuery))
                .addAggregation(agg_source)
                .addAggregation(agg_deviceid)
                .addAggregation(agg_month)
                .addAggregation(agg_day)
                .execute()
                .actionGet();
        long totalHits = sr.getHits().totalHits;
        Terms aggregation = sr.getAggregations().get("agg_deviceid");

//        for (Terms.Bucket bucket : aggregation.getBuckets()) {
//            if (bucket.getDocCount() > 0)
//                System.out.println(bucket.getKey()+"--"+bucket.getDocCount());
//
//        }
        long endTime = System.currentTimeMillis();
        System.out.println("count sec = " + (endTime-beginTime));
        System.out.println("totalHits--"+aggregation.getBuckets().size());

    }

    static DateFormat dateFormat = DateFormat.getDateInstance();
    public static void main(String[] args) throws ParseException {
        List<GCTEventV2Param> paramList = new ArrayList<>();
        GCTEventV2Param param = new GCTEventV2Param();
        param.setDateType("month");
        param.setEventId("all");
        param.setAction("active");
        param.setCompanyId("all");
        param.setVersion("all");
        param.setDeviceType("all");
        param.setQRYcompanyId("all");
        param.setStartDate(dateFormat.parse("2019-04-01"));
        param.setEndDate(new Date());
        paramList.add(param);

        GCTEventV2Param param2 = BeanMapper.map(param, GCTEventV2Param.class);
        param2.setDateType("day");
        param2.setStartDate(dateFormat.parse("2019-04-01"));
        param2.setEndDate(new Date());
        paramList.add(param);
        System.out.println(JSON.toJSONString(paramList));
    }
}
