package com.qding.bigdata.ds.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.qding.bigdata.ds.VO.SelectVO;
import com.qding.bigdata.ds.VO.UserPortraitDetailVO;
import com.qding.bigdata.ds.annotation.DynamicDataSource;
import com.qding.bigdata.ds.aop.dynamicsource.DataSourceContextHolder;
import com.qding.bigdata.ds.cache.RedisCache;
import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.dao.DIMCategoryDao;
import com.qding.bigdata.ds.dao.DIMLiveClassifyDao;
import com.qding.bigdata.ds.dao.DIMSKUDao;
import com.qding.bigdata.ds.dao.LabelPreferenceResultV2Dao;
import com.qding.bigdata.ds.enums.UserActionEnum;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.service.*;
import com.qding.bigdata.ds.util.DateUtil;
import com.qding.bigdata.ds.util.EsInstance;
import com.qding.bigdata.ds.util.PropertiesUtil;
import com.qding.bigdata.ds.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.InternalNumericMetricsAggregation;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.InternalValueCount;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserProfileV2ServiceImpl implements UserProfileV2Service {

    private static final String USER_PROFILE_ES_INDEX = "qding_user_label";
    private static final String USER_PROFILE_PREFERENCE_ES_INDEX = "qding_user_label_row";

	public static Logger logger = LoggerFactory.getLogger(UserProfileV2ServiceImpl.class);

	@Autowired
	private PortraitTagService portraitTagService;

	@Autowired
	private CorpusKeywordsService  corpusKeywordsService;

	@Autowired
	private UserGroupService userGroupService;
	private DIMSKUDao dimskuDao;
	private DIMLiveClassifyDao dimLiveClassifyDao;
	private DIMCategoryDao dimCategoryDao;
	private LabelPreferenceResultV2Dao labelPreferenceResultV2Dao;
	@Resource(name = "dynamicSqlSession")
	public void initDao(SqlSession sqlSession) {
		DataSourceContextHolder.setDataSource("databus");
		this.labelPreferenceResultV2Dao = sqlSession.getMapper(LabelPreferenceResultV2Dao.class);
		this.dimCategoryDao = sqlSession.getMapper(DIMCategoryDao.class);
		this.dimLiveClassifyDao = sqlSession.getMapper(DIMLiveClassifyDao.class);
		this.dimskuDao = sqlSession.getMapper(DIMSKUDao.class);
		DataSourceContextHolder.clearDataSource();
	}


	@Override
	public Map<String, Object> query(JSONObject queryJson) {
		Map<String, Object> map = new HashMap<String, Object>();
		TransportClient client = EsInstance.getInstance();
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

		//1、构建查询条件
		JSONArray conditionsArr = queryJson.getJSONArray("conditions");
		buildQuery(boolQuery,conditionsArr);

		SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices(USER_PROFILE_ES_INDEX).setTypes("users").setQuery(boolQuery).setSize(0);
		//2、构建聚合字段
		JSONArray aggArr = queryJson.getJSONArray("agg");

		if(null != aggArr){
			buildAggregations(searchRequestBuilder, aggArr);
		}
		//3、获取组装结果
		getResults(map, searchRequestBuilder, aggArr);
		return map;
	}

    /**
     * 数据概览-全部用户
     * @return
     */
	@Override
//    @Cacheable(value = "redisCache",key = "'userPortrait:allUserOverview'")
	@DynamicDataSource("databus")
	public Map<String, Object> queryAllUserOverview(JSONArray conditionsArr) {
        return getAllUser(conditionsArr);
    }

    @Override
	@DynamicDataSource("databus")
    public Map<String, Object> queryCUserOverview(JSONArray conditionsArr) {
	    return getCUser(conditionsArr);
    }

    @Override
    public Map<String, Object> queryPUserOverview(JSONArray conditionsArr) {
        return getPUser(conditionsArr);
    }

    public List<StringTerms.Bucket>  getBucketByOneAgg(SearchRequestBuilder srb,String field,JSONArray conditionsArr){
        TermsAggregationBuilder agg = AggregationBuilders.terms("agg_"+field).field(field).size(Integer.MAX_VALUE);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        buildQuery(boolQuery,conditionsArr);
        SearchResponse sr = srb.setQuery(boolQuery).addAggregation(agg)
                .execute().actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        StringTerms stringTerms = (StringTerms) aggregationMap.get("agg_"+field);
        if(stringTerms.getBuckets() != null){
            return stringTerms.getBuckets();
        }else{
            return null;
        }
    }

	public List<StringTerms.Bucket>  getBucketByOneAgg(SearchRequestBuilder srb,String field,JSONArray conditionsArr,BoolQueryBuilder boolQuery){
		TermsAggregationBuilder agg = AggregationBuilders.terms("agg_"+field).field(field).size(Integer.MAX_VALUE);
		buildQuery(boolQuery,conditionsArr);
		SearchResponse sr = srb.setQuery(boolQuery).addAggregation(agg)
				.execute().actionGet();
		Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
		StringTerms stringTerms = (StringTerms) aggregationMap.get("agg_"+field);
		if(stringTerms.getBuckets() != null){
			return stringTerms.getBuckets();
		}else{
			return null;
		}
	}

	public List<? extends Bucket> getBucketByOneAgg2(SearchRequestBuilder srb, String field, JSONArray conditionsArr, BoolQueryBuilder boolQuery){
		TermsAggregationBuilder agg = AggregationBuilders.terms("agg_"+field).field(field).size(Integer.MAX_VALUE);
		buildQuery(boolQuery,conditionsArr);
		SearchResponse sr = srb.setQuery(boolQuery).addAggregation(agg)
				.execute().actionGet();
		Terms  aggTerms = sr.getAggregations().get("agg_"+field);
		List<? extends Bucket> buckets = aggTerms.getBuckets();
		return buckets;
	}

    /**
     * 递归的解析聚合后的数据
     * @param aggregationMap
     * @throws IllegalAccessException
     */
    public static void helper(Map<String, Aggregation> aggregationMap, Map<String, Object> restMap,List<String> aggNames) throws IllegalAccessException {

        if (CollectionUtils.isEmpty(aggregationMap)) {
            return ;
        }
        Set<Map.Entry<String, Aggregation>> entrySet = aggregationMap.entrySet();
        boolean hasBucket = false;
        for (Map.Entry<String, Aggregation> entry : entrySet) {
            String key = entry.getKey();
            //只取出自己需要的aggregation
            if( aggNames.contains(entry.getKey())){
                Aggregation aggregation = entry.getValue();
                if (aggregation instanceof StringTerms) {
                    hasBucket = true;
                    StringTerms stringTerms = ((StringTerms) aggregation);
                    for (Terms.Bucket bucket : stringTerms.getBuckets()) {
                        if ( bucket != null) {
                            String bucketVal = bucket.getKeyAsString();
                            if(bucket.getAggregations().asList().size() == 0){
                                restMap.put(bucketVal,bucket.getDocCount());
                            }else{
                                Map<String, Object> o = new HashMap<>();
                                restMap.put(bucket.getKeyAsString(),o);
                                helper(bucket.getAggregations().asMap(), (Map<String, Object>) restMap.get(bucket.getKeyAsString()),aggNames);
                            }

                        }

                    }
                } else if (aggregation instanceof InternalNumericMetricsAggregation.SingleValue) {
                    InternalNumericMetricsAggregation.SingleValue internalSum = ((InternalNumericMetricsAggregation.SingleValue) aggregation);
                    Double value = internalSum.value();
                    if (value != null ) {
                        System.out.println(value);
                    }
                }
            }
        }
        if (!hasBucket) {
            return;
        }
    }

	@Override
	public Map<String, Object> queryForSum(JSONObject queryJson) {
		Map<String, Object> map = new HashMap<String, Object>();
		TransportClient client = EsInstance.getInstance();
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		boolQuery.must(QueryBuilders.existsQuery("mid"));

		//1、构建查询条件
		JSONArray conditionsArr = queryJson.getJSONArray("conditions");

		//把聚合字段加入到查询条件，sum求和时，需要把小于0的数据过滤掉
		JSONArray aggArr = queryJson.getJSONArray("agg");
		/*for (Object aggObj : aggArr) {
			String tag = (String) aggObj;
			conditionsArr.add(JSON.parse("{\"tag\":\""+tag+"\",\"rule\":\"gt\",\"value1\":\"0\"}"));
		}*/

		buildQuery(boolQuery,conditionsArr);

		SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices(USER_PROFILE_ES_INDEX).setTypes("userprofile")
				.setQuery(boolQuery).setSize(0);

		//2、构建聚合字段
		buildAggregationsForSum(searchRequestBuilder, aggArr);

		//3、获取组装结果
		getResultsForSum(map, searchRequestBuilder, aggArr);
		return map;
	}

	/**
	 * 获取组装结果
	 * @param map
	 * @param searchRequestBuilder
	 * @param aggArr
	 */
	public void getResultsForSum(Map<String, Object> map,
			SearchRequestBuilder searchRequestBuilder, JSONArray aggArr) {
		SearchResponse sr = searchRequestBuilder.get();
		System.out.println(searchRequestBuilder.toString());
		map.put("totalHit", sr.getHits().getTotalHits());
		List<LinkedHashMap<String, Object>> aggList = new ArrayList<LinkedHashMap<String, Object>>();
		for (Object aggObj : aggArr) {
			LinkedHashMap<String, Object> aggMap = new LinkedHashMap<String, Object>();
			String tag = (String) aggObj;
			PortraitTag thisTag = portraitTagService.getByTagName(tag);
			aggMap.put("tag", tag);
			aggMap.put("name", thisTag.getName());

			Sum agg = sr.getAggregations().get("agg_" + tag);
			double value = agg.getValue();
			aggMap.put("data", value);
			aggList.add(aggMap);
		}
		map.put("aggregations", aggList);
	}

	/**
	 * 获取组装结果
	 * @param map
	 * @param searchRequestBuilder
	 * @param aggArr
	 */
	private void getResults(Map<String, Object> map,SearchRequestBuilder searchRequestBuilder, JSONArray aggArr) {
		SearchResponse sr = searchRequestBuilder.get();
		System.out.println(searchRequestBuilder.toString());
		map.put("totalHit", sr.getHits().getTotalHits());
		List<LinkedHashMap<String, Object>> aggList = new ArrayList<LinkedHashMap<String, Object>>();
		if(null!= aggArr){
			for (Object aggObj : aggArr) {
				LinkedHashMap<String, Object> aggMap = new LinkedHashMap<String, Object>();
				String tag = (String) aggObj;

					PortraitTag thisTag = portraitTagService.getByTagName(tag);
					aggMap.put("tag", tag);
					aggMap.put("name", thisTag.getName());
					LinkedHashMap<Object, Long> values = new LinkedHashMap<Object, Long>();
					Terms aggregation = sr.getAggregations().get("agg_" + tag);

					for (Bucket bucket : aggregation.getBuckets()) {
						if(bucket.getDocCount() > 0) {
							String key = "";
							if(tag.contains("_time")||tag.contains("_date")){
								key =Long.valueOf(bucket.getKey().toString())!=-1?
										new DateTime(Long.parseLong(bucket.getKey()+"")-28800000).toString("yyyy-MM-dd HH:mm:ss"):
										StringUtil.toString(bucket.getKey());
							}else{
								key=StringUtil.toString(bucket.getKey());
							}
                            values.put(key, bucket.getDocCount());
                        }
                    }
					aggMap.put("data", values);
					aggList.add(aggMap);
			}
		}
		map.put("aggregations", aggList);
	}

	private void kCountByMap(Map<String,BigDecimal> retmap,Map<String,BigDecimal> v,Map<String,Integer> retXlsNumMap){
	    for(String key : v.keySet()){
            if(retmap.containsKey(key)){
               try {
                   retmap.put(key,retmap.get(key).add(v.get(key)));
				   retXlsNumMap.put(key,retXlsNumMap.get(key)+1);
               }catch (Exception e){
                   logger.error("v.get(key) = " +v.get(key) + "-----" + v.get(key).getClass().getName());
                   logger.error("z_id = " + v.get("z_id"));
               }
            }else{
                retmap.put(key,v.get(key));
				retXlsNumMap.put(key,1);
            }
        }
    }
    private static List<Map.Entry<String, BigDecimal>>  valueUpSort(Map<String,BigDecimal> map) {
        // 升序比较器
        Comparator<Map.Entry<String, BigDecimal>> valueComparator = Comparator.comparing(Map.Entry::getValue);
//        Comparator<Map.Entry<String, BigDecimal>> valueComparator = (o1, o2)->o1.getValue().compareTo(o2.getValue());

        // map转换成list进行排序
        List<Map.Entry<String, BigDecimal>> list = new ArrayList<>(map.entrySet());

        // 排序
        list.sort(valueComparator.reversed());
        return list;
    }
	/**
	 * 构建聚合字段
	 * @param searchRequestBuilder
	 * @param aggArr
	 */
	private void buildAggregations(SearchRequestBuilder searchRequestBuilder,JSONArray aggArr) {
		for (Object aggObj : aggArr) {
			String tag = (String) aggObj;
			String aggName = "agg_" + tag;
			//普通条件聚合
			searchRequestBuilder.addAggregation(AggregationBuilders.terms(aggName).field(tag).size(100));
		}
	}


	/**
	 * 构建聚合字段
	 * @param searchRequestBuilder
	 * @param aggArr
	 */
	public void buildAggregationsForSum(SearchRequestBuilder searchRequestBuilder,JSONArray aggArr) {
		for (Object aggObj : aggArr) {
			String tag = (String) aggObj;
			String aggName = "agg_" + tag;
			if(!tag.startsWith("interesting")){
				searchRequestBuilder.addAggregation(AggregationBuilders.sum(aggName).field(tag));
			}
		}
	}

	/**
	 * 判断用户兴趣是否参与聚合：
	 *    true 参与聚合
	 *    false 参与聚合
	 * @param aggArr
	 * @param interestingType
	 * @return
	 */
	private boolean getInterestingAggFlag(JSONArray aggArr,
			String interestingType) {
		boolean interestingAggFlag = false;
		if(aggArr.contains(interestingType)){
			interestingAggFlag = true;
			aggArr.remove(interestingType);
		}
		return interestingAggFlag;
	}


	/**
	 * 构建查询条件
	 * @param boolQuery
	 * @param conditionsArr
	 */
	public void buildQuery(BoolQueryBuilder boolQuery, JSONArray conditionsArr) {
	    if(null == conditionsArr){
	        return;
        }
		for (Object conditionObj : conditionsArr) {
			JSONObject condition = (JSONObject) conditionObj;
			String tag = condition.getString("tag");
			String rule = condition.getString("rule");
			String value1 = condition.getString("value1");
			String value2 = condition.getString("value2");
			if ("eq".equals(rule)) {
				boolQuery.must(QueryBuilders.termsQuery(tag, value1.split(",")));
			}
			if ("neq".equals(rule)) {
				boolQuery.mustNot(QueryBuilders.termsQuery(tag, value1.split(",")));
			}
			if ("gt".equals(rule)) {
				boolQuery.must(QueryBuilders.rangeQuery(tag).gt(value1));
			}
			if ("lt".equals(rule)) {
				boolQuery.must(QueryBuilders.rangeQuery(tag).lt(value1));
			}
			if ("ge".equals(rule)) {
				boolQuery.must(QueryBuilders.rangeQuery(tag).gte(value1));
			}
			if ("le".equals(rule)) {
				boolQuery.must(QueryBuilders.rangeQuery(tag).lte(value1));
			}
			if ("bt".equals(rule)) {
				boolQuery.must(QueryBuilders.rangeQuery(tag).from(value1, true).to(value2, true));
			}
			//包含
			if ("in".equals(rule)) {
				BoolQueryBuilder containQuery = QueryBuilders.boolQuery();
				String[] splited = value1.split(",");
				for (String split:splited) {
					containQuery.should(QueryBuilders.termQuery(tag,split));
				}
				boolQuery.must(containQuery);
			}
			//不包含
			if ("ex".equals(rule)) {
				BoolQueryBuilder nocontainQuery = QueryBuilders.boolQuery();
				String[] splited = value1.split(",");
				for (String split:splited) {
					nocontainQuery.mustNot(QueryBuilders.termQuery(tag,split));
				}
                nocontainQuery.mustNot(QueryBuilders.termQuery(tag,-1));
                boolQuery.must(nocontainQuery);
			}
			//模糊匹配
			if ("wildcard".equals(rule)) {
				BoolQueryBuilder wildcardQuery = QueryBuilders.boolQuery();
				String[] splited = value1.split(",");
				for (String split:splited) {
					wildcardQuery.should(QueryBuilders.wildcardQuery(tag,"*"+ split +"*"));
				}
				boolQuery.must(wildcardQuery);
			}
			//不包含模糊匹配
			if ("exWildcard".equals(rule)) {
				BoolQueryBuilder wildcardQuery = QueryBuilders.boolQuery();
				String[] splited = value1.split(",");
				for (String split:splited) {
					wildcardQuery.mustNot(QueryBuilders.wildcardQuery(tag,"*"+ split +"*"));
				}
                wildcardQuery.mustNot(QueryBuilders.termQuery(tag,-1));
				boolQuery.must(wildcardQuery);
			}
		}
	}

	/**
	 * 获取感兴趣词结果集合
	 * @param idxKeyWordMap
	 * @param sr
	 * @param interestingValueMap
	 * @param tag
	 * @param intestringType
	 */
	private void getInterestingValueMap(Map<Long, String> idxKeyWordMap,
			SearchResponse sr,
			LinkedHashMap<Object, Long> interestingValueMap, String tag,String intestringType) {
		String idx = tag.replace(intestringType+".k","");
		InternalValueCount count = sr.getAggregations().get("agg_" + tag);
		if(null != idxKeyWordMap.get(Long.parseLong(idx)) && count.getValue()>0 ){
			interestingValueMap.put(idxKeyWordMap.get(Long.parseLong(idx)),count.getValue());
		}
	}

	/**
	 * 往aggList中添加感兴趣词结果集合
	 * @param aggList
	 * @param interestingValueMap   感兴趣词结果集合
	 * @param intestringType  感兴趣词类型：interesting_buy-购买兴趣；interesting_read-阅读兴趣
	 */
	private void addEleAggList(List<LinkedHashMap<String, Object>> aggList,
			LinkedHashMap<Object, Long> interestingValueMap,String intestringType) {
		LinkedHashMap<String, Object> aggMap = new LinkedHashMap<String, Object>();
		PortraitTag thisTag = portraitTagService.getByTagName(intestringType);
		aggMap.put("tag", thisTag.getTag());
		aggMap.put("name", thisTag.getName());
		aggMap.put("data", sortByValue(interestingValueMap));
		aggList.add(aggMap);
	}

	/**
	 * 拼装感兴趣词查询条件
	 * @param boolQuery
	 * @param idxKeyWordMap
	 * @param tag  兴趣类型
	 * @param rule  eq-精确查找； like-模糊查找
	 * @param value1 关键词
	 */
	private void buildInsertingQuery(BoolQueryBuilder boolQuery,
			Map<Long, String> idxKeyWordMap, String tag, String rule,
			String value1) {
		//根据关键词查idx
		CorpusKeywords corpusKeywordsParam = new  CorpusKeywords();
		corpusKeywordsParam.setSearchType(rule);
		corpusKeywordsParam.setKw(value1);
		List<CorpusKeywords> list = corpusKeywordsService.list(corpusKeywordsParam);
		if(null != list && list.size()>0){
			for (CorpusKeywords corpusKeywords : list) {
				idxKeyWordMap.put(corpusKeywords.getIdx(),corpusKeywords.getKw());
			}
		}

		//拼装条件
		BoolQueryBuilder keyWordQuery = new BoolQueryBuilder();
		if(null!=idxKeyWordMap && idxKeyWordMap.size()>0){
			for (Long idx : idxKeyWordMap.keySet()) {
				if(idxKeyWordMap.size()==1){
					keyWordQuery.must(QueryBuilders.rangeQuery(tag+".k"+idx).gt(0.0));
				}else{
					keyWordQuery.should(QueryBuilders.rangeQuery(tag+".k"+idx).gt(0.0));

				}
			}
		}
		//搜索关键词不存在时，默认条件是查询不到数据
		else{
			keyWordQuery.should(QueryBuilders.rangeQuery(tag+".k00000").gt(0));
		}
		boolQuery.must(keyWordQuery);
	}

	/**
   	 * map排序
   	 * @param map
   	 * @return
   	 */
	 public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	       List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
	       Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
	           @Override
	           public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
	               return (o2.getValue()).compareTo(o1.getValue());
	           }
	       });
	       Map<K, V> result = new LinkedHashMap<K, V>();
	       for (Map.Entry<K, V> entry : list) {
	           result.put(entry.getKey(), entry.getValue());
	       }
	       return result;
	 }


	/**
	 * 根据群组ID导出用户列表
	 * @param userGroupId
	 * @return
	 * @see
	 *   1、根据用户群组ID查询条件
	 *   2、解析条件，组装ES查询条件
	 *   3、导出用户列表，生成文件
	 *   4、返回文件名称和路径
	 */
	public Object exportUsers(String userGroupId) {
		long beginTime = System.currentTimeMillis();

		Map<String,Object> rsMap = new HashMap<String, Object>();

		try {
			String filePath = PropertiesUtil.getPropertiesByKey(
					Constant.CONFIGFILE, "export.user.path")
					+ File.separator
					+ userGroupId + File.separator;
			String fileName = filePath + "export_user_" + userGroupId+".txt";
			logger.info("origin fileName:"+fileName);
			//检查文件是否存在，存在则重命名
			File oldFile = new File(fileName);
			if (oldFile.exists()) {
				String newFileName = "export_user_"
						+ userGroupId+"_bak_"
						+ DateUtil.formatDateToFullString(new Date())+".txt";
				logger.info("origin fileName:["+oldFile.getName()+ "]  exists,rename to [" + newFileName+"]");
				oldFile.renameTo(new File(filePath + newFileName));
			}else{
				File parentPath=new File(oldFile.getParent());
				parentPath.mkdirs();
			}
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName,
					true));
			PortraitUserGroup userGroupParam = new PortraitUserGroup();
			userGroupParam.setId(userGroupId);
			PortraitUserGroup userGroup = userGroupService.getOne(userGroupParam);
			if (null != userGroup) {
				JSONObject queryJson = JSONObject.parseObject(userGroup
						.getConditions());

				JSONArray conditions = (JSONArray) queryJson.get("conditions");
				if(conditions!=null && conditions.size()!=0){
					getUserDetailsByScorll(conditions,100000,5000, out);
				}
				logger.info("查询结束");
				long endTime = System.currentTimeMillis();

				//权限控制
				Runtime.getRuntime().exec("chmod -R 775 "+new File(fileName).getParent());

				rsMap.put("fileName", fileName);
				rsMap.put("usetime", (endTime - beginTime)/1000);
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsMap;
	}

    @Override
	@DynamicDataSource("databus")
        public Map<String,Object> queryPreferences(JSONArray conditionsArr) {
        Map<String, Object> map = new LinkedHashMap<>();
        TransportClient client = EsInstance.getInstance();
        long startTime = System.currentTimeMillis();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        buildQuery(boolQuery,conditionsArr);

        SearchResponse scrollResp = client.prepareSearch(USER_PROFILE_ES_INDEX).setTypes("users")
                .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(600000))
                .setQuery(boolQuery)
                .setSize(5000).get(); //max of 100 hits will be returned for each scroll Scroll until no hits are returned
        Map<String,BigDecimal> retXlsMap = new HashMap<>();
        Map<String,Integer> retXlsNumMap = new HashMap<>();
        Map<String,BigDecimal> retServiceMap = new HashMap<>();
        Map<String,Integer> retServiceNumMap = new HashMap<>();
        do {

            for (SearchHit hit : scrollResp.getHits().getHits()) {
                JSONObject json = JSONObject.parseObject(hit.getSourceAsString());
                //新零售购买偏好
                Object xls_preference = json.get("xls_preference");
                if(!"-1".equals(xls_preference.toString())){
                    Map<String, BigDecimal> xlsMap = JSON.parseObject(xls_preference.toString(), new TypeReference<Map<String, BigDecimal>>() {});
                    kCountByMap(retXlsMap,xlsMap,retXlsNumMap);
                }
                //服务购买偏好
                Object service_preference = json.get("service_preference");
                if(!"-1".equals(service_preference.toString())){
                    Map<String, BigDecimal> serviceMap = JSON.parseObject(service_preference.toString(), new TypeReference<Map<String, BigDecimal>>() {});
                    kCountByMap(retServiceMap,serviceMap,retServiceNumMap);
                }

                //领域偏好
            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(600000)).execute().actionGet();
        } while(scrollResp.getHits().getHits().length != 0); // Zero hits mark the end of the scroll and the while loop.

        List<Map.Entry<String, BigDecimal>> retXlsList = valueUpSort(retXlsMap);
        List<Map.Entry<String, BigDecimal>> retServiceList = valueUpSort(retServiceMap);

        if(retXlsList.size()>20){
            retXlsList = retXlsList.subList(0,20);
        }
        if(retServiceList.size()>15){
            retServiceList = retServiceList.subList(0,15);
        }
		//取出ID对应的Name
        Map<String, Integer> xlsMap = mapEntryListToMap(retXlsList, retXlsNumMap);
        Map<String, Integer> serviceMap = mapEntryListToMap(retServiceList, retServiceNumMap);
		Set<String> strings = xlsMap.keySet();
		Set<String> strings2 = serviceMap.keySet();
		List<DIMSKU> dimSKUs = null;
        List<DIMCategory> dimCategories = null;
        Map<Long, DIMSKU> dimSKUMap = null;
        Map<Long, DIMCategory> dimCategorieMap = null;
        if(strings.size() > 0){
            dimSKUs = dimskuDao.listByIDs(StringUtils.join(strings.toArray(),","));
            dimSKUMap = dimSKUs.stream().collect(Collectors.toMap(DIMSKU::getId, a -> a, (k1, k2) -> k1));
        }
        if(strings2.size() > 0){
            dimCategories = dimCategoryDao.listByIDs(StringUtils.join(strings2.toArray(),","));
            dimCategorieMap = dimCategories.stream().collect(Collectors.toMap(DIMCategory::getId, a -> a, (k1, k2) -> k1));
        }

		List<String> retXls = new ArrayList<>();
		List<String> retService = new ArrayList<>();
		for(Map.Entry<String, Integer> entry : xlsMap.entrySet()){
			if(dimSKUMap.containsKey(Long.parseLong(entry.getKey()))){
                DIMSKU dimsku = dimSKUMap.get(Long.valueOf(entry.getKey()));
                //过滤测试数据
                if("1000324".equals(dimsku.getProviderId().toString())){
                    continue;
                }
                retXls.add(xlsMap.get(entry.getKey())+"|"+dimsku.getWareName());
			}
		}
		for(Map.Entry<String, Integer> entry : serviceMap.entrySet()){
			if(dimCategorieMap.containsKey(Long.parseLong(entry.getKey()))){
				retService.add(serviceMap.get(entry.getKey())+"|"+dimCategorieMap.get(Long.valueOf(entry.getKey())).getName());
			}
		}
		map.put("retXls",retXls);
        map.put("retService",retService);
        long endTime = System.currentTimeMillis();
        System.out.println("time = "+ (endTime - startTime));
        return map;
    }

	@Override
	@DynamicDataSource("databus")
	public Map<String,Object> queryPreferences2(JSONArray conditionsArr) {
		Map<String, Object> map = new LinkedHashMap<>();
		TransportClient client = EsInstance.getInstance();
		long startTime = System.currentTimeMillis();
		//1.查询偏好打分前15的新零售偏好ID
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		buildQuery(boolQuery,conditionsArr);
		//不包含preference_id为空字符串
		BoolQueryBuilder nocontainQuery = QueryBuilders.boolQuery();
		nocontainQuery.mustNot(QueryBuilders.termQuery("preference_id",""));
		boolQuery.must(nocontainQuery);
		boolQuery.must(QueryBuilders.termsQuery("preference_type", "xls_preference"));

		SearchRequestBuilder scrollResp = client.prepareSearch(USER_PROFILE_PREFERENCE_ES_INDEX).setTypes("users");
		TermsAggregationBuilder tb = AggregationBuilders.terms("agg_preference_id").field("preference_id").order(BucketOrder.compound(
				BucketOrder.aggregation("sum_preference_score", false)
		)).size(Integer.MAX_VALUE);
		SumAggregationBuilder sumAggregationBuilder = AggregationBuilders.sum("sum_preference_score").field("preference_score");
		tb.subAggregation(sumAggregationBuilder);

		SearchResponse sr =  scrollResp.setQuery(boolQuery).addAggregation(tb).execute().actionGet();
		Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
		StringTerms stringTerms = (StringTerms) aggregationMap.get("agg_preference_id");
		List<StringTerms.Bucket> pBuckets = null;
		if(stringTerms.getBuckets() != null){
			pBuckets =  stringTerms.getBuckets();
		}
		List<String> idsList = new ArrayList<>();
		for(Bucket bucket : pBuckets){
			if(idsList.size()>14){
				break;
			}
			String key = bucket.getKeyAsString();
			idsList.add(key);
		}

		//查询偏好中文名称
		List<DIMSKU> dimSKUs = null;
		Map<Long, DIMSKU> dimSKUMap = null;
		if(idsList.size() > 0){
			dimSKUs = dimskuDao.listByIDs(StringUtils.join(idsList.toArray(),","));
			dimSKUMap = dimSKUs.stream().collect(Collectors.toMap(DIMSKU::getId, a -> a, (k1, k2) -> k1));
		}

		//2.查询偏好ID对应的人数
		BoolQueryBuilder boolQuery2 = QueryBuilders.boolQuery();
		buildQuery(boolQuery2,conditionsArr);
		//不包含preference_id为空字符串
		BoolQueryBuilder nocontainQuery2 = QueryBuilders.boolQuery();
		nocontainQuery2.mustNot(QueryBuilders.termQuery("preference_id",""));
		boolQuery2.must(nocontainQuery2);
		boolQuery2.must(QueryBuilders.termsQuery("preference_type", "xls_preference"));
		BoolQueryBuilder containQuery = QueryBuilders.boolQuery();
		for (int i=0;i<idsList.size();i++) {
			containQuery.should(QueryBuilders.termQuery("preference_id",idsList.get(i)));
		}
		boolQuery2.must(containQuery);
		TermsAggregationBuilder tb2 = AggregationBuilders.terms("agg_preference_id2").field("preference_id").size(Integer.MAX_VALUE);
		SearchResponse sr2 =  scrollResp.setQuery(boolQuery2).addAggregation(tb2).execute().actionGet();
		Map<String, Aggregation> aggregationMap2 = sr2.getAggregations().asMap();
		StringTerms stringTerms2 = (StringTerms)aggregationMap2.get("agg_preference_id2");
		List<StringTerms.Bucket> buckets = stringTerms2.getBuckets();
		List<String> retXls = new ArrayList<>();
		for(Bucket bucket : buckets){
			if(dimSKUMap.containsKey(Long.parseLong(bucket.getKeyAsString()))){
				//过滤测试数据
				if("1000324".equals(bucket.getKeyAsString())){
					continue;
				}
				retXls.add(bucket.getDocCount()+"|"+dimSKUMap.get(Long.valueOf(bucket.getKeyAsString())).getWareName());
			}
		}

		//1.查询偏好打分前15的服务偏好ID
		BoolQueryBuilder fxboolQuery = QueryBuilders.boolQuery();
		buildQuery(fxboolQuery,conditionsArr);
		//不包含preference_id为空字符串
		BoolQueryBuilder nocontainQuery3 = QueryBuilders.boolQuery();
		nocontainQuery3.mustNot(QueryBuilders.termQuery("preference_id",""));
		fxboolQuery.must(nocontainQuery3);
		fxboolQuery.must(QueryBuilders.termsQuery("preference_type", "service_preference"));

		SearchRequestBuilder scrollResp3 = client.prepareSearch(USER_PROFILE_PREFERENCE_ES_INDEX).setTypes("users");
		TermsAggregationBuilder tb3 = AggregationBuilders.terms("agg_preference_id").field("preference_id").order(BucketOrder.compound(
				BucketOrder.aggregation("sum_preference_score", false)
		)).size(Integer.MAX_VALUE);
		SumAggregationBuilder sumAggregationBuilder3 = AggregationBuilders.sum("sum_preference_score").field("preference_score");
		tb3.subAggregation(sumAggregationBuilder3);

		SearchResponse sr3 =  scrollResp3.setQuery(fxboolQuery).addAggregation(tb3).execute().actionGet();
		Map<String, Aggregation> aggregationMap3 = sr3.getAggregations().asMap();
		StringTerms stringTerms3 = (StringTerms) aggregationMap3.get("agg_preference_id");
		List<StringTerms.Bucket> pBuckets3 = null;
		if(stringTerms3.getBuckets() != null){
			pBuckets3 =  stringTerms3.getBuckets();
		}
		List<String> idsList3 = new ArrayList<>();
		for(Bucket bucket : pBuckets3){
			if(idsList3.size()>14){
				break;
			}
			String key = bucket.getKeyAsString();
			idsList3.add(key);
		}

		//查询偏好中文名称
		List<DIMSKU> dimSKU2 = null;
		Map<Long, DIMSKU> dimSKUMap2 = null;
		if(idsList3.size() > 0){
			dimSKU2 = dimskuDao.listByIDs(StringUtils.join(idsList3.toArray(),","));
			dimSKUMap2 = dimSKU2.stream().collect(Collectors.toMap(DIMSKU::getId, a -> a, (k1, k2) -> k1));
		}

		//2.查询偏好ID对应的人数
		BoolQueryBuilder boolQuery4 = QueryBuilders.boolQuery();
		buildQuery(boolQuery4,conditionsArr);
		//不包含preference_id为空字符串
		BoolQueryBuilder nocontainQuery4 = QueryBuilders.boolQuery();
		nocontainQuery4.mustNot(QueryBuilders.termQuery("preference_id",""));
		boolQuery4.must(nocontainQuery4);
		boolQuery4.must(QueryBuilders.termsQuery("preference_type", "service_preference"));
		BoolQueryBuilder containQuery4 = QueryBuilders.boolQuery();
		for (int i=0;i<idsList3.size();i++) {
			containQuery4.should(QueryBuilders.termQuery("preference_id",idsList3.get(i)));
		}
		boolQuery4.must(containQuery4);
		TermsAggregationBuilder tb4 = AggregationBuilders.terms("agg_preference_id4").field("preference_id").size(Integer.MAX_VALUE);
		SearchResponse sr4 =  scrollResp3.setQuery(boolQuery4).addAggregation(tb4).execute().actionGet();
		Map<String, Aggregation> aggregationMap4 = sr4.getAggregations().asMap();
		StringTerms stringTerms4 = (StringTerms)aggregationMap4.get("agg_preference_id4");
		List<StringTerms.Bucket> buckets4 = stringTerms4.getBuckets();
		List<String> retService = new ArrayList<>();
		for(Bucket bucket : buckets4){
			if(dimSKUMap2.containsKey(Long.parseLong(bucket.getKeyAsString()))){
				DIMSKU dimsku = dimSKUMap2.get(Long.valueOf(bucket.getKeyAsString()));
				retService.add(bucket.getDocCount()+"|"+dimsku.getWareName()+"("+dimsku.getId()+")");
			}
		}

		map.put("retXls",retXls);
		map.put("retService",retService);
		return map;
	}

    private Map<String, Integer> mapEntryListToMap (List<Map.Entry<String, BigDecimal>> list,Map<String,Integer> retNumMap){
        Map<String, Integer> retMap = new LinkedHashMap<>();
        for(int i=0;i<list.size();i++){
            Map.Entry<String, BigDecimal> v = list.get(i);
            retMap.put(v.getKey(),retNumMap.get(v.getKey()));
        }

		return sortByValue(retMap);
    }

	@Override
	@DynamicDataSource("databus")
	public Map<String, Object> getValueByConditional(JSONArray conditionsArr,String typeVal) {
		Map<String, Object> map = new HashMap<>();
		TransportClient client = EsInstance.getInstance();
		SearchRequestBuilder srb = client.prepareSearch(USER_PROFILE_ES_INDEX).setTypes("users");
		if("crowd".equals(typeVal)){//人群
//    		用户城市分布
			List<StringTerms.Bucket> cityBuckets = getBucketByOneAgg(srb,"city",conditionsArr);
			Map<String, Long> userGroupCity = new HashMap<>();
			if(null!=cityBuckets && !cityBuckets.isEmpty()) {
				for (Bucket bucket : cityBuckets) {
					userGroupCity.put(bucket.getKeyAsString(), bucket.getDocCount());
				}
			}
            //城市补0
            Map<String, Long> cityMap = getCityMap();
            cityMap.putAll(userGroupCity);
			map.put("userGroupCity",cityMap);

//    		用户年龄分布
			List<StringTerms.Bucket> ageBuckets = getBucketByOneAgg(srb,"age",conditionsArr);
			Map<String, Object> userGroupAge = new HashMap<>();
			if(null!=ageBuckets && !ageBuckets.isEmpty()) {
				for (Bucket bucket : ageBuckets) {
					userGroupAge.put(bucket.getKeyAsString(), bucket.getDocCount());
				}
			}
			map.put("userGroupAge",userGroupAge);
//     		社区价值
			List<StringTerms.Bucket> communityvalueBuckets = getBucketByOneAgg(srb,"communityvalue",conditionsArr);
			Map<String, Object> userGroupCommunityvalue = new HashMap<>();
			if(null!=communityvalueBuckets && !communityvalueBuckets.isEmpty()) {
				for (Bucket bucket : communityvalueBuckets) {
					userGroupCommunityvalue.put(bucket.getKeyAsString(), bucket.getDocCount());
				}
			}
			map.put("userGroupCommunityvalue",userGroupCommunityvalue);
//     		社区管理水平
			List<StringTerms.Bucket> managementBuckets = getBucketByOneAgg(srb,"management",conditionsArr);
			Map<String, Object> userGroupManagement = new HashMap<>();
			if(null!=managementBuckets && !managementBuckets.isEmpty()) {
				for (Bucket bucket : managementBuckets) {
					userGroupManagement.put(bucket.getKeyAsString(), bucket.getDocCount());
				}
			}
			map.put("userGroupManagement",userGroupManagement);
		}else if("business".equals(typeVal)){//商业
//     		消费能力
			List<StringTerms.Bucket> consumptionBuckets = getBucketByOneAgg(srb,"consumption",conditionsArr);
			Map<String, Object> userGroupConsumption = new HashMap<>();
			if(null!=consumptionBuckets && !consumptionBuckets.isEmpty()) {
				for (Bucket bucket : consumptionBuckets) {
					userGroupConsumption.put(bucket.getKeyAsString(), bucket.getDocCount());
				}
			}
			map.put("userGroupConsumption",userGroupConsumption);
//      	房屋资产
			List<StringTerms.Bucket> houseassetBuckets = getBucketByOneAgg(srb,"houseasset",conditionsArr);
			Map<String, Object> userGroupHouseasset = new HashMap<>();
			if(null!=houseassetBuckets && !houseassetBuckets.isEmpty()) {
				for (Bucket bucket : houseassetBuckets) {
					userGroupHouseasset.put(bucket.getKeyAsString(), bucket.getDocCount());
				}
			}
			map.put("userGroupHouseasset",userGroupHouseasset);
		}else if("habit".equals(typeVal)){//习惯偏好
            map.put("userGroupPreferences",queryPreferences2(conditionsArr));
		}else if("activeAndTravel".equals(typeVal)){//活跃和出行
//     		 APP活跃频率
			List<StringTerms.Bucket> activeRateBuckets = getBucketByOneAgg(srb,"active_rate",conditionsArr);
			Map<String, Object> userGroupactiveRate = new HashMap<>();
			if(null!=activeRateBuckets && !activeRateBuckets.isEmpty()) {
				for (Bucket bucket : activeRateBuckets) {
					userGroupactiveRate.put(bucket.getKeyAsString(), bucket.getDocCount());
				}
			}
			map.put("userGroupactiveRate",userGroupactiveRate);

//      	APP活跃类型
			List<StringTerms.Bucket> activeTypeBuckets = getBucketByOneAgg(srb,"active_type",conditionsArr);
			Map<String, Object> userGroupActiveType = new HashMap<>();
			if(null!=activeTypeBuckets && !activeTypeBuckets.isEmpty()) {
				for (Bucket bucket : activeTypeBuckets) {
					userGroupActiveType.put(bucket.getKeyAsString(), bucket.getDocCount());
				}
			}
			map.put("userGroupActiveType",userGroupActiveType);

//     		出行规律
			BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
			buildQuery(boolQuery,conditionsArr);
			TermsAggregationBuilder agg_entrance = AggregationBuilders.terms("agg_entrance").field("entrance").size(Integer.MAX_VALUE);
			TermsAggregationBuilder agg_city2 = AggregationBuilders.terms("agg_city2").field("city").size(Integer.MAX_VALUE);
			SearchResponse sr = srb.setQuery(boolQuery).addAggregation(agg_city2.subAggregation(agg_entrance))
					.execute().actionGet();
			Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
			Map<String, Object> userGroupEntrance = new HashMap<>();
			try {
				List<String> getAggList = new ArrayList<>(Arrays.asList("agg_entrance","agg_city2"));
				helper(aggregationMap,userGroupEntrance,getAggList);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			map.put("userGroupEntrance", userGroupEntrance);
		}else if("travel".equals(typeVal)){//物业
//      	物业费缴纳
			List<StringTerms.Bucket> wypayWillBuckets = getBucketByOneAgg(srb,"wypay_will",conditionsArr);
			Map<String, Object> userGroupWyPayWill = new HashMap<>();
			if(null!=wypayWillBuckets && !wypayWillBuckets.isEmpty()){
				for(Bucket bucket : wypayWillBuckets){
					userGroupWyPayWill.put(bucket.getKeyAsString(),bucket.getDocCount());
				}
			}
			map.put("userGroupWyPayWill",userGroupWyPayWill);

//      	报事渠道
			List<StringTerms.Bucket> postTypeBuckets = getBucketByOneAgg(srb,"posttype",conditionsArr);
			Map<String, Object> userGroupPostType = new HashMap<>();
			if(null!=postTypeBuckets && !postTypeBuckets.isEmpty()) {
				for (Bucket bucket : postTypeBuckets) {
					userGroupPostType.put(bucket.getKeyAsString(), bucket.getDocCount());
				}
			}
			map.put("userGroupPostType",userGroupPostType);

//      	业主缴费渠道
			List<StringTerms.Bucket> wypayTypePreferenceBuckets = getBucketByOneAgg(srb,"wypay_type_preference",conditionsArr);
			Map<String, Object> userGroupWypayTypePreference = new HashMap<>();
			if(null!=wypayTypePreferenceBuckets && !wypayTypePreferenceBuckets.isEmpty()) {
				for (Bucket bucket : wypayTypePreferenceBuckets) {
					userGroupWypayTypePreference.put(bucket.getKeyAsString(), bucket.getDocCount());
				}
			}
			map.put("userGroupWypayTypePreference", userGroupWypayTypePreference);
		}
//		符合条件的人数
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		buildQuery(boolQuery,conditionsArr);
		SearchResponse sr = srb.setQuery(boolQuery).execute().actionGet();
		map.put("hitCount",sr.getHits().getTotalHits());
		return map;
	}

	@Override
	public Map<String,Object> getUserDetails(JSONArray conditionsArr, String wildcardParam, int from, int size) {
        Map<String, Object> map = new HashMap<>();
		TransportClient client = EsInstance.getInstance();
		SearchRequestBuilder srb = client.prepareSearch(USER_PROFILE_ES_INDEX).setTypes("users");
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		BoolQueryBuilder boolQuery2 = QueryBuilders.boolQuery();
		buildQuery(boolQuery,conditionsArr);
		if(!StringUtils.isEmpty(wildcardParam)){
            boolQuery2.should(QueryBuilders.wildcardQuery("phone","*"+ wildcardParam +"*"));
            boolQuery2.should(QueryBuilders.wildcardQuery("z_id", "*"+ wildcardParam +"*"));
        }
        boolQuery.must(boolQuery2);
		SearchResponse searchResponse = srb.setQuery(boolQuery).setFrom(from).setSize(size).get();
		System.out.println(srb.toString());
        long totalHits = searchResponse.getHits().getTotalHits();
        SearchHit[] hits = searchResponse.getHits().getHits();
		List<Object> retList = new ArrayList<>();
		for(SearchHit s : hits){
		    retList.add(s.getSourceAsString());
		}
		map.put("totalHits",totalHits);
		map.put("details",retList);
		return map;
	}

	@Override
	public List<JSONObject> getUserDetailsByScorll(JSONArray conditionsArr,Integer maxNum,Integer size) {
		Map<String, Object> map = new HashMap<>();
		TransportClient client = EsInstance.getInstance();
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		buildQuery(boolQuery,conditionsArr);
		List<JSONObject> ret = new ArrayList<>();
		SearchResponse scrollResp = client.prepareSearch(USER_PROFILE_ES_INDEX).setTypes("users")
				.addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
				.setScroll(new TimeValue(600000))
				.setQuery(boolQuery)
				.setSize(size).get(); //max of 100 hits will be returned for each scroll Scroll until no hits are returned
		do {
			for (SearchHit hit : scrollResp.getHits().getHits()) {
				JSONObject json = JSONObject.parseObject(hit.getSourceAsString());
				ret.add(json);
				maxNum --;
			}
			scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(600000)).execute().actionGet();
		} while(scrollResp.getHits().getHits().length != 0 && maxNum > 0); // Zero hits mark the end of the scroll and the while loop.
		return ret;
	}


	/**
	 * 关键词模糊检索接口
	 * @param tag
	 * @param value
	 * @return
	 */
	public List<String> getWordsByTag(String tag, String value) {
		List<String> lists = new ArrayList<String>();

		//购买兴趣提示词查询
		if(tag.equals(UserActionEnum.SEL_GOODS_DETAILS.getIntestringType()) || tag.equals(UserActionEnum.GET_TOPIC_DETAIL.getIntestringType())){
			//根据关键词查idx
			CorpusKeywords corpusKeywordsParam = new  CorpusKeywords();
			corpusKeywordsParam.setSearchType("like");
			corpusKeywordsParam.setKw(value);
			List<CorpusKeywords> list = corpusKeywordsService.list(corpusKeywordsParam);
			if(null != list && list.size()>0){
				for (CorpusKeywords corpusKeywords : list) {
					if(lists.size()>100){
						break;
					}
					lists.add(corpusKeywords.getKw());
				}
			}
		//普通标签提示词查询
		}else{
			TransportClient client = EsInstance.getInstance();
			BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
			boolQuery.must(QueryBuilders.existsQuery("mid"));
			if(StringUtils.isNotBlank(value)){
				boolQuery.must(QueryBuilders.wildcardQuery(tag,"*"+value+"*"));
			}
			SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices(USER_PROFILE_ES_INDEX).setTypes("userprofile")
					.setQuery(boolQuery).setSize(0);

			String aggName="agg_"+tag;
			searchRequestBuilder.addAggregation(AggregationBuilders.terms(aggName).field(tag).size(100));
			System.out.println(searchRequestBuilder.toString());
			SearchResponse sr = searchRequestBuilder.get();
			Terms aggregation = sr.getAggregations().get(aggName);

			for (Bucket bucket : aggregation.getBuckets()) {
				if(lists.size()>100){
					break;
				}
				lists.add(bucket.getKey().toString());
			}

		}


		return lists;
	}

    private Map<String, Object> getAllUser(JSONArray conditionsArr){
        Map<String, Object> map = new HashMap<String, Object>();
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(USER_PROFILE_ES_INDEX).setTypes("users");

//      用户城市分布
        List<StringTerms.Bucket> cityBuckets = getBucketByOneAgg(srb,"city",conditionsArr);
        Map<String, Long> userGroupCity = new HashMap<>();
		if(null!=cityBuckets &&  !cityBuckets.isEmpty()) {
			for (Bucket bucket : cityBuckets) {
				userGroupCity.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		}
        //城市补0
        Map<String, Long> cityMap = getCityMap();
        cityMap.putAll(userGroupCity);
        map.put("userGroupCity",cityMap);

//      用户年龄分布
        List<StringTerms.Bucket> ageBuckets = getBucketByOneAgg(srb,"age",conditionsArr);
        Map<String, Object> userGroupAge = new HashMap<>();
		if(null!=ageBuckets &&  !ageBuckets.isEmpty()) {
			for (Bucket bucket : ageBuckets) {
				userGroupAge.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		}
        map.put("userGroupAge",userGroupAge);

//      APP活跃频率
        List<StringTerms.Bucket> activeRateBuckets = getBucketByOneAgg(srb,"active_rate",conditionsArr);
        Map<String, Object> userGroupactiveRate = new HashMap<>();
		if(null!=activeRateBuckets &&  !activeRateBuckets.isEmpty()) {
			for (Bucket bucket : activeRateBuckets) {
				userGroupactiveRate.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		}
        map.put("userGroupactiveRate",userGroupactiveRate);

//      APP活跃类型
        List<StringTerms.Bucket> activeTypeBuckets = getBucketByOneAgg(srb,"active_type",conditionsArr);
        Map<String, Object> userGroupActiveType = new HashMap<>();
		if(null!=activeTypeBuckets &&  !activeTypeBuckets.isEmpty()) {
			for (Bucket bucket : activeTypeBuckets) {
				userGroupActiveType.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		}
        map.put("userGroupActiveType",userGroupActiveType);

//      物业费缴纳
        List<StringTerms.Bucket> wypayWillBuckets = getBucketByOneAgg(srb,"wypay_will",conditionsArr);
        Map<String, Object> userGroupWyPayWill = new HashMap<>();
		if(null!=wypayWillBuckets &&  !wypayWillBuckets.isEmpty()) {
			for (Bucket bucket : wypayWillBuckets) {
				userGroupWyPayWill.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		}
        map.put("userGroupWyPayWill",userGroupWyPayWill);

//      报事渠道
        List<StringTerms.Bucket> postTypeBuckets = getBucketByOneAgg(srb,"posttype",conditionsArr);
        Map<String, Object> userGroupPostType = new HashMap<>();
		if(null!=postTypeBuckets &&  !postTypeBuckets.isEmpty()) {
			for (Bucket bucket : postTypeBuckets) {
				userGroupPostType.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		}
        map.put("userGroupPostType",userGroupPostType);

//      业主缴费渠道
        List<StringTerms.Bucket> wypayTypePreferenceBuckets = getBucketByOneAgg(srb,"wypay_type_preference",conditionsArr);
        Map<String, Object> userGroupWypayTypePreference = new HashMap<>();
		if(null!=wypayTypePreferenceBuckets &&  !wypayTypePreferenceBuckets.isEmpty()) {
			for (Bucket bucket : wypayTypePreferenceBuckets) {
				userGroupWypayTypePreference.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		}
        map.put("userGroupWypayTypePreference", userGroupWypayTypePreference);

//      出行规律
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        buildQuery(boolQuery,conditionsArr);
        TermsAggregationBuilder agg_entrance = AggregationBuilders.terms("agg_entrance").field("entrance").size(Integer.MAX_VALUE);
        TermsAggregationBuilder agg_city2 = AggregationBuilders.terms("agg_city2").field("city").size(Integer.MAX_VALUE);
        SearchResponse sr = srb.setQuery(boolQuery).addAggregation(agg_city2.subAggregation(agg_entrance))
                .execute().actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        Map<String, Object> userGroupEntrance = new HashMap<>();
        try {
            List<String> getAggList = new ArrayList<>(Arrays.asList("agg_entrance","agg_city2"));
            helper(aggregationMap,userGroupEntrance,getAggList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        map.put("userGroupEntrance", userGroupEntrance);
//        偏好
        LabelPreferenceResultV2 lable = new LabelPreferenceResultV2();
        lable.setMemberType("member_type_all");
        lable.setPreferenceType("xls_preference");
		List<LabelPreferenceResultV2> xlxList = labelPreferenceResultV2Dao.list(lable, "sort",15,0);
		lable.setPreferenceType("service_preference");
		List<LabelPreferenceResultV2> serviceList = labelPreferenceResultV2Dao.list(lable, "sort",15,0);
		lable.setPreferenceType("field_preference");
		List<LabelPreferenceResultV2> fieldList = labelPreferenceResultV2Dao.list(lable, "sort",15,0);
        getPerferenceName(xlxList,serviceList,fieldList);
		map.put("userGroupXlsPreference",xlxList);
		map.put("userGroupServicePreference",serviceList);
		map.put("userGroupFieldPreference",fieldList);
		return map;
    }

    private Map<String, Object> getCUser(JSONArray conditionsArr){
        Map<String, Object> map = new HashMap<String, Object>();
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(USER_PROFILE_ES_INDEX).setTypes("users");

//      用户城市分布
        List<StringTerms.Bucket> cityBuckets = getBucketByOneAgg(srb,"city",conditionsArr);
        Map<String, Long> userGroupCity = new HashMap<>();
		if(null!=cityBuckets &&  !cityBuckets.isEmpty()) {
			for (Bucket bucket : cityBuckets) {
				userGroupCity.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		}
        Map<String, Long> cityMap = getCityMap();
        cityMap.putAll(userGroupCity);
        map.put("userGroupCity",cityMap);

//      用户年龄分布
        List<StringTerms.Bucket> ageBuckets = getBucketByOneAgg(srb,"age",conditionsArr);
        Map<String, Object> userGroupAge = new HashMap<>();
		if(null!=ageBuckets &&  !ageBuckets.isEmpty()) {
			for (Bucket bucket : ageBuckets) {
				userGroupAge.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		}
        map.put("userGroupAge",userGroupAge);

//      APP活跃频率
        List<StringTerms.Bucket> activeRateBuckets = getBucketByOneAgg(srb,"active_rate",conditionsArr);
        Map<String, Object> userGroupactiveRate = new HashMap<>();
		if(null!=activeRateBuckets &&  !activeRateBuckets.isEmpty()) {
			for (Bucket bucket : activeRateBuckets) {
				userGroupactiveRate.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		}
        map.put("userGroupactiveRate",userGroupactiveRate);

//      APP活跃类型
        List<StringTerms.Bucket> activeTypeBuckets = getBucketByOneAgg(srb,"active_type",conditionsArr);
        Map<String, Object> userGroupActiveType = new HashMap<>();
		if(null!=activeTypeBuckets &&  !activeTypeBuckets.isEmpty()) {
			for (Bucket bucket : activeTypeBuckets) {
				userGroupActiveType.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		}
        map.put("userGroupActiveType",userGroupActiveType);

//      出行规律
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        buildQuery(boolQuery,conditionsArr);
        TermsAggregationBuilder agg_entrance = AggregationBuilders.terms("agg_entrance").field("entrance").size(Integer.MAX_VALUE);
        TermsAggregationBuilder agg_city2 = AggregationBuilders.terms("agg_city2").field("city").size(Integer.MAX_VALUE);
        SearchResponse sr = srb.setQuery(boolQuery).addAggregation(agg_city2.subAggregation(agg_entrance))
                .execute().actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        Map<String, Object> userGroupEntrance = new HashMap<>();
        try {
            List<String> getAggList = new ArrayList<>(Arrays.asList("agg_entrance","agg_city2"));
            helper(aggregationMap,userGroupEntrance,getAggList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        map.put("userGroupEntrance", userGroupEntrance);
//      偏好
		LabelPreferenceResultV2 lable = new LabelPreferenceResultV2();
		lable.setMemberType("member_type_c");
		lable.setPreferenceType("xls_preference");
		List<LabelPreferenceResultV2> xlxList = labelPreferenceResultV2Dao.list(lable, "sort",15,0);
		lable.setPreferenceType("service_preference");
		List<LabelPreferenceResultV2> serviceList = labelPreferenceResultV2Dao.list(lable, "sort",15,0);
		lable.setPreferenceType("field_preference");
		List<LabelPreferenceResultV2> fieldList = labelPreferenceResultV2Dao.list(lable, "sort",15,0);
        getPerferenceName(xlxList,serviceList,fieldList);
		map.put("userGroupXlsPreference",xlxList);
		map.put("userGroupServicePreference",serviceList);
		map.put("userGroupFieldPreference",fieldList);
        return map;
    }
    private Map<String, Object> getPUser(JSONArray conditionsArr){
        Map<String, Object> map = new HashMap<String, Object>();
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(USER_PROFILE_ES_INDEX).setTypes("users");

//      用户城市分布
        List<StringTerms.Bucket> cityBuckets = getBucketByOneAgg(srb,"city",conditionsArr);
        Map<String, Long> userGroupCity = new HashMap<>();
		if(null!=cityBuckets &&  !cityBuckets.isEmpty()) {
			for (Bucket bucket : cityBuckets) {
				userGroupCity.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		}
        Map<String, Long> cityMap = getCityMap();
        cityMap.putAll(userGroupCity);
        map.put("userGroupCity",cityMap);

//      用户年龄分布
        List<StringTerms.Bucket> ageBuckets = getBucketByOneAgg(srb,"age",conditionsArr);
        Map<String, Object> userGroupAge = new HashMap<>();
		if(null!=ageBuckets &&  !ageBuckets.isEmpty()) {
			for (Bucket bucket : ageBuckets) {
				userGroupAge.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		}
        map.put("userGroupAge",userGroupAge);

//      物业费缴纳
        List<StringTerms.Bucket> wypayWillBuckets = getBucketByOneAgg(srb,"wypay_will",conditionsArr);
        Map<String, Object> userGroupWyPayWill = new HashMap<>();
        if(null!=wypayWillBuckets &&  !wypayWillBuckets.isEmpty()){
			for(Bucket bucket : wypayWillBuckets){
				userGroupWyPayWill.put(bucket.getKeyAsString(),bucket.getDocCount());
			}
		}
        map.put("userGroupWyPayWill",userGroupWyPayWill);

//      报事渠道
        List<StringTerms.Bucket> postTypeBuckets = getBucketByOneAgg(srb,"posttype",conditionsArr);
        Map<String, Object> userGroupPostType = new HashMap<>();
		if(null!=postTypeBuckets &&  !postTypeBuckets.isEmpty()) {
			for (Bucket bucket : postTypeBuckets) {
				userGroupPostType.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		}
        map.put("userGroupPostType",userGroupPostType);

//      缴费渠道
        List<StringTerms.Bucket> wypayTypePreferenceBuckets = getBucketByOneAgg(srb,"wypay_type_preference",conditionsArr);
        Map<String, Object> userGroupWypayTypePreference = new HashMap<>();
		if(null!=wypayTypePreferenceBuckets &&  !wypayTypePreferenceBuckets.isEmpty()) {
			for (Bucket bucket : wypayTypePreferenceBuckets) {
				userGroupWypayTypePreference.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		}
        map.put("userGroupWypayTypePreference", userGroupWypayTypePreference);

//      出行规律
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        buildQuery(boolQuery,conditionsArr);
        TermsAggregationBuilder agg_entrance = AggregationBuilders.terms("agg_entrance").field("entrance").size(Integer.MAX_VALUE);
        TermsAggregationBuilder agg_city2 = AggregationBuilders.terms("agg_city2").field("city").size(Integer.MAX_VALUE);
        SearchResponse sr = srb.setQuery(boolQuery).addAggregation(agg_city2.subAggregation(agg_entrance))
                .execute().actionGet();
        Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
        Map<String, Object> userGroupEntrance = new HashMap<>();
        try {
            List<String> getAggList = new ArrayList<>(Arrays.asList("agg_entrance","agg_city2"));
            helper(aggregationMap,userGroupEntrance,getAggList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        map.put("userGroupEntrance", userGroupEntrance);
        return map;
    }

    @DynamicDataSource("databus")
    private void  getPerferenceName(List<LabelPreferenceResultV2> xlxList,List<LabelPreferenceResultV2> serviceList,List<LabelPreferenceResultV2> fieldList){
        List<String> strings = new ArrayList<>();
        List<String> strings2 = new ArrayList<>();
        List<String> strings3 = new ArrayList<>();
        for(int i=0;i<xlxList.size();i++){
            strings.add(xlxList.get(i).getPreferenceId());
        }
        for(int i=0;i<serviceList.size();i++){
            strings2.add(serviceList.get(i).getPreferenceId());
        }
		for(int i=0;i<fieldList.size();i++){
			strings3.add(fieldList.get(i).getPreferenceId());
		}

        List<DIMSKU> dimskuList = dimskuDao.listByIDs(StringUtils.join(strings,","));
        List<DIMSKU> dimskuList2 = dimskuDao.listByIDs(StringUtils.join(strings2,","));
        List<DIMLiveClassify> dimLiveClassifyList = dimLiveClassifyDao.listByIDs(StringUtils.join(strings3,","));
        Map<Long, DIMSKU> dimskuMap = dimskuList.stream().collect(Collectors.toMap(DIMSKU::getId, a -> a, (k1, k2) -> k1));
        Map<Long, DIMSKU> dimskuMap2 = dimskuList2.stream().collect(Collectors.toMap(DIMSKU::getId, a -> a, (k1, k2) -> k1));
        Map<Long, DIMLiveClassify> dimskuMap3 = dimLiveClassifyList.stream().collect(Collectors.toMap(DIMLiveClassify::getId, a -> a, (k1, k2) -> k1));
        for(int i=0;i<xlxList.size();i++){
            LabelPreferenceResultV2 xlx = xlxList.get(i);
            if(dimskuMap.containsKey(Long.valueOf(xlx.getPreferenceId()))){
                DIMSKU dimsku = dimskuMap.get(Long.valueOf(xlx.getPreferenceId()));
                xlx.setName(dimsku.getWareName());
            }
        }
        for(int i=0;i<serviceList.size();i++){
            LabelPreferenceResultV2 server = serviceList.get(i);
            if(dimskuMap2.containsKey(Long.valueOf(server.getPreferenceId()))){
				DIMSKU dimsku = dimskuMap2.get(Long.valueOf(server.getPreferenceId()));
				server.setName(dimsku.getWareName()+"("+dimsku.getId()+")");
            }
        }
		for(int i=0;i<fieldList.size();i++){
			LabelPreferenceResultV2 field = fieldList.get(i);
			if(dimskuMap3.containsKey(Long.valueOf(field.getPreferenceId()))){
				DIMLiveClassify dimLiveClassify = dimskuMap3.get(Long.valueOf(field.getPreferenceId()));
				field.setName(dimLiveClassify.getName()+"("+dimLiveClassify.getId()+")");
			}
		}
    }

    private Map<String,Long> getCityMap(){
        Map<String,Long> cityMap = new HashMap(){};
        cityMap.put("1",0L);
        cityMap.put("11",0L);
        cityMap.put("14",0L);
        cityMap.put("3",0L);
        cityMap.put("5",0L);
        cityMap.put("9",0L);
        cityMap.put("31",0L);
        return cityMap;
    }

    @Override
    @CachePut(value = "redisCache",key = "'userPortrait:allUserOverview'")
	@DynamicDataSource("databus")
    public Map<String, Object> putCacheAllUserOverview() {
        return getAllUser(null);
    }

	@Override
    @CachePut(value = "redisCache",key = "'userPortrait:CUserOverview'")
	@DynamicDataSource("databus")
    public Map<String, Object> putCacheCUserOverview() {
        JSONArray conditionsArr = new JSONArray();
        JSONObject o = new JSONObject();
        o.put("tag","member_type");
        o.put("rule","in");
        o.put("value1","member_type_c,member_type_pc");
        conditionsArr.add(o);
        return getCUser(conditionsArr);
    }

	@Override
    @CachePut(value = "redisCache",key = "'userPortrait:PUserOverview'")
    public Map<String, Object> putCachePUserOverview() {
        JSONArray conditionsArr = new JSONArray();
        JSONObject o = new JSONObject();
        o.put("tag","member_type");
        o.put("rule","in");
        o.put("value1","member_type_p,member_type_pc");
        conditionsArr.add(o);
        return getPUser(conditionsArr);
    }

	@Override
	@DynamicDataSource("databus")
	public Map<Object,Object> searchSKUByWareNameOrID(String searchKey, Boolean isMatch) {
		boolean flag = true;
		try {
			Long.valueOf(searchKey);
		}catch (Exception e){
			flag = false;
		}
		List<DIMSKU> dimskuList = dimskuDao.matchSKUIDOrWareName(searchKey, isMatch, flag);
		Map<Object,Object>  ret= new HashMap<>();
		List<SelectVO> voList = new ArrayList<>();
		for (DIMSKU dimsku : dimskuList) {
			voList.add(new SelectVO(dimsku.getId(),dimsku.getWareName()));
		}
		ret.put("results",voList);
		return ret;
	}

	@Override
	@DynamicDataSource("databus")
	public int searchSKUByWareNameOrIDCount(String searchKey, Boolean isMatch) {
    	boolean flag = true;
    	try {
    		Long.valueOf(searchKey);
		}catch (Exception e){
    		flag = false;
		}
		return dimskuDao.matchSKUIDOrWareNameCount(searchKey, isMatch, flag);
	}

	private void getUserDetailsByScorll(JSONArray conditionsArr,Integer maxNum,Integer size, BufferedWriter out) throws Exception {
		Map<String, Object> map = new HashMap<>();
		TransportClient client = EsInstance.getInstance();
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		buildQuery(boolQuery,conditionsArr);
		SearchResponse scrollResp = client.prepareSearch(USER_PROFILE_ES_INDEX).setTypes("users")
				.addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
				.setScroll(new TimeValue(600000))
				.setQuery(boolQuery)
				.setSize(size).get(); //max of 100 hits will be returned for each scroll Scroll until no hits are returned
		do {
			for (SearchHit hit : scrollResp.getHits().getHits()) {
				JSONObject json = JSONObject.parseObject(hit.getSourceAsString());
				String mid = json.getString("m_id");
				if(StringUtils.isNotEmpty(mid)) {
					try{
						out.write(mid);
						out.write("\r\n");
					}catch (Exception e){
						e.printStackTrace();
					}
				}
				maxNum --;
			}

			out.flush();
			scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(600000)).execute().actionGet();
		} while(scrollResp.getHits().getHits().length != 0 && maxNum > 0); // Zero hits mark the end of the scroll and the while loop.
	}

    @Override
    public List<UserPortraitDetailVO> getUserInfo(JSONObject queryObject) {
        JSONArray conditions = (JSONArray) queryObject.get("conditions");
        if(conditions==null || conditions.size()==0){
            return null;
        }
        List<JSONObject> userDetails = getUserDetailsByScorll(conditions,100000,5000);
        List<UserPortraitDetailVO> ret = new ArrayList<>();
        for (JSONObject s : userDetails) {
			String mid = (String) s.get("m_id");
			String pid = (String) s.get("p_id");
			if(StringUtils.isEmpty(mid)){
				mid = "";
			}
			if(StringUtils.isEmpty(pid)){
				pid = "";
			}
            ret.add(new UserPortraitDetailVO(mid,pid));
        }
        return ret;
    }

	@Override
	@DynamicDataSource("databus")
	public int searchClassifByNameOrIDCount(String searchKey, Boolean isMatch) {
		boolean flag = true;
		try {
			Long.valueOf(searchKey);
		}catch (Exception e){
			flag = false;
		}
		return dimLiveClassifyDao.matchClassifIDOrNameCount(searchKey, isMatch, flag);
	}

	@Override
	@DynamicDataSource("databus")
	public Map<Object, Object> searchClassifByNameOrID(String searchKey, Boolean isMatch) {
    	boolean flag = true;
    	try {
    		Long.valueOf(searchKey);
		}catch (Exception e){
    		flag = false;
		}
		List<DIMLiveClassify> dimLiveClassifiesList = dimLiveClassifyDao.matchClassifIDOrName(searchKey, isMatch, flag);
		Map<Object,Object>  ret= new HashMap<>();
		List<SelectVO> voList = new ArrayList<>();
		for (DIMLiveClassify dimLiveClassify : dimLiveClassifiesList) {
			voList.add(new SelectVO(dimLiveClassify.getId(),dimLiveClassify.getName()));
		}
		ret.put("results",voList);
		return ret;
	}

	@Override
	public Map<Object,Object> checkMaxNum(int count){
		if(count > 10000){
			Map<Object,Object> ret = new HashMap<>();
			List<SelectVO> list = new ArrayList(){};
			SelectVO selectVO = new SelectVO(-1L,"请出入更详细的名称或者ID");
			list.add(selectVO);
			ret.put("results",list);
			return ret;
		}else{
			return null;
		}
	}

	@Override
	@DynamicDataSource("databus")
	public int searchCategoryByNameOrIDCount(String searchKey, Boolean isMatch) {
		boolean flag = true;
		try {
			Long.valueOf(searchKey);
		}catch (Exception e){
			flag = false;
		}
		return dimCategoryDao.matchCategoryIDOrNameCount(searchKey, isMatch, flag);
	}

	@Override
	@DynamicDataSource("databus")
	public Map<Object, Object> searchCategoryByNameOrID(String searchKey, Boolean isMatch) {
		boolean flag = true;
		try {
			Long.valueOf(searchKey);
		}catch (Exception e){
			flag = false;
		}
		List<DIMCategory> dimCategoryList = dimCategoryDao.matchCategoryIDOrName(searchKey, isMatch, flag);
		Map<Object,Object>  ret= new HashMap<>();
		List<SelectVO> voList = new ArrayList<>();
		for (DIMCategory dimCategory : dimCategoryList) {
			voList.add(new SelectVO(dimCategory.getId(),dimCategory.getName()));
		}
		ret.put("results",voList);
		return ret;
	}
}
