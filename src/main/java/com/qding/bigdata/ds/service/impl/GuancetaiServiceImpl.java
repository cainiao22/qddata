package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.util.BeanMapper;
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
import org.elasticsearch.search.aggregations.metrics.cardinality.InternalCardinality;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yanpf
 * @date 2018/12/27 15:17
 * @description
 */
public class GuancetaiServiceImpl {

    DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

    static final long PRECISION_THRESHOLD = 10000;

    /**
     * 构建统一的查询条件
     *
     * @param param
     * @return
     */
    protected BoolQueryBuilder getBooleanQueryBuilder(GuancetaiQuotaParam param) {
        //查询source
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();

        if(!StringUtils.isEmpty(param.getLesseeId())){
            boolBuilder.must(QueryBuilders.termQuery("lessee_id", param.getLesseeId()));
        }

        if (param.getStartupTimes() != null) {
            boolBuilder.must(QueryBuilders.rangeQuery("startup_times").gt(0));
        }

        if (param.getBusinessType() != null) {
            boolBuilder.must(QueryBuilders.matchQuery("business_type", param.getBusinessType()));
        }
        if (param.getDeviceStatus() != null) {
            boolBuilder.must(QueryBuilders.matchQuery("device_status", param.getDeviceStatus()));
        }
        if (!StringUtils.isEmpty(param.getSource())) {
            boolBuilder.must(QueryBuilders.matchQuery("source", param.getSource()));
        }

        if(param.getQueryDate() != null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(param.getQueryDate());
            int day = cal.get(Calendar.DAY_OF_MONTH);
            boolBuilder.must(QueryBuilders.matchQuery("day", String.format("%02d", day)));
        }

        if (param instanceof GuancetaiPageQuotaParam) {
            GuancetaiPageQuotaParam pageQuotaParam = (GuancetaiPageQuotaParam) param;
            if (!StringUtils.isEmpty(pageQuotaParam.getPageId())) {
                boolBuilder.must(QueryBuilders.termQuery("pageid", pageQuotaParam.getPageId()));
            }

        }

        if (param instanceof GuancetaiEventQuotaParam) {
            GuancetaiEventQuotaParam eventQuotaParam = (GuancetaiEventQuotaParam) param;
            if (!StringUtils.isEmpty(eventQuotaParam.getEventId())) {
                boolBuilder.must(QueryBuilders.termQuery("eventid", eventQuotaParam.getEventId()));
            }
        }
        return boolBuilder;
    }

    /**
     * 获取用户访问数量
     *
     * @param param
     * @return
     */
    public GuancetaiQuotaInfo getStartupUsersInfo(GuancetaiQuotaParam param, String index) {
        Calendar calendar = Calendar.getInstance();
        GuancetaiQuotaInfo result = new GuancetaiQuotaInfo();
        if (param.getDeviceStatus() == null || param.getDeviceStatus() == 1) {
            Double current = this.getStartupUsersInfo(param, calendar.getTime(), index);
            calendar.add(Calendar.DATE, -1);
            Double last = this.getStartupUsersInfo(param, calendar.getTime(), index);
            result.setCurrent(current);
            result.setLast(last);
            result.setIncreasement();
        } else {
            param.setDeviceStatus(null);
            Double currentAll = this.getStartupUsersInfo(param, calendar.getTime(), index);
            calendar.add(Calendar.DATE, -1);
            Double lastAll = this.getStartupUsersInfo(param, calendar.getTime(), index);
            calendar.add(Calendar.DATE, 1);
            param.setDeviceStatus(1);
            Double currentOn = this.getStartupUsersInfo(param, calendar.getTime(), index);
            calendar.add(Calendar.DATE, -1);
            Double lastOn = this.getStartupUsersInfo(param, calendar.getTime(), index);
            Double current = currentAll - currentOn;
            Double last = lastAll - lastOn;
            result.setCurrent(current);
            result.setLast(last);
            result.setIncreasement();
        }
        return result;
    }

    /**
     * 合并结果，登录/未登录
     *
     * @param param
     * @param date
     * @param index
     * @return
     */
    protected Double getStartupUsersInfo(GuancetaiQuotaParam param, Date date, String index) {
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(index + dateformat.format(date));
        param.setQueryDate(date);
        BoolQueryBuilder boolBuilder = getBooleanQueryBuilder(param);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        boolBuilder.must(QueryBuilders.rangeQuery("hour").lte(hour));
        CardinalityAggregationBuilder cardinalityAggregationBuilder = AggregationBuilders.cardinality("deviceid")
                .field("deviceid").precisionThreshold(PRECISION_THRESHOLD);
        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder))
                .addAggregation(cardinalityAggregationBuilder)
                .setSize(0)
                .execute()
                .actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        InternalCardinality aggregation = (InternalCardinality) aggregationMap.get("deviceid");
        long value = aggregation.getValue();
        return Double.valueOf(value);
    }

    protected void mergetResult(List<GuancetaiQuotaTrendItem> all, List<GuancetaiQuotaTrendItem> on, List<GuancetaiQuotaTrendItem> result) {
        int i = 0, j = 0;
        while (i < all.size() && j < on.size()) {
            GuancetaiQuotaTrendItem allItem = all.get(i);
            GuancetaiQuotaTrendItem onItem = on.get(j);
            if (allItem.getKey().compareTo(onItem.getKey()) < 0) {
                result.add(allItem);
                i++;
            } else if (allItem.getKey().compareTo(onItem.getKey()) == 0) {
                allItem.setValue(allItem.getValue() - onItem.getValue());
                result.add(allItem);
                i++;
                j++;
            } else {
                onItem.setValue(-onItem.getValue());
                result.add(onItem);
                j++;
            }
        }
        while (i < all.size()) {
            result.add(all.get(i));
            i++;
        }
        while (j < on.size()) {
            GuancetaiQuotaTrendItem item = on.get(j);
            item.setValue(-item.getValue());
            result.add(item);
            j++;
        }
    }

    protected void mergeGuancetaiPageDetailResult(List<GuancetaiPageDetail> all, List<GuancetaiPageDetail> on, List<GuancetaiPageDetail> result) {
        int i = 0, j = 0;
        while (i < all.size() && j < on.size()) {
            GuancetaiPageDetail allItem = all.get(i);
            GuancetaiPageDetail onItem = on.get(j);
            if (allItem.compareTo(onItem) < 0) {
                result.add(allItem);
                i++;
            } else if (allItem.compareTo(onItem) == 0) {
                allItem.setVisitCount(allItem.getVisitCount() - onItem.getVisitCount());
                allItem.setVisitUsers(allItem.getVisitUsers() - onItem.getVisitUsers());
                allItem.setOnlineTimeEveryTime(allItem.getOnlineTimeEveryTime() - onItem.getOnlineTimeEveryTime());
                result.add(allItem);
                i++;
                j++;
            } else {
                onItem.setVisitCount(-onItem.getVisitCount());
                onItem.setVisitUsers(-onItem.getVisitUsers());
                onItem.setOnlineTimeEveryTime(-onItem.getOnlineTimeEveryTime());
                result.add(onItem);
                j++;
            }
        }
        while (i < all.size()) {
            result.add(all.get(i));
            i++;
        }
        while (j < on.size()) {
            GuancetaiPageDetail onItem = on.get(j);
            onItem.setVisitCount(-onItem.getVisitCount());
            onItem.setVisitUsers(-onItem.getVisitUsers());
            onItem.setOnlineTimeEveryTime(-onItem.getOnlineTimeEveryTime());
            result.add(onItem);
            j++;
        }
    }

    protected void mergeGuancetaiEventDetailResult(List<GuancetaiEventDetail> all, List<GuancetaiEventDetail> on, List<GuancetaiEventDetail> result) {
        int i = 0, j = 0;
        while (i < all.size() && j < on.size()) {
            GuancetaiEventDetail allItem = all.get(i);
            GuancetaiEventDetail onItem = on.get(j);
            if (allItem.compareTo(onItem) < 0) {
                result.add(allItem);
                i++;
            } else if (allItem.compareTo(onItem) == 0) {
                allItem.setVisitCount(allItem.getVisitCount() - onItem.getVisitCount());
                allItem.setVisitUsers(allItem.getVisitUsers() - onItem.getVisitUsers());
                result.add(allItem);
                i++;
                j++;
            } else {
                onItem.setVisitCount(-onItem.getVisitCount());
                onItem.setVisitUsers(-onItem.getVisitUsers());
                result.add(onItem);
                j++;
            }
        }
        while (i < all.size()) {
            result.add(all.get(i));
            i++;
        }
        while (j < on.size()) {
            GuancetaiEventDetail onItem = on.get(j);
            onItem.setVisitCount(-onItem.getVisitCount());
            onItem.setVisitUsers(-onItem.getVisitUsers());
            result.add(onItem);
            j++;
        }
    }

    protected List<GuancetaiQuotaTrendItem> getStartupUsersInfoTrend(GuancetaiQuotaParam param, String index) {
        if (param.getDeviceStatus() == null || param.getDeviceStatus() == 1) {
            return getStartupUsersInfoTrendInner(param, index);
        }

        param.setDeviceStatus(null);
        List<GuancetaiQuotaTrendItem> all = this.getStartupUsersInfoTrendInner(param, index);
        param.setDeviceStatus(1);
        List<GuancetaiQuotaTrendItem> on = this.getStartupUsersInfoTrendInner(param, index);
        List<GuancetaiQuotaTrendItem> result = new ArrayList<GuancetaiQuotaTrendItem>();
        this.mergetResult(all, on, result);
        return result;
    }

    private List<GuancetaiQuotaTrendItem> getStartupUsersInfoTrendInner(GuancetaiQuotaParam param, String index) {
        TransportClient client = EsInstance.getInstance();
        List<GuancetaiQuotaTrendItem> result = new ArrayList<GuancetaiQuotaTrendItem>();
        SearchRequestBuilder srb = client.prepareSearch(index + dateformat.format(param.getQueryDate()));
        //查询source
        BoolQueryBuilder boolBuilder = getBooleanQueryBuilder(param);


        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("hour").field("hour").size(Integer.MAX_VALUE);
        CardinalityAggregationBuilder cardinalityAggregationBuilder = AggregationBuilders.cardinality("deviceid")
                .field("deviceid").precisionThreshold(PRECISION_THRESHOLD);
        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(boolBuilder))
                .addAggregation(aggregationBuilder.subAggregation(cardinalityAggregationBuilder))
                .execute()
                .actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        if(aggregationMap.get("hour") instanceof InternalMappedTerms) {
            List<InternalTerms.Bucket<LongTerms.Bucket>> buckets = ((InternalMappedTerms) aggregationMap.get("hour")).getBuckets();
            for (Terms.Bucket bucket : buckets) {
                String key = bucket.getKey().toString();
                Map<String, Aggregation> stringAggregationMap = bucket.getAggregations().asMap();
                InternalNumericMetricsAggregation.SingleValue visitCount = (InternalNumericMetricsAggregation.SingleValue) stringAggregationMap.get("deviceid");
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

    /**
     * 递归方式遍历
     *
     * @param aggregationMap
     * @param fieldCache
     * @param instance
     * @param result
     * @param statisticsKey
     * @param <T>
     * @throws Exception
     */
    protected <T> void helper(Map<String, Aggregation> aggregationMap,
                              Map<String, Field> fieldCache,
                              T instance,
                              List<T> result,
                              String statisticsKey) throws Exception {
        Terms statisticsTerms = null;
        for (Map.Entry<String, Aggregation> entry : aggregationMap.entrySet()) {
            String key = entry.getKey();
            Aggregation aggregation = entry.getValue();
            Field field = fieldCache.get(key);
            if (aggregation instanceof Terms) {
                Terms stringTerms = ((Terms) aggregation);
                if (stringTerms.getName().equals(statisticsKey)) {
                    statisticsTerms = stringTerms;
                    continue;
                }
                for (Terms.Bucket bucket : stringTerms.getBuckets()) {
                    String bucketVal = bucket.getKeyAsString();
                    if (field != null && bucket != null) {
                        setValue(bucketVal, field, instance);
                    }
                    helper(bucket.getAggregations().asMap(), fieldCache, instance, result, statisticsKey);
                }
            } else if (aggregation instanceof InternalNumericMetricsAggregation.SingleValue) {
                InternalNumericMetricsAggregation.SingleValue internalSum = ((InternalNumericMetricsAggregation.SingleValue) aggregation);
                Double value = internalSum.value();
                if (value != null && field != null) {
                    setValue(value.longValue(), field, instance);
                }
            }
        }

        if (statisticsTerms != null) {
            Field field = fieldCache.get(statisticsTerms.getName());
            for (Terms.Bucket bucket : statisticsTerms.getBuckets()) {
                String bucketVal = bucket.getKeyAsString();
                if (field != null && bucket != null) {
                    setValue(bucketVal, field, instance);
                }
                helper(bucket.getAggregations().asMap(), fieldCache, instance, result, statisticsKey);
                result.add((T) BeanMapper.map(instance, instance.getClass()));
            }
        }

    }

    /**
     * 迭代方式遍历
     *
     * @param aggregationMap
     * @param fieldCache
     * @param instance
     * @param result
     * @param statisticsKey
     * @param <T>
     * @throws Exception
     */
    protected <T> void helper2(Map<String, Aggregation> aggregationMap,
                               Map<String, Field> fieldCache,
                               T instance,
                               List<T> result,
                               String statisticsKey) throws Exception {
        Stack<MemoryedBucket> stack = new Stack<MemoryedBucket>();
        for (Map.Entry<String, Aggregation> entry : aggregationMap.entrySet()) {
            Terms stringTerms = ((Terms) entry.getValue());
            pushStack(statisticsKey, stack, stringTerms);
        }

        while(!stack.isEmpty()){
            MemoryedBucket top = stack.pop();
            if(top instanceof  FlagAggregation){
                result.add((T) BeanMapper.map(instance, instance.getClass()));
                continue;
            }
            Terms.Bucket bucket = top.getBucket();
            List<Aggregation> aggregations = bucket.getAggregations().asList();
            Field field = fieldCache.get(top.getAggregationName());
            String bucketVal = bucket.getKeyAsString();
            if(field != null){
                setValue(bucketVal, field, instance);
            }
            for (Aggregation aggregation : aggregations) {
                if(aggregation instanceof Terms){
                    Terms terms = (Terms) aggregation;
                    pushStack(statisticsKey, stack, terms);
                }else if (aggregation instanceof InternalNumericMetricsAggregation.SingleValue) {
                    InternalNumericMetricsAggregation.SingleValue internalSum = ((InternalNumericMetricsAggregation.SingleValue) aggregation);
                    Double value = internalSum.value();
                    field = fieldCache.get(internalSum.getName());
                    if (value != null && field != null) {
                        setValue(value.longValue(), field, instance);
                    }
                }
            }
        }

    }

    private void pushStack(String statisticsKey, Stack<MemoryedBucket> stack, Terms stringTerms) {
        for (Terms.Bucket bucket : stringTerms.getBuckets()) {
            if(stringTerms.getName().equals(statisticsKey)){
                FlagAggregation flagAggregation = new FlagAggregation();
                flagAggregation.setValue(bucket.getKeyAsString());
                stack.push(flagAggregation);
            }
            MemoryedBucket memoryedBucket = new MemoryedBucket();
            memoryedBucket.setAggregationName(stringTerms.getName());
            memoryedBucket.setBucket(bucket);
            stack.push(memoryedBucket);
        }
    }

    /**
     * TODO 有bug
     * @param aggregationMap
     * @param fieldCache
     * @param instance
     * @param result
     * @param statisticsKey
     * @param <T>
     * @throws Exception
     */
    protected <T> void helper3(Map<String, Aggregation> aggregationMap,
                               Map<String, Field> fieldCache,
                               T instance,
                               List<T> result,
                               String statisticsKey) throws Exception {
        Stack<Aggregation> stack = new Stack<Aggregation>();
        if (aggregationMap.containsKey(statisticsKey)) {
            stack.push(aggregationMap.get(statisticsKey));
        }
        for (Aggregation aggregation : aggregationMap.values()) {
            if (!aggregation.getName().equals(statisticsKey)) {
                stack.push(aggregation);
            }
        }
        while (!stack.isEmpty()) {
            Aggregation aggregation = stack.pop();
            Field field = fieldCache.get(aggregation.getName());
            if (aggregation instanceof Terms) {
                Terms stringTerms = ((Terms) aggregation);
                for (Terms.Bucket bucket : stringTerms.getBuckets()) {
                    String bucketVal = bucket.getKeyAsString();

                    if (stringTerms.getName().equals(statisticsKey)) {
                        FlagAggregation flagAggregation = new FlagAggregation();
                        flagAggregation.setValue(bucketVal);
                        //todo flagAggregation类型改变
                        //stack.push(flagAggregation);
                    } else if (field != null && bucket != null) {
                        setValue(bucketVal, field, instance);
                    }
                    Map<String, Aggregation> bucketsMap = bucket.getAggregations().asMap();
                    if (bucketsMap.containsKey(statisticsKey)) {
                        stack.push(bucketsMap.get(statisticsKey));
                    }
                    for (Aggregation item : bucketsMap.values()) {
                        if (!item.getName().equals(statisticsKey)) {
                            stack.push(item);
                        }
                    }

                }
            } else if (aggregation instanceof InternalNumericMetricsAggregation.SingleValue) {
                InternalNumericMetricsAggregation.SingleValue internalSum = ((InternalNumericMetricsAggregation.SingleValue) aggregation);
                Double value = internalSum.value();
                if (value != null && field != null) {
                    setValue(value.longValue(), field, instance);
                }
            } else if (aggregation instanceof FlagAggregation) {
                field = fieldCache.get(statisticsKey);
                if (field != null) {
                    field.set(instance, ((FlagAggregation) aggregation).getValue());
                }
                result.add((T) BeanMapper.map(instance, instance.getClass()));
            }

        }

    }

    protected static void setValue(Object value, Field field, Object ref) throws IllegalAccessException {
        Class<?> type = field.getType();
        if (type == String.class) {
            field.set(ref, value.toString());
        } else if (type == long.class || type == Long.class) {
            if (value instanceof Long) {
                field.set(ref, value);
            } else {
                field.set(ref, Long.valueOf(value.toString()));
            }
        } else if (type == double.class || type == Double.class) {
            if (value instanceof Double) {
                field.set(ref, value);
            } else {
                field.set(ref, Double.valueOf(value.toString()));
            }
        } else if (type == Integer.class || type == int.class) {
            field.set(ref, Integer.valueOf(value.toString()));
        } else {
            field.set(ref, value);
        }
    }

    private class MemoryedBucket {
        private String aggregationName;
        private Terms.Bucket bucket;

        public String getAggregationName() {
            return aggregationName;
        }

        public void setAggregationName(String aggregationName) {
            this.aggregationName = aggregationName;
        }

        public Terms.Bucket getBucket() {
            return bucket;
        }

        public void setBucket(Terms.Bucket bucket) {
            this.bucket = bucket;
        }
    }

    private class FlagAggregation<T> extends MemoryedBucket {

        private T value;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    public static <T> void getDeclaredFields(Class<T> clazz, Map<String, Field> fieldMap){
        Class<? super T> superclass = clazz.getSuperclass();
        if(superclass != null){
            getDeclaredFields(superclass, fieldMap);
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            fieldMap.put(field.getName(), field);
        }
    }

    public static <T> void getDeclaredFieldsIterator(Class<T> clazz, Map<String, Field> fieldMap){
        Stack<Class> stack = new Stack<Class>();
        stack.push(clazz);
        Class superClazz = clazz.getSuperclass();
        while (superClazz != null){
            stack.push(superClazz);
            superClazz = superClazz.getSuperclass();
        }
        while (!stack.isEmpty()){
            Class pop = stack.pop();
            Field[] fields = pop.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                fieldMap.put(field.getName(), field);
            }
        }
    }
}
