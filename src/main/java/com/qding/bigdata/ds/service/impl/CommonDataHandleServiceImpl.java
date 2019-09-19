package com.qding.bigdata.ds.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.alibaba.fastjson.JSON;
import com.qding.bigdata.ds.Exception.CommonDataSearchEmptyException;
import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.util.DateUtil;
import com.qding.bigdata.ds.util.HttpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qding.bigdata.ds.cache.RedisCache;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.component.CommonDataHandler;
import com.qding.bigdata.ds.dao.CommonDataSummaryDao;
import com.qding.bigdata.ds.enums.CommonDataHandlerEnum;
import com.qding.bigdata.ds.enums.RedisDataTypeEnum;
import com.qding.bigdata.ds.model.CommonDataDetail;
import com.qding.bigdata.ds.model.CommonDataSummary;
import com.qding.bigdata.ds.service.CommonDataHandleService;
import com.qding.bigdata.ds.util.BeanMapper;
import com.qding.framework.common.codis.JedisClient;
import com.qding.framework.common.util.MD5Util;

/**
 * Created by yanpf on 2017/7/27.
 */
@Service
public class CommonDataHandleServiceImpl implements CommonDataHandleService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CommonDataSummaryDao commonDataSummaryDao;

	@Autowired
	Map<String, CommonDataHandler> commonDataHandlerMap;
//	@Autowired
	private JedisClient jedisClient;

	private static ExecutorService exec = Executors.newFixedThreadPool(30, new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread t = Executors.defaultThreadFactory().newThread(r);
			t.setDaemon(true);
			return t;
		}
	});

	@Override
	public Result<List<Map<String, Object>>> execute(String jobName, Map<String, String> params) {
		CommonDataSummary commonDataSummary = commonDataSummaryDao.getByName(jobName);
		if (commonDataSummary == null) {
			return Result.failed(1, "该id不存在对应的接口查询");
		}
		String dataSource = commonDataSummary.getDataSource();
		String handlerName = CommonDataHandlerEnum.getHandler(dataSource);
		if (dataSource == null || handlerName == null) {
			return Result.failed(2, "不支持的查询来源:" + dataSource);
		}
		try {
			long startTime = System.currentTimeMillis();
			Result<List<Map<String, Object>>> result = commonDataHandlerMap.get(handlerName).execute(commonDataSummary,
					params);
			result.setTimeUsed(System.currentTimeMillis() - startTime);
			if(CommonDataHandlerEnum.SQL.getDataSource().equals(dataSource)){
				exec.submit(new CommonDataTimeUsedThread(jobName, params, result, result.getRemark()));
			}
			result.setRemark(null);
			return result;
		} catch (InvocationTargetException e) {
			if(e.getTargetException() instanceof CommonDataSearchEmptyException){
				//todo 可以忽略这个错误
			}
			logger.error("任务执行异常:{}", e);
			return Result.failed(100, "任务执行失败" + e.getTargetException().getMessage());
		} catch (Exception e) {
			logger.error("任务执行异常:{}", e);
			return Result.failed(100, "任务执行失败" + e.getMessage());
		}
	}

	private Result checkPublicParams(CommonDataDetail commonDataDetail) {

		if (StringUtils.isBlank(commonDataDetail.getName())) {
			return Result.failed(-1, "接口标识不能为空");
		}
		if (StringUtils.isBlank(commonDataDetail.getAlias())) {
			return Result.failed(-1, "接口名称不能为空");
		}
		if (StringUtils.isBlank(commonDataDetail.getOwner())) {
			return Result.failed(-1, "负责人不能为空");
		}
		return Result.success();
	}

	@Override
	public Result save(CommonDataDetail commonDataDetail) {
		String handlerName = CommonDataHandlerEnum.getHandler(commonDataDetail.getDataSource());
		if (handlerName == null) {
			return Result.failed(2, "不支持的数据源类型");
		}

		Result res = this.checkPublicParams(commonDataDetail);
		if (res.getCode() != 0) {
			return res;
		}
		CommonDataHandler handler = commonDataHandlerMap.get(handlerName);
		Result result = handler.checkParams(commonDataDetail);
		if (result.getCode() != 0) {
			return result;
		}
		CommonDataSummary commonDataSummary = new CommonDataSummary();
		BeanUtils.copyProperties(commonDataDetail, commonDataSummary);
		CommonDataSummary summary = commonDataSummaryDao.getByName(commonDataDetail.getName());
		if (summary != null) {
			return Result.failed(1, "接口名称重复了");
		}
		commonDataSummary.setCreateTime(new Date());
		commonDataSummary.setUpdateTime(new Date());
		commonDataSummaryDao.insertSelective(commonDataSummary);
		commonDataDetail.setCommonDataSummaryId(commonDataSummary.getId());
		handler.save(commonDataDetail);
		return Result.success();
	}

	@Override
	public Result update(CommonDataDetail commonDataDetail) {
		String handlerName;
		String dataSource = commonDataDetail.getDataSource();
		// 判断校验必传参数
		if (dataSource != null) {
			handlerName = CommonDataHandlerEnum.getHandler(commonDataDetail.getDataSource());
			if (handlerName == null) {
				return Result.failed(2, "不支持的数据源类型");
			}
		}
		if (StringUtils.isEmpty(commonDataDetail.getName())) {
			return Result.failed(2, "接口名称不能为空");
		}
		CommonDataSummary commonDataSummary = new CommonDataSummary();
		BeanUtils.copyProperties(commonDataDetail, commonDataSummary);
		commonDataSummary.setId(commonDataDetail.getCommonDataSummaryId());
		commonDataSummary.setUpdateTime(new Date());
		CommonDataSummary summary = commonDataSummaryDao.selectByPrimaryKey(commonDataDetail.getCommonDataSummaryId());
		if (summary == null) {
			return Result.failed(3, "非法的job ID");
		}
		CommonDataSummary repeatSummary = commonDataSummaryDao.getByName(commonDataDetail.getName());
		if (repeatSummary != null && repeatSummary.getId().intValue() != summary.getId()) {
			return Result.failed(1, "名称必须唯一");
		}
		// 提前判断所需的参数是否都有
		Result result = commonDataHandlerMap.get(CommonDataHandlerEnum.getHandler(dataSource))
				.checkParams(commonDataDetail);
		if (result.getCode() != 0) {
			return result;
		}
		// 如果数据源发生了变化，需要重新插入新的数据源，同时删除旧的数据源
		if (StringUtils.isNotEmpty(dataSource) && !summary.getDataSource().equals(dataSource)) {
			commonDataHandlerMap.get(CommonDataHandlerEnum.getHandler(summary.getDataSource()))
					.delete(commonDataSummary.getId());
			commonDataHandlerMap.get(CommonDataHandlerEnum.getHandler(dataSource)).save(commonDataDetail);
		} else {// 否则就是数据源没有发生变化
			CommonDataHandler handler = commonDataHandlerMap
					.get(CommonDataHandlerEnum.getHandler(summary.getDataSource()));
			handler.update(commonDataDetail);
		}
		commonDataSummaryDao.updateByPrimaryKey(commonDataSummary);

		return Result.success();
	}

	@Override
	public SearchResult<CommonDataDetail> query(CommonDataDetail commonDataDetail) {
		SearchResult<CommonDataDetail> result = new SearchResult<CommonDataDetail>();
		result.setPageCount(commonDataDetail.getPageCount());
		result.setCurrentPage(commonDataDetail.getPage());

		List<CommonDataSummary> summaryList = commonDataSummaryDao.query(commonDataDetail);
		if (CollectionUtils.isEmpty(summaryList)) {
			result.setTotal(0);
			return result;
		}
		List<CommonDataDetail> detailList = BeanMapper.mapList(summaryList, CommonDataDetail.class);
		for (int i = 0; i < detailList.size(); i++) {
			detailList.get(i).setCommonDataSummaryId(summaryList.get(i).getId());
		}
		// datasource -> List<CommonDataDetail>
		Map<String, List<CommonDataDetail>> commonDataDetailMap = new HashMap<String, List<CommonDataDetail>>();
		for (CommonDataDetail dataDetail : detailList) {
			if (commonDataDetailMap.get(dataDetail.getDataSource()) == null) {
				commonDataDetailMap.put(dataDetail.getDataSource(), new ArrayList<CommonDataDetail>());
			}
			commonDataDetailMap.get(dataDetail.getDataSource()).add(dataDetail);
		}
		for (Map.Entry<String, CommonDataHandler> handler : commonDataHandlerMap.entrySet()) {
			handler.getValue()
					.fillProperties(commonDataDetailMap.get(CommonDataHandlerEnum.getDataSource(handler.getKey())));
		}

		result.setRows(detailList);
		int total = commonDataSummaryDao.queryCount(commonDataDetail);
		result.setTotal(total);

		return result;
	}

	@Override
	public CommonDataDetail getById(Integer commonDataSummaryId) {
		CommonDataSummary commonDataSummary = commonDataSummaryDao.selectByPrimaryKey(commonDataSummaryId);
		CommonDataDetail commonDataDetail = BeanMapper.map(commonDataSummary, CommonDataDetail.class);
		commonDataDetail.setCommonDataSummaryId(commonDataSummary.getId());
		commonDataHandlerMap.get(CommonDataHandlerEnum.getHandler(commonDataDetail.getDataSource()))
				.fillProperties(Arrays.asList(commonDataDetail));
		return commonDataDetail;
	}

	@Override
	public Result deleteById(Integer commonDataSummaryId) {
		CommonDataSummary commonDataSummary = commonDataSummaryDao.selectByPrimaryKey(commonDataSummaryId);
		// 这里需要获取对应的数据源信息
		if (commonDataSummary != null) {
			commonDataSummaryDao.deleteByPrimaryKey(commonDataSummaryId);
			commonDataHandlerMap.get(CommonDataHandlerEnum.getHandler(commonDataSummary.getDataSource()))
					.delete(commonDataSummaryId);
		}
		return Result.success();
	}

	@Override
	public Result cleanCache(String dataApiName) {
		CommonDataSummary commonDataSummary = commonDataSummaryDao.getByName(dataApiName);
		if (commonDataSummary == null) {
			return Result.failed(1, "该id不存在对应的接口查询");
		}

		// String
		// redisKey=RedisCache.PREFIX+"dataApi"+RedisCache.SPLIT_KEY+dataApiName+"*";

		CommonDataSummary cds = new CommonDataSummary();
		cds.setId(commonDataSummary.getId());
		cds.setUpdateTime(new Date());
		int res = commonDataSummaryDao.updateByPrimaryKeySelective(cds);
		return res > 0 ? Result.success() : (Result.failed(-1, "操作失败,数据库更新记录:" + res));
	}

	private static class CommonDataTimeUsedThread implements Runnable {

		private Logger logger = LoggerFactory.getLogger(this.getClass());

		private String jobName;
		private Map<String, String> params;
		private String remark;
		private Result result;

		public CommonDataTimeUsedThread(String jobName, Map<String, String> params, Result result, String remark) {
			this.jobName = jobName;
			this.params = params;
			this.result = result;
			this.remark = remark;
		}

		@Override
		public void run() {
			logger.info("remark:{}", remark);
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				map.put("jobName", jobName);
				map.put("params", params);
				map.put("sql", URLEncoder.encode(remark.replaceAll("\t", " ").replaceAll("\n", " "), "utf-8"));
				long timeMillis = System.currentTimeMillis();
				map.put("timestamp", timeMillis);
				map.put("inputTime", DateUtil.formatDateByMilliseconds(timeMillis));
				map.put("timeUsed", result.getTimeUsed());
				map.put("code", result.getCode());
				map.put("msg", result.getErrorMsg());


				String json = JSON.toJSONString(map);
				logger.info("json:{}", json);
				logger.info("url: {}", Constant.COMMON_DATA_TIME_USED_DRUID);
				HttpUtil.post(Constant.COMMON_DATA_TIME_USED_DRUID, json);
			} catch (IOException e) {
				logger.error("发送接口耗时信息到druid异常:{}", e);
			}
		}
	}
}
