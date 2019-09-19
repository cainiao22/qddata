package com.qding.bigdata.ds.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.dao.DsMaidianPageMapper;
import com.qding.bigdata.ds.dao.DsMaidianPageParamRelationshipMapper;
import com.qding.bigdata.ds.dao.DsMaidianParamMapper;
import com.qding.bigdata.ds.model.DsMaidianPage;
import com.qding.bigdata.ds.model.DsMaidianPageParamRelationship;
import com.qding.bigdata.ds.model.DsMaidianParam;
import com.qding.bigdata.ds.service.MaidianPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/3/29 10:47
 * @description
 */

@Service
public class MaidianPageServiceImpl implements MaidianPageService {

    @Autowired
    DsMaidianPageMapper dsMaidianPageMapper;

    @Autowired
    DsMaidianPageParamRelationshipMapper relationshipMapper;

    @Autowired
    DsMaidianParamMapper maidianParamMapper;

    @Override
    public PageInfo<DsMaidianPage> list(DsMaidianPage param, Integer curPage, Integer pageSize) {
        if(curPage != null && pageSize != null) {
            PageHelper.startPage(curPage, pageSize);
        }
        List<DsMaidianPage> list = dsMaidianPageMapper.query(param);
        PageInfo<DsMaidianPage> pageInfo=new PageInfo<DsMaidianPage>(list);
        return pageInfo;
    }

    @Override
    public void deleteById(Long id) {
        dsMaidianPageMapper.deleteByPrimaryKey(id);
        relationshipMapper.deleteByPageId(id);
    }

    @Override
    public void add(DsMaidianPage page) {
        dsMaidianPageMapper.insertSelective(page);
        Long[] paramIds = page.getParamIds();
        if(paramIds != null){
            for (Long id : paramIds) {
                DsMaidianPageParamRelationship item = new DsMaidianPageParamRelationship();
                item.setPageId(page.getId());
                item.setParamId(id);
                relationshipMapper.insert(item);
            }
        }
    }

    @Override
    public void update(DsMaidianPage page) {
        dsMaidianPageMapper.updateByPrimaryKeyWithBLOBs(page);
        Long[] paramIds = page.getParamIds();
        relationshipMapper.deleteByPageId(page.getId());
        if(paramIds != null){
            for (Long id : paramIds) {
                DsMaidianPageParamRelationship item = new DsMaidianPageParamRelationship();
                item.setPageId(page.getId());
                item.setParamId(id);
                relationshipMapper.insert(item);
            }
        }
    }

    @Override
    public DsMaidianPage findById(Long id) {
        return dsMaidianPageMapper.selectByPrimaryKey(id);
    }

    @Transactional(value = "dstransactionManager", rollbackFor = {Exception.class})
    @Override
    public void batchInsert(List<DsMaidianPage> maidianPageList) throws Exception {
        if(CollectionUtils.isEmpty(maidianPageList)){
            return;
        }
        dsMaidianPageMapper.deleteAll();
        relationshipMapper.deleteAll();
        for(DsMaidianPage item : maidianPageList){
            dsMaidianPageMapper.insertSelective(item);
            for(DsMaidianParam param : item.getParamList()){
                DsMaidianParam maidianParam = maidianParamMapper.getByCode(param.getCode());
                if(maidianParam == null){
                    throw new Exception("不存在对应的param:" + param.getCode());
                }
                DsMaidianPageParamRelationship relationship = new DsMaidianPageParamRelationship();
                relationship.setPageId(item.getId());
                relationship.setParamId(maidianParam.getId());
                relationshipMapper.insertSelective(relationship);
            }
        }
    }

    @Override
    public DsMaidianPage findByCode(String code) {
        return dsMaidianPageMapper.getMaidianPageByCode(code);
    }

    @Override
    public List<DsMaidianPage> queryAll() {
        return dsMaidianPageMapper.queryAll();
    }

    @Override
    public List<DsMaidianPage> getPageInfoByProductId(Long productId) {
        return dsMaidianPageMapper.queryByProductId(productId);
    }
	
	@Override
    public List<DsMaidianPage> query(DsMaidianPage page) {
        List<DsMaidianPage> list = dsMaidianPageMapper.query(page);
        return list;
    }
}
