package com.qding.bigdata.ds.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.dao.DsMaidianEventPageRelationshipMapper;
import com.qding.bigdata.ds.model.DsMaidianEventPageRelationship;
import com.qding.bigdata.ds.service.MaidianEventPageRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/4/1 19:14
 * @description
 */

@Service
public class MaidianEventPageRelationServiceImpl implements MaidianEventPageRelationService {

    @Autowired
    DsMaidianEventPageRelationshipMapper maidianEventPageRelationshipMapper;

    @Override
    public PageInfo<DsMaidianEventPageRelationship> list(DsMaidianEventPageRelationship param, Integer curPage, Integer pageSize) {
        if(curPage != null && pageSize != null) {
            PageHelper.startPage(curPage, pageSize);
        }
        List<DsMaidianEventPageRelationship> list = maidianEventPageRelationshipMapper.query(param);
        PageInfo<DsMaidianEventPageRelationship> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void deleteById(Long id) {
        maidianEventPageRelationshipMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void add(DsMaidianEventPageRelationship event) {
        maidianEventPageRelationshipMapper.insert(event);
    }

    @Override
    public void update(DsMaidianEventPageRelationship event) {
        maidianEventPageRelationshipMapper.updateByPrimaryKey(event);
    }

    @Transactional(value = "dstransactionManager", rollbackFor = {Exception.class})
    @Override
    public void batchInsert(List<DsMaidianEventPageRelationship> eventPageRelationshipList) {
        if(!CollectionUtils.isEmpty(eventPageRelationshipList)){
             maidianEventPageRelationshipMapper.deleteAll();
            for (DsMaidianEventPageRelationship dsMaidianParam : eventPageRelationshipList) {
                maidianEventPageRelationshipMapper.insertSelective(dsMaidianParam);
            }
        }
    }
}
