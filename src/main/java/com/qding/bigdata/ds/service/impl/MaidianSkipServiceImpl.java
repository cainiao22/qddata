package com.qding.bigdata.ds.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.dao.DsMaidianPageMapper;
import com.qding.bigdata.ds.dao.DsMaidianSkipMapper;
import com.qding.bigdata.ds.model.DsMaidianPage;
import com.qding.bigdata.ds.model.DsMaidianSkip;
import com.qding.bigdata.ds.service.MaidianSkipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/4/1 17:36
 * @description
 */

@Service
public class MaidianSkipServiceImpl implements MaidianSkipService {

    @Autowired
    DsMaidianSkipMapper maidianSkipMapper;

    @Autowired
    DsMaidianPageMapper maidianPageMapper;

    @Override
    public void add(DsMaidianSkip dsMaidianSkip) throws Exception {
        maidianSkipMapper.insertSelective(dsMaidianSkip);
    }

    @Override
    public PageInfo<DsMaidianSkip> list(DsMaidianSkip param, Integer curPage, Integer pageSize) {
        if(curPage != null && pageSize != null){
            PageHelper.startPage(curPage, pageSize);
        }
        List<DsMaidianSkip> list = maidianSkipMapper.query(param);
        PageInfo<DsMaidianSkip> pageInfo=new PageInfo<DsMaidianSkip>(list);
        return pageInfo;
    }

    @Override
    public void update(DsMaidianSkip param) throws Exception {
        maidianSkipMapper.updateByPrimaryKey(param);
    }

    @Override
    public void deleteById(Long id) {
        maidianSkipMapper.deleteByPrimaryKey(id);
    }

    @Override
    public DsMaidianSkip findById(Long id) {
        return maidianSkipMapper.selectByPrimaryKey(id);
    }


    @Transactional(value = "dstransactionManager", rollbackFor = {Exception.class})
    @Override
    public void batchInsert(List<DsMaidianSkip> maidianSkipList) throws Exception {
        if(CollectionUtils.isEmpty(maidianSkipList)){
            return;
        }
        maidianSkipMapper.deleteAll();
        for(DsMaidianSkip maidianSkip : maidianSkipList){
            maidianSkipMapper.insert(maidianSkip);
        }
    }
}
