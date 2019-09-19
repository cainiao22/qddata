package com.qding.bigdata.ds.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qding.bigdata.ds.VO.UserPortraitDetailVO;
import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.UserActionEnum;
import com.qding.bigdata.ds.model.CorpusKeywords;
import com.qding.bigdata.ds.model.DIMSKU;
import com.qding.bigdata.ds.model.Tag;
import com.qding.bigdata.ds.model.UserGroup;
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
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.valuecount.InternalValueCount;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

@Service
public interface UserProfileV2Service {

	Map<String, Object> query(JSONObject queryJson);
	Map<String, Object> queryAllUserOverview(JSONArray conditionsArr);

	Map<String, Object> queryCUserOverview(JSONArray conditionsArr);
	Map<String, Object> queryPUserOverview(JSONArray conditionsArr);
	Map<String, Object> queryForSum(JSONObject queryJson);

	/**
	 * 获取组装结果
	 * @param map
	 * @param searchRequestBuilder
	 * @param aggArr
	 */
	void getResultsForSum(Map<String, Object> map,
			SearchRequestBuilder searchRequestBuilder, JSONArray aggArr);

	/**
	 * 构建聚合字段
	 * @param searchRequestBuilder
	 * @param aggArr
	 */
	void buildAggregationsForSum(SearchRequestBuilder searchRequestBuilder,JSONArray aggArr);

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
	Object exportUsers(String userGroupId);

	/**
	 * 获取偏好
	 * @param conditionsArr
	 * @return
	 */
	Map<String,Object> queryPreferences(JSONArray conditionsArr);
	Map<String,Object> queryPreferences2(JSONArray conditionsArr);

	/**
	 * 根据条件过滤用户群体查询某些属性的图表大数据
	 * @param conditionsArr 查询条件
	 * @param typeVal 业务类型
	 * @return
	 */
	Map<String, Object> getValueByConditional(JSONArray conditionsArr,String typeVal);

	/**
	 * @param conditionsArr
	 * @param from 起始页数
	 * @param size 条数
	 * @return
	 */
	Map<String,Object> getUserDetails(JSONArray conditionsArr,String wildcardParam,int from,int size);
	List<JSONObject> getUserDetailsByScorll(JSONArray conditionsArr,Integer maxNum,Integer size);

	/**
	 * 初始化缓存业务数据-用户画像-概览-全部
	 * @return
	 */
	Map<String, Object> putCacheAllUserOverview();
	/**
	 * 初始化缓存业务数据-用户画像-概览-C端
	 * @return
	 */
	Map<String, Object> putCacheCUserOverview();
	/**
	 * 初始化缓存业务数据-用户画像-概览-P端
	 * @return
	 */
	Map<String, Object> putCachePUserOverview();

	/**
	 * 搜索DIMSKU
	 * @param searchKey
	 * @return
	 */
	Map<Object,Object> searchSKUByWareNameOrID(String searchKey, Boolean isMatch);

    int searchSKUByWareNameOrIDCount(String searchKey, Boolean isMatch);

	List<UserPortraitDetailVO> getUserInfo(JSONObject queryObject);

	int searchClassifByNameOrIDCount(String searchKey, Boolean isMatch);

	Map<Object,Object> searchClassifByNameOrID(String searchKey, Boolean isMatch);

	Map<Object,Object> checkMaxNum(int count);

	int searchCategoryByNameOrIDCount(String searchKey, Boolean isMatch);

	Map<Object,Object> searchCategoryByNameOrID(String searchKey, Boolean isMatch);

	List<StringTerms.Bucket>  getBucketByOneAgg(SearchRequestBuilder srb, String field, JSONArray conditionsArr);
	List<StringTerms.Bucket>  getBucketByOneAgg(SearchRequestBuilder srb, String field, JSONArray conditionsArr,BoolQueryBuilder boolQuery);
	List<? extends Bucket>  getBucketByOneAgg2(SearchRequestBuilder srb, String field, JSONArray conditionsArr,BoolQueryBuilder boolQuery);
}
