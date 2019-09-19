package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.annotation.DynamicDataSource;
import com.qding.bigdata.ds.aop.dynamicsource.DataSourceContextHolder;
import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.DIMProjectDao;
import com.qding.bigdata.ds.dao.PortraitDictionariesDao;
import com.qding.bigdata.ds.model.DIMProject;
import com.qding.bigdata.ds.model.PortraitDictionaries;
import com.qding.bigdata.ds.model.PortraitTag;
import com.qding.bigdata.ds.service.PortraitDictionariesService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/21 0021.
 */
@Service
public class PortraitDictionariesServiceImpl extends BaseServiceImpl<PortraitDictionaries> implements PortraitDictionariesService {

    @Autowired
    private PortraitDictionariesDao portraitDictionariesDao;


    @Override
    public BaseDao<PortraitDictionaries> getDao() {
        return portraitDictionariesDao;
    }

    private DIMProjectDao dimProjectDao;
    @Resource(name = "dynamicSqlSession")
    public void initDao(SqlSession sqlSession) {
        DataSourceContextHolder.setDataSource("databus");
        this.dimProjectDao = sqlSession.getMapper(DIMProjectDao.class);
        DataSourceContextHolder.setDataSource("");
    }

    @Override
    @DynamicDataSource("databus")
    public List<DIMProject> getDimProjectCity() {
        List<DIMProject> cityList = dimProjectDao.getCity();

        return cityList;
    }

    @Override
    @DynamicDataSource("databus")
    public List<DIMProject> getDimProjectProperty() {
        List<DIMProject> cityList = dimProjectDao.getProperty();
        return cityList;
    }

    @Override
    @DynamicDataSource("databus")
    public List<DIMProject> getDimProjectCommunity() {
        List<DIMProject> cityList = dimProjectDao.getCommunity();
        return cityList;
    }

    public void addDinctionaries(List<DIMProject> ldm,PortraitTag portraitTag){
        List<PortraitDictionaries> pdList = pdList = new ArrayList<PortraitDictionaries>();
        if("city".equals(portraitTag.getTag())){
            for (int j = 0; j < ldm.size(); j++) {
                PortraitDictionaries  pd = new PortraitDictionaries();
                pd.setExample_id(ldm.get(j).getRegionId());
                pd.setExample_name(ldm.get(j).getRegionName());
                pd.setSource(portraitTag.getTag());
                pdList.add(pd);
            }
        }else if("community".equals(portraitTag.getTag())){
            for (int j = 0; j < ldm.size(); j++) {
                PortraitDictionaries  pd = new PortraitDictionaries();
                pd.setExample_id(ldm.get(j).getProjectId());
                pd.setExample_name(ldm.get(j).getProjectName());
                pd.setSource(portraitTag.getTag());
                pdList.add(pd);
            }
        }else if("property".equals(portraitTag.getTag())){
            for (int j = 0; j < ldm.size(); j++) {
                PortraitDictionaries  pd = new PortraitDictionaries();
                pd.setExample_id(ldm.get(j).getPropertyinfoId());
                pd.setExample_name(ldm.get(j).getPropertyinfoName());
                pd.setSource(portraitTag.getTag());
                pdList.add(pd);
            }
        }
        PortraitDictionaries  pd = new PortraitDictionaries();
        pd.setExample_id("-1");
        pd.setExample_name("无标签");
        pd.setSource(portraitTag.getTag());
        pdList.add(pd);
        if (pdList.size()>0) {
            portraitTag.setLpd(pdList);
        }
    }

}
