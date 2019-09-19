package com.qding.bigdata.ds.component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qding.bigdata.ds.model.CommonDataSummary;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.dao.CommonDataRedisDao;
import com.qding.bigdata.ds.enums.RedisDataTypeEnum;
import com.qding.bigdata.ds.model.CommonDataDetail;
import com.qding.bigdata.ds.model.CommonDataRedis;
import com.qding.bigdata.ds.util.BeanMapper;
import com.qding.bigdata.ds.util.RegexUtil;

/**
 * Created by yanpf on 2017/8/7.
 */

@Component
public class CommonDataRedisHandler implements CommonDataHandler<Object> {

    @Autowired
    private RedisDataTypeHandler redisDataTypeHandler;

    @Autowired
    private CommonDataRedisDao commonDataRedisDao;

    @Override
    public Result<Object> execute(CommonDataSummary commonDataSummary, Map<String, String> params) {
        CommonDataRedis commonDataRedis = commonDataRedisDao.getBySummaryId(commonDataSummary.getId());
        if (commonDataRedis == null) {
            return Result.failed(1, "对应的执行操作不存在");
        }
        String dataType = commonDataRedis.getDataType();
        String key = commonDataRedis.getKey();
        Result<String> repResult = RegexUtil.replaceDynamicParams(params, key, false);
        if (repResult.getCode() != 0) {
            return Result.failed(repResult.getCode(), repResult.getErrorMsg());
        }
        key = repResult.getData();
        List<String> fieldList = null;
        String fields = commonDataRedis.getFields();
        if (fields != null) {
            repResult = RegexUtil.replaceDynamicParams(params, fields, false);
            if (repResult.getCode() != 0) {
                return Result.failed(repResult.getCode(), repResult.getErrorMsg());
            }

            fieldList = Arrays.asList(repResult.getData().split(","));
        }
        //如果新增了redis的处理类型且需要传递其他处理参数，直接在这后面添加就可以了
        return redisDataTypeHandler.invoke(dataType, key, fieldList, null);
    }

    @Override
    public void save(CommonDataDetail commonDataDetail) {
        CommonDataRedis commonDataRedis = BeanMapper.map(commonDataDetail, CommonDataRedis.class);
        if(null!=commonDataRedis){
            trimParams(commonDataRedis);
            commonDataRedis.setCreateTime(new Date());
            commonDataRedis.setUpdateTime(new Date());
        }
        commonDataRedisDao.insertSelective(commonDataRedis);
    }

    /**
     * 对入参进行规范处理
     * @param commonDataRedis
     */
    private void trimParams(CommonDataRedis commonDataRedis) {
        if(null != commonDataRedis && commonDataRedis.getKey() != null){
            commonDataRedis.setKey(commonDataRedis.getKey().trim());
        }
        if(null != commonDataRedis && commonDataRedis.getFields() != null){
            String trim = commonDataRedis.getFields().trim();
            if(null!= trim && StringUtils.isNotEmpty(trim)){
                commonDataRedis.setFields(trim.replace(" ", "").replace("\t", ""));
            }
        }
    }

    @Override
    public void update(CommonDataDetail commonDataDetail) {
        CommonDataRedis commonDataRedis = BeanMapper.map(commonDataDetail, CommonDataRedis.class);
        if(null != commonDataRedis){
            trimParams(commonDataRedis);
            commonDataRedis.setUpdateTime(new Date());
        }
        commonDataRedisDao.updateBySummaryId(commonDataRedis);
    }

    @Override
    public Result checkParams(CommonDataDetail commonDataDetail) {
        if (StringUtils.isBlank(commonDataDetail.getDataType())) {
            return Result.failed(1, "数据类型不能为空");
        }
        if (RedisDataTypeEnum.getByName(commonDataDetail.getDataType()) == null) {
            return Result.failed(2, "不支持的redis数据类型");
        }
        if (StringUtils.isBlank(commonDataDetail.getKey())) {
            return Result.failed(3, "键值不能为空");
        }
        return Result.success();
    }

    @Override
    public void delete(Integer summaryId) {
        commonDataRedisDao.deleteBySummaryId(summaryId);
    }

    @Override
    public void fillProperties(List<CommonDataDetail> detailList) {
        if (CollectionUtils.isNotEmpty(detailList)) {
            List<CommonDataRedis> commonDataSqlList = commonDataRedisDao.getListBySummaryIds(detailList);
            if (CollectionUtils.isEmpty(commonDataSqlList)) {
                return;
            }
            for (CommonDataDetail detail : detailList) {
                for (CommonDataRedis redis : commonDataSqlList) {
                    if (redis.getCommonDataSummaryId().intValue() == detail.getCommonDataSummaryId()) {
                        detail.setKey(redis.getKey());
                        detail.setFields(redis.getFields());
                        detail.setDataType(redis.getDataType());
                        break;
                    }
                }
            }
        }
    }
}
