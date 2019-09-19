package com.qding.bigdata.ds.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.dao.DsMaidianParamMapper;
import com.qding.bigdata.ds.model.DsMaidianParam;
import com.qding.bigdata.ds.service.MaidianParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/4/2 9:36
 * @description
 */

@Service
public class MaidianParamServiceImpl implements MaidianParamService {

    @Autowired
    DsMaidianParamMapper maidianParamMapper;

    @Override
    public PageInfo<DsMaidianParam> list(DsMaidianParam param, Integer curPage, Integer pageSize) {
        if(curPage != null && pageSize != null) {
            PageHelper.startPage(curPage, pageSize);
        }
        List<DsMaidianParam> list = maidianParamMapper.query(param);
        PageInfo<DsMaidianParam> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void deleteById(Long id) {
        maidianParamMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void add(DsMaidianParam param) {
        maidianParamMapper.insert(param);
    }

    @Override
    public void update(DsMaidianParam param) {
        maidianParamMapper.updateByPrimaryKeyWithBLOBs(param);
    }

    @Override
    public DsMaidianParam findById(Long id) {
        return maidianParamMapper.selectByPrimaryKey(id);
    }

    @Transactional(value = "dstransactionManager", rollbackFor = {Exception.class})
    @Override
    public void batchInsert(List<DsMaidianParam> dsMaidianParamList) {
        if(!CollectionUtils.isEmpty(dsMaidianParamList)){
            maidianParamMapper.deleteAll();
            for (DsMaidianParam dsMaidianParam : dsMaidianParamList) {
                maidianParamMapper.insertSelective(dsMaidianParam);
            }
        }
    }

    @Override
    public List<DsMaidianParam> queryByPageId(String pageId) {
        return maidianParamMapper.queryByPageCode(pageId);
    }

    @Override
    public List<DsMaidianParam> queryByEventId(String eventId) {
        return maidianParamMapper.queryByEventCode(eventId);
    }
}
