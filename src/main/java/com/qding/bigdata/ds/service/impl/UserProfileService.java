package com.qding.bigdata.ds.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.UserActionEnum;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.service.CorpusKeywordsService;
import com.qding.bigdata.ds.service.TagService;
import com.qding.bigdata.ds.service.UserGroupService;
import com.qding.bigdata.ds.util.DateUtil;
import com.qding.bigdata.ds.util.EsInstance;
import com.qding.bigdata.ds.util.PropertiesUtil;
import com.qding.bigdata.ds.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.valuecount.InternalValueCount;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

@Service
public class UserProfileService {

    private static final String USER_PROFILE_ES_INDEX = "qding_new";

	public static final Logger logger = LoggerFactory.getLogger(UserProfileService.class);

	@Autowired
	private TagService tagService;
	
	@Autowired
	private CorpusKeywordsService  corpusKeywordsService;
	
	@Autowired
	private UserGroupService userGroupService;
	
	private boolean interestingBuyAggFlag = false;//购买兴趣聚合标志
	private boolean interestingReadAggFlag = false;//阅读兴趣聚合标志
	
	public Map<String, Object> query(JSONObject queryJson) {
		Map<String, Object> map = new HashMap<>();
		TransportClient client = EsInstance.getInstance();
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		boolQuery.must(QueryBuilders.existsQuery("mid"));
		Map<Long,String> idxKeyWordBuyMap = new HashMap<>();
		Map<Long,String> idxKeyWordReadMap = new HashMap<>();

		//1、构建查询条件
		JSONArray conditionsArr = queryJson.getJSONArray("conditions");
		buildQuery(boolQuery, idxKeyWordBuyMap, idxKeyWordReadMap,
				conditionsArr);
		
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices(USER_PROFILE_ES_INDEX).setTypes("userprofile")
				.setQuery(boolQuery).setSize(0);
		
		//2、构建聚合字段
		JSONArray aggArr = queryJson.getJSONArray("agg");

		if(null != aggArr){
			interestingBuyAggFlag = getInterestingAggFlag(aggArr,
					UserActionEnum.SEL_GOODS_DETAILS.getIntestringType());//购买兴趣关键词是否聚合标志

			interestingReadAggFlag = getInterestingAggFlag(aggArr,
					UserActionEnum.GET_TOPIC_DETAIL.getIntestringType());//阅读兴趣关键词是否聚合标志

			buildAggregations(searchRequestBuilder, aggArr,
					idxKeyWordBuyMap, idxKeyWordReadMap);
		}


		//3、获取组装结果
		getResults(map, idxKeyWordBuyMap,idxKeyWordReadMap, searchRequestBuilder, aggArr);
		return map;
	}


	public Map<String, Object> queryForSum(JSONObject queryJson) {
		Map<String, Object> map = new HashMap<String, Object>();
		TransportClient client = EsInstance.getInstance();
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		boolQuery.must(QueryBuilders.existsQuery("mid"));
		Map<Long,String> idxKeyWordBuyMap = new HashMap<>();
		Map<Long,String> idxKeyWordReadMap = new HashMap<Long,String>();

		//1、构建查询条件
		JSONArray conditionsArr = queryJson.getJSONArray("conditions");
		
		//把聚合字段加入到查询条件，sum求和时，需要把小于0的数据过滤掉
		JSONArray aggArr = queryJson.getJSONArray("agg");
		buildQuery(boolQuery, idxKeyWordBuyMap, idxKeyWordReadMap,
				conditionsArr);
		
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
	private void getResultsForSum(Map<String, Object> map,
			SearchRequestBuilder searchRequestBuilder, JSONArray aggArr) {
		SearchResponse sr = searchRequestBuilder.get();
		logger.info(searchRequestBuilder.toString());
		map.put("totalHit", sr.getHits().getTotalHits());
		List<LinkedHashMap<String, Object>> aggList = new ArrayList<LinkedHashMap<String, Object>>();
		if(aggArr != null){
			for (Object aggObj : aggArr) {
				LinkedHashMap<String, Object> aggMap = new LinkedHashMap<String, Object>();
				String tag = (String) aggObj;
				PortraitTag thisTag = tagService.getByTagName(tag);
				aggMap.put("tag", tag);
				aggMap.put("name", thisTag.getName());

				Sum agg = sr.getAggregations().get("agg_" + tag);
				double value = agg.getValue();
				aggMap.put("data", value);
				aggList.add(aggMap);
			}
		}
		map.put("aggregations", aggList);
	}
	
	/**
	 * 获取组装结果
	 * @param map
	 * @param idxKeyWordBuyMap
	 * @param idxKeyWordReadMap
	 * @param searchRequestBuilder
	 * @param aggArr
	 */
	private void getResults(Map<String, Object> map,
			Map<Long, String> idxKeyWordBuyMap,Map<Long, String> idxKeyWordReadMap,
			SearchRequestBuilder searchRequestBuilder, JSONArray aggArr) {
		SearchResponse sr = searchRequestBuilder.get();
		logger.info(searchRequestBuilder.toString());
		map.put("totalHit", sr.getHits().getTotalHits());
		List<LinkedHashMap<String, Object>> aggList = new ArrayList<LinkedHashMap<String, Object>>();
		//感兴趣结果map集合
		LinkedHashMap<Object, Long> interestingBuyValueMap = new LinkedHashMap<>();
		LinkedHashMap<Object, Long> interestingReadValueMap = new LinkedHashMap<Object, Long>();

		if(null!= aggArr){
			for (Object aggObj : aggArr) {
				LinkedHashMap<String, Object> aggMap = new LinkedHashMap<>();
				String tag = (String) aggObj;
				//组装感兴趣关键词结果
				if(tag.startsWith(UserActionEnum.SEL_GOODS_DETAILS.getIntestringType()+".k")){
					getInterestingValueMap(idxKeyWordBuyMap, sr,
							interestingBuyValueMap, tag,UserActionEnum.SEL_GOODS_DETAILS.getIntestringType());
				}else if(tag.startsWith(UserActionEnum.GET_TOPIC_DETAIL.getIntestringType()+".k")){
					getInterestingValueMap(idxKeyWordReadMap, sr,
							interestingReadValueMap, tag,UserActionEnum.GET_TOPIC_DETAIL.getIntestringType());
				}else{

					PortraitTag thisTag = tagService.getByTagName(tag);
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
			//加入购买感兴趣词的聚合结果
			if(interestingBuyAggFlag){
				addEleAggList(aggList, interestingBuyValueMap,UserActionEnum.SEL_GOODS_DETAILS.getIntestringType());
			}
			//加入阅读感兴趣词的聚合结果
			if(interestingReadAggFlag){
				addEleAggList(aggList, interestingReadValueMap,UserActionEnum.GET_TOPIC_DETAIL.getIntestringType());
			}
		}

		map.put("aggregations", aggList);
	}


	/**
	 * 构建聚合字段
	 * @param searchRequestBuilder
	 * @param aggArr
	 * @param idxKeyWordBuyMap
	 * @param idxKeyWordReadMap
	 */
	private void buildAggregations(SearchRequestBuilder searchRequestBuilder,
			JSONArray aggArr,Map<Long,String> idxKeyWordBuyMap ,Map<Long,String> idxKeyWordReadMap) {
		
		//添加购买关键词聚类字段
		if(null!=idxKeyWordBuyMap && idxKeyWordBuyMap.size()>0 && interestingBuyAggFlag){
			for (Long idx : idxKeyWordBuyMap.keySet()) {
				aggArr.add(UserActionEnum.SEL_GOODS_DETAILS.getIntestringType()+".k"+idx);
			}
		}
		//添加阅读关键词聚类字段
		if(null!=idxKeyWordReadMap && idxKeyWordReadMap.size()>0 && interestingReadAggFlag){
			for (Long idx : idxKeyWordReadMap.keySet()) {
				aggArr.add(UserActionEnum.GET_TOPIC_DETAIL.getIntestringType()+".k"+idx);
			}
		}
		for (Object aggObj : aggArr) {
			String tag = (String) aggObj;
			String aggName = "agg_" + tag;
			PortraitTag thisTag = null;
			//感兴趣关键词聚合
			if(tag.startsWith(UserActionEnum.SEL_GOODS_DETAILS.getIntestringType()) ||
					tag.startsWith(UserActionEnum.GET_TOPIC_DETAIL.getIntestringType())){
				searchRequestBuilder.addAggregation(AggregationBuilders.count(aggName).field(tag));
			}
			//普通条件聚合
			else if(!tag.startsWith("interesting")){
				tagService.getByTagName(tag);
				searchRequestBuilder.addAggregation(AggregationBuilders.terms(aggName).field(tag).size(100));
			}
		}
	}

	
	/**
	 * 构建聚合字段
	 * @param searchRequestBuilder
	 * @param aggArr
	 */
	private void buildAggregationsForSum(SearchRequestBuilder searchRequestBuilder,JSONArray aggArr) {
		if(aggArr == null){
			return;
		}
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
	 * @param idxKeyWordBuyMap
	 * @param idxKeyWordReadMap
	 * @param conditionsArr
	 */
	private void buildQuery(BoolQueryBuilder boolQuery,
			Map<Long, String> idxKeyWordBuyMap,
			Map<Long, String> idxKeyWordReadMap, JSONArray conditionsArr) {
		for (Object conditionObj : conditionsArr) {
			JSONObject condition = (JSONObject) conditionObj;
			String tag = condition.getString("tag");
			String rule = condition.getString("rule");
			String value1 = condition.getString("value1");
			String value2 = condition.getString("value2");
			
			if(tag.equals(UserActionEnum.SEL_GOODS_DETAILS.getIntestringType())){
				buildInsertingQuery(boolQuery, idxKeyWordBuyMap, tag, rule,
						value1);
			}else if(tag.equals(UserActionEnum.GET_TOPIC_DETAIL.getIntestringType()) ){
				buildInsertingQuery(boolQuery, idxKeyWordReadMap, tag, rule,
						value1);

			}else{
				if ("eq".equals(rule)) {
					boolQuery.must(QueryBuilders.termsQuery(tag, value1.split(",")));
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
					boolQuery.must(nocontainQuery);
				}
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
		PortraitTag thisTag = tagService.getByTagName(intestringType);
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
	       Collections.sort(list, (o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));
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
	public Object exportUsers(String userGroupId) throws Exception{
		long beginTime = System.currentTimeMillis();

		Map<String,Object> rsMap = new HashMap<String, Object>();

		try {
			SearchHits searchHit = null;
			String filePath = PropertiesUtil.getPropertiesByKey(
					Constant.CONFIGFILE, "export.user.path")
					+ File.separator
					+ userGroupId + File.separator;
			String fileName = filePath + "export_user_" + userGroupId+".txt";
			logger.info("origin fileName:{}", fileName);
			//检查文件是否存在，存在则重命名
			File oldFile = new File(fileName);
			if (oldFile.exists()) {
				String newFileName = "export_user_"
						+ userGroupId+"_bak_"
						+ DateUtil.formatDateToFullString(new Date())+".txt";
				logger.info("origin fileName:[{}]  exists,rename to [{}]", oldFile.getName(), newFileName);
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

				TransportClient client = EsInstance.getInstance();
				BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
				boolQuery.must(QueryBuilders.existsQuery("mid"));
				Map<Long, String> idxKeyWordBuyMap = new HashMap<Long, String>();
				Map<Long, String> idxKeyWordReadMap = new HashMap<Long, String>();

				//1、构建查询条件
				JSONArray conditionsArr = queryJson.getJSONArray("conditions");
				buildQuery(boolQuery, idxKeyWordBuyMap, idxKeyWordReadMap,
						conditionsArr);

				SearchResponse response = client.prepareSearch(USER_PROFILE_ES_INDEX)
						.setTypes("userprofile").setQuery(boolQuery)
						.setSize(100000).setFetchSource(false)
						.setScroll(new TimeValue(600000))
						.setSearchType(SearchType.DEFAULT).execute()
						.actionGet();//setSearchType(SearchType.Scan) 告诉ES不需要排序只要结果返回即可 setScroll(new TimeValue(600000)) 设置滚动的时间
				searchHit = response.getHits();
				logger.info("查询数量 ：{}", searchHit.getHits().length);
				for (int i = 0; i < searchHit.getHits().length; i++) {
					String json = searchHit.getHits()[i].getId();
					out.write(json);
					out.write("\r\n");
				}

				String scrollid = response.getScrollId();
				//把导出的结果以JSON的格式写到文件里

				//每次返回数据10000条。一直循环查询直到所有的数据都查询出来
				while (true) {
					SearchResponse response2 = client
							.prepareSearchScroll(scrollid)
							.setScroll(new TimeValue(100000)).execute()
							.actionGet();
					searchHit = response2.getHits();
					//再次查询不到数据时跳出循环
					if (searchHit.getHits().length == 0) {
						break;
					}
					logger.info("查询数量 ：{}", searchHit.getHits().length);
					for (int i = 0; i < searchHit.getHits().length; i++) {
						String json = searchHit.getHits()[i].getId();
						out.write(json);
						out.write("\r\n");
					}
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
			makeCorpusKeywords(value, lists);
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
			logger.info(searchRequestBuilder.toString());
			SearchResponse sr = searchRequestBuilder.get();
			Terms aggregation = sr.getAggregations().get(aggName);

			for (Terms.Bucket bucket : aggregation.getBuckets()) {
				if(lists.size()>100){
					break;
				}
				lists.add(bucket.getKey().toString());
			}

		}


		return lists;
	}

	private void makeCorpusKeywords(String value, List<String> lists) {
		//根据关键词查idx
		CorpusKeywords corpusKeywordsParam = new  CorpusKeywords();
		corpusKeywordsParam.setSearchType("like");
		corpusKeywordsParam.setKw(value);
		List<CorpusKeywords> list = corpusKeywordsService.list(corpusKeywordsParam);
		if(!CollectionUtils.isEmpty(list)){
            for (CorpusKeywords corpusKeywords : list) {
                if(lists.size()>100){
                    break;
                }
                lists.add(corpusKeywords.getKw());
            }
        }
	}
}
