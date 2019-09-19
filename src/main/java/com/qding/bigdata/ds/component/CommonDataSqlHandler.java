package com.qding.bigdata.ds.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.qding.bigdata.ds.Exception.CommonDataSearchEmptyException;
import com.qding.bigdata.ds.annotation.SQLDataSourceType;
import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.common.PageResult;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.dao.CommonDataSqlDao;
import com.qding.bigdata.ds.enums.SQLDataSourceTypeEnum;
import com.qding.bigdata.ds.model.CommonDataDetail;
import com.qding.bigdata.ds.model.CommonDataSql;
import com.qding.bigdata.ds.model.CommonDataSummary;
import com.qding.bigdata.ds.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by yanpf on 2017/7/27.
 */
@Component
public class CommonDataSqlHandler implements CommonDataHandler<List<Map<String, Object>>>, InitializingBean {



    @Autowired
    CommonDataSqlDao commonDataSqlDao;

    CommonDataSqlDao executorDao;
    @Resource(name = "executorESJdbcTemplate")
    JdbcTemplate   executorESJdbcTemplate;

    private Map<String, Method> methodMap;

    public static CommonDataSqlHandler instance;


    @Resource(name = "sqlSessionExecutor")
    public void initExecutorGPDao(SqlSession sqlSession) {
        this.executorDao = sqlSession.getMapper(CommonDataSqlDao.class);
    }
 
    //这里的#号是特殊字符，key中不可以出现它
    //直接用cacheTime代替updateTime做缓存key的版本号似乎更好
    @Cacheable(value = "redisCache", key = "'dataApi'.concat(':').concat(#commonDataSummary.name).concat(':').concat(#params.toString()).concat(':').concat(#commonDataSummary.updateTime).concat(':').concat(#commonDataSummary.getCacheTime())")
    @Override
    public Result<List<Map<String, Object>>> execute(CommonDataSummary commonDataSummary, Map<String, String> params) throws Exception {
        CommonDataSql commonDataSql = commonDataSqlDao.getBySummaryId(commonDataSummary.getId());
        if (commonDataSql == null) {
            return Result.failed(1, "对应的执行操作不存在");
        }
        String queryContent = commonDataSql.getQueryContent();
        //Result<String> repResult = RegexUtil.replaceDynamicParams(params, queryContent, false);
        Result<String> repResult = FreeMarkerUtil.process(queryContent, params);
        if (repResult.getCode() != 0) {
            return Result.failed(repResult.getCode(), repResult.getErrorMsg());
        }
        queryContent = repResult.getData();
        Method invokeMethod = methodMap.get(commonDataSummary.getDataSource());
        if(invokeMethod == null){
           throw new Exception(("未找到对应的sql处理器:" + commonDataSummary.getDataSource()));
        }
        Object[] realParams = new Object[invokeMethod.getParameterTypes().length];
        if(realParams.length > 0) {
            realParams[0] = queryContent;
            if (realParams.length == 2){
                realParams[1] = params;
            }else if(realParams.length > 2){
                throw new Exception("所需参数过多，无法提供:" + realParams.length);
            }
        }
        Result<List<Map<String, Object>>> result = (Result<List<Map<String, Object>>>) invokeMethod.invoke(this, realParams);
        result.setRemark(queryContent);
        if(CollectionUtils.isEmpty(result.getData())){
            throw new CommonDataSearchEmptyException();
        }
        return result;
    }

    @Override
    public void save(CommonDataDetail commonDataDetail) {
        CommonDataSql commonDataSql = new CommonDataSql();
        BeanUtils.copyProperties(commonDataDetail, commonDataSql);
        commonDataSql.setCreateTime(new Date());
        commonDataSql.setUpdateTime(new Date());
        commonDataSqlDao.insertSelective(commonDataSql);
    }

    @Override
    public void update(CommonDataDetail commonDataDetail) {
        CommonDataSql commonDataSql = new CommonDataSql();
        BeanUtils.copyProperties(commonDataDetail, commonDataSql);
        commonDataSql.setUpdateTime(new Date());
        commonDataSql.setCommonDataSummaryId(commonDataDetail.getCommonDataSummaryId());
        commonDataSqlDao.updateBySummaryId(commonDataSql);
    }

    @Override
    public Result checkParams(CommonDataDetail commonDataDetail) {
        if (StringUtils.isEmpty(commonDataDetail.getQueryContent())) {
            return Result.failed(1, "SQL语句不能为空");
        }
        return Result.success();
    }

    @Override
    public void delete(Integer summaryId) {
        commonDataSqlDao.deleteBySummaryId(summaryId);
    }

    @Override
    public void fillProperties(List<CommonDataDetail> detailList) {
        if (CollectionUtils.isNotEmpty(detailList)) {
            List<CommonDataSql> commonDataSqlList = commonDataSqlDao.getListBySummaryIds(detailList);
            if (CollectionUtils.isEmpty(commonDataSqlList)) {
                return;
            }
            for (CommonDataDetail detail : detailList) {
                for (CommonDataSql sql : commonDataSqlList) {
                    if (sql.getCommonDataSummaryId().intValue() == detail.getCommonDataSummaryId()) {
                        detail.setQueryContent(sql.getQueryContent());
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        methodMap = new HashMap<String, Method>();
        Method[] methods = this.getClass().getDeclaredMethods();
        for(Method method : methods){
            SQLDataSourceType sqlDataSourceType = method.getAnnotation(SQLDataSourceType.class);
            if(sqlDataSourceType != null){
                Method pre = methodMap.put(sqlDataSourceType.value().name, method);
                if(pre != null){
                    throw new Exception("duplicate method to deal with the same sql datasource! which one do you want to use?");
                }
            }
        }
        instance = this;
    }

    @SQLDataSourceType(SQLDataSourceTypeEnum.GPSQL)
    private Result<List<Map<String, Object>>> dealWithGPSQL(String queryContent, Map<String, String> params){
        List<Map<String, Object>> result = executorDao.execute(queryContent);
        if(params.get(Constant.CALCULATEL_TOTAL) != null && "true".equals(params.get(Constant.CALCULATEL_TOTAL))){
            String countSql = SQLCountUtil.getCountSql(queryContent);
            Integer count = executorDao.executeCount(countSql);
            if (count == null) {
                count = 0;
            }
            PageResult<List<Map<String, Object>>> pageResult = new PageResult<List<Map<String, Object>>>();
            pageResult.setCurrentPage(Integer.valueOf(params.get(Constant.CURRENT_PAGE)));
            pageResult.setPageSize(Integer.valueOf(params.get(Constant.PAGE_SIZE)));
            pageResult.setTotal(count);
            pageResult.setData(result);

            return pageResult;
        }
        return Result.success(result);
    }

    @SQLDataSourceType(SQLDataSourceTypeEnum.DRUIDSQL)
    private Result<List<Map<String, Object>>> dealWithDruidSQL(String queryContent) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("query", queryContent);
        String response = HttpClientUtil.doPostWithJSON(Constant.DRUID_QUERY_URL, JSON.toJSONString(params));
        List<Map<String,Object>> listObjectSec = JSONArray.parseObject(response,List.class);
        return Result.success(listObjectSec);
    }
    @SQLDataSourceType(SQLDataSourceTypeEnum.ESSQL)
    private Result<List<Map<String, Object>>> dealWithESSQL(String queryContent, Map<String, String> params) throws Exception {

        PageResult rs = new PageResult();
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = new HashMap<String, Object>();
        ArrayList<HashMap<String, Object>> arrayList = null;

        List<Map<String, Object>> res =new ArrayList<Map<String,Object>>();
        String response = HttpUtil.post(Constant.ES_QUERY_URL, queryContent);
        map = ((HashMap<String, Object>) objectMapper.readValue(response, Map.class));
        if(null!=map && map.size()>0){
            map = ((HashMap<String, Object>)map.get("hits"));
            if(null!=map && map.size()>0){
                arrayList = (ArrayList<HashMap<String, Object>>) map.get("hits");
                if(null!=arrayList && arrayList.size()>0){
                    for (HashMap<String, Object>  map1:arrayList) {
                        res.add((HashMap<String, Object>) map1.get("_source"));
                    }
                }
                //总记录条数
                Integer total = 0;
                //每页显示条数
                Integer pageCount = 0;
                //偏移量
                Integer offset = 0;
                if(null != res && res.size()>0){
                    pageCount = Integer.valueOf(params.get("pageCount"));
                    offset = Integer.valueOf(params.get("offset"));
                    total = (Integer)map.get("total");
                    rs.setTotal(total);
                    if(null != total && pageCount != null){
                        rs.setTotalPage(PageUtil.getTotalCount(total,pageCount));
                    }
                    if(null != offset && pageCount != null) {
                        rs.setCurrentPage(PageUtil.getCurrentPage(offset, pageCount));
                    }
                    rs.setPageSize(res.size());
                    rs.setPageCount(pageCount);
                    rs.setData(res);
                }
            }
        }
        return rs;
    }

    public CommonDataSql getBySummaryName(String name){
        return commonDataSqlDao.getBySummaryName(name);
    }
}
