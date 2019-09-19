package com.qding.bigdata.ds.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Joiner;
import com.qding.bigdata.ds.VO.AnalysisVO;
import com.qding.bigdata.ds.VO.PathAnalysisVO;
import com.qding.bigdata.ds.VO.PathNodeVO;
import com.qding.bigdata.ds.annotation.DynamicDataSource;
import com.qding.bigdata.ds.aop.dynamicsource.DataSourceContextHolder;
import com.qding.bigdata.ds.dao.GCTFunnelDao;
import com.qding.bigdata.ds.dao.GCTPagePathDao;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.service.GuancetaiPathService;
import com.qding.bigdata.ds.service.MaidianEventService;
import com.qding.bigdata.ds.service.MaidianPageService;
import com.qding.bigdata.ds.service.UserProfileV2Service;
import com.qding.bigdata.ds.util.EsInstance;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.InternalCardinality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GuancetaiPathServiceImpl implements GuancetaiPathService {

    @Autowired
    GCTFunnelDao gctFunnelDao;
    @Autowired
    UserProfileV2Service userProfileV2Service;
    @Autowired
    MaidianPageService maidianPageService;
    @Autowired
    MaidianEventService maidianEventService;

    GCTPagePathDao gctPagePathDao;
    @Resource(name = "dynamicSqlSession")
    public void initDao(SqlSession sqlSession) {
        DataSourceContextHolder.setDataSource("databus");
        this.gctPagePathDao = sqlSession.getMapper(GCTPagePathDao.class);
        DataSourceContextHolder.clearDataSource();
    }
    @Override
    @DynamicDataSource("databus")
    public List<PathNodeVO> getNode(GuancetaiPagePathParam guancetaiPagePathParam) {
        List<GCTPagePath> allNode = gctPagePathDao.getAllNode(guancetaiPagePathParam);
        List<PathNodeVO> resList = new ArrayList<>();
        if(allNode.size()>0){
            for (GCTPagePath gctPagePath : allNode) {
                resList.add(new PathNodeVO(gctPagePath.getPi(),gctPagePath.getPiName()));
            }
        }
        return resList;
    }

    /**
     *
     * @param guancetaiPagePathParam
     * @return
     */
    @Override
    @DynamicDataSource("databus")
    public PathAnalysisVO getPagePathTree(GuancetaiPagePathParam guancetaiPagePathParam) {
        List<GCTPagePath> pagePathList = gctPagePathDao.getPagePath(guancetaiPagePathParam);
        PathAnalysisVO pathAnalysisVO = new PathAnalysisVO(){};
        pathAnalysisVO.setName(guancetaiPagePathParam.getNodeName());
        getTree(4,guancetaiPagePathParam.getNodeID(),pagePathList,pathAnalysisVO.getChildren());
        return pathAnalysisVO;
    }

    @Override
    public List<AnalysisVO> getBehaviorAnalysis(GuancetaiBehaviorParam guancetaiBehaviorParam) {
        String analysisType = guancetaiBehaviorParam.getAnalysisType();
        TransportClient client = EsInstance.getInstance();

        SearchRequestBuilder srb = null;
        Map<String, String> pageMap = null;
        Map<String, String> evenMap = null;
        if("pageAnalysis".equals(analysisType)){
            srb = client.prepareSearch(QDING_GCT_PAGEPATH_DETAIL);
            List<DsMaidianPage> dsMaidianPages = maidianPageService.queryAll();
            if(dsMaidianPages != null){
                pageMap = dsMaidianPages.stream().collect(Collectors.toMap(DsMaidianPage::getCode, DsMaidianPage::getName, (k1, k2) -> k1));
            }
        }else if("eventAnalysis".equals(analysisType)){
            srb = client.prepareSearch(QDING_GCT_EVENTPATH_DETAIL);
            List<DsMaidianEvent> dsMaidianEvens = maidianEventService.queryAll();
            if(dsMaidianEvens != null){
                evenMap = dsMaidianEvens.stream().collect(Collectors.toMap(DsMaidianEvent::getCode, DsMaidianEvent::getName, (k1, k2) -> k1));
            }
        }else{
            return null;
        }

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(QueryBuilders.rangeQuery("dt").from(guancetaiBehaviorParam.getStartDate(), true).to(guancetaiBehaviorParam.getEndDate(), true));
        boolBuilder.must(QueryBuilders.termsQuery("lse",guancetaiBehaviorParam.getProductId().toString()));
//        端
        if(StringUtils.isNotEmpty(guancetaiBehaviorParam.getDeviceType()) && !"all".equals(guancetaiBehaviorParam.getDeviceType())){
            boolBuilder.must(QueryBuilders.termsQuery("source", guancetaiBehaviorParam.getDeviceType()));
        }
//        指定页面
        if(StringUtils.isNotEmpty(guancetaiBehaviorParam.getEndPi())){
            BoolQueryBuilder wildcardQuery = QueryBuilders.boolQuery();
            wildcardQuery.should(QueryBuilders.wildcardQuery("pageids","*"+ guancetaiBehaviorParam.getEndPi() +"*"));
            boolBuilder.must(wildcardQuery);
        }
        String ids = guancetaiBehaviorParam.getIds();
        String pathName = "pi%d";
        Integer pathNum = 0;
        if(StringUtils.isNotEmpty(ids) && ids.split(",").length < 11){
            String[] idArr = ids.split(",");
            pathNum = idArr.length;
            for (int i = 0; i < pathNum; i++) {
                boolBuilder.must(QueryBuilders.termsQuery(String.format(pathName, i+1), idArr[i]));
            }
        }
        List<? extends Terms.Bucket> bucketByOneAgg = userProfileV2Service.getBucketByOneAgg2(srb, String.format(pathName, pathNum+1), null,boolBuilder);
        List<AnalysisVO> ret = new ArrayList<>();
        if(null!=bucketByOneAgg && !bucketByOneAgg.isEmpty()) {
            if(pageMap != null){
                for (Terms.Bucket bucket : bucketByOneAgg) {
                    ret.add(new AnalysisVO(pageMap.get(bucket.getKeyAsString()),bucket.getKeyAsString(),bucket.getDocCount()));
                }
            }else if(evenMap != null){
                for (Terms.Bucket bucket : bucketByOneAgg) {
                    ret.add(new AnalysisVO(evenMap.get(bucket.getKeyAsString()),bucket.getKeyAsString(),bucket.getDocCount()));
                }
            }

        }
        return ret;
    }

    @Override
    public ArrayList<AnalysisVO> getFunnelAnalysis(GuancetaiFunnelParam guancetaiFunnelParam) {
        ArrayList<AnalysisVO> analysisVOArrayList = new ArrayList<AnalysisVO>();
        Map<String, String> pageMap = null;
        List<DsMaidianPage> dsMaidianPages = maidianPageService.queryAll();
        if(dsMaidianPages != null){
            pageMap = dsMaidianPages.stream().collect(Collectors.toMap(DsMaidianPage::getCode, DsMaidianPage::getName, (k1, k2) -> k1));
        }
        TransportClient client = EsInstance.getInstance();
        SearchRequestBuilder srb = client.prepareSearch(QDING_GCT_PAGEPATH_DETAIL);
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(QueryBuilders.rangeQuery("dt").from(guancetaiFunnelParam.getStartDate(), true).to(guancetaiFunnelParam.getEndDate(), true));
//        端
        if(StringUtils.isNotEmpty(guancetaiFunnelParam.getDeviceType()) && !"all".equals(guancetaiFunnelParam.getDeviceType())){
            boolBuilder.must(QueryBuilders.termsQuery("source", guancetaiFunnelParam.getDeviceType()));
        }
        String codes = guancetaiFunnelParam.getFunnelPath();
        String pathName = "pi%d";
        Integer pathNum = 1;
        String wildcardStr = "*";
        if(StringUtils.isNotEmpty(codes) && codes.split(",").length < 11){
            String[] codeArr = codes.split(",");
            pathNum = codeArr.length;
//            去重DI
            CardinalityAggregationBuilder cardinalityAggregationBuilder = AggregationBuilders.cardinality("di")
                    .field("di").precisionThreshold(10000);
            SearchRequestBuilder searchRequestBuilder = srb.addAggregation(cardinalityAggregationBuilder);
            for (int i = 0; i < pathNum; i++) {
                wildcardStr += codeArr[i]+"*";
                BoolQueryBuilder wildcardQuery = QueryBuilders.boolQuery();
                    wildcardQuery.should(QueryBuilders.wildcardQuery("pageids",wildcardStr));
                boolBuilder.must(wildcardQuery);

                SearchResponse sr = searchRequestBuilder.setQuery(QueryBuilders.boolQuery()
                        .must(boolBuilder))
                        .setSize(0)
                        .execute()
                        .actionGet();
                Map<String, Aggregation> aggregationMap = sr.getAggregations().asMap();
                InternalCardinality aggregation = (InternalCardinality) aggregationMap.get("di");
                long value = aggregation.getValue();
                //code转为中文名称
                analysisVOArrayList.add(new AnalysisVO(pageMap.get(codeArr[i]),codeArr[i],value));
            }
        }
        return analysisVOArrayList;
    }

    @Override
    public List<GCTFunnel> queryFunnelListByProductID( GCTFunnel gctFunnel) {
        return gctFunnelDao.query(gctFunnel);
    }

    @Override
    public int insertFunnel(GCTFunnel gctFunnel) {
        return gctFunnelDao.insert(gctFunnel);
    }

    @Override
    public int updateFunnel(GCTFunnel gctFunnel) {
        return gctFunnelDao.update(gctFunnel);
    }

    @Override
    public Map<String, String>  getMaidianPageByProductID(DsMaidianPage page){
        Map<String, String> pageMap = null;
        List<DsMaidianPage> dsMaidianPages = maidianPageService.query(page);
        if(dsMaidianPages != null){
            pageMap = dsMaidianPages.stream().collect(Collectors.toMap(DsMaidianPage::getCode, DsMaidianPage::getName, (k1, k2) -> k1));
        }
        return pageMap;
    }

    /**
     * 由子到父遍历
     * @param level 往下找几层
     * @param node
     * @param pagePathList
     * @return
     */
    private void getTree(Integer level,String node,List<GCTPagePath> pagePathList,List<PathAnalysisVO> children){
        if(StringUtils.isEmpty(node) || level==0 || pagePathList.size()==0){
            return;
        }
        for (GCTPagePath gctPagePath : pagePathList) {
            if(node.equals(gctPagePath.getPi())){
                PathAnalysisVO path = new PathAnalysisVO(){};
                path.setName(gctPagePath.getRpiName());
                path.setValue(gctPagePath.getUv());
                this.getTree(level-1,gctPagePath.getRpi(),pagePathList,path.getChildren());
                children.add(path);
            }
        }
    }
}
