package com.qding.bigdata.ds.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.dao.DsMaidianEventMapper;
import com.qding.bigdata.ds.dao.DsMaidianEventParamRelationshipMapper;
import com.qding.bigdata.ds.dao.DsMaidianParamMapper;
import com.qding.bigdata.ds.model.DsMaidianEvent;
import com.qding.bigdata.ds.model.DsMaidianEventParamRelationship;
import com.qding.bigdata.ds.model.DsMaidianParam;
import com.qding.bigdata.ds.service.MaidianEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/3/29 16:02
 * @description
 */

@Service
public class MaidianEventServiceImpl implements MaidianEventService {

    @Autowired
    DsMaidianEventMapper dsMaidianEventMapper;

    @Autowired
    DsMaidianEventParamRelationshipMapper relationshipMapper;

    @Autowired
    DsMaidianParamMapper maidianParamMapper;

    @Override
    public PageInfo<DsMaidianEvent> list(DsMaidianEvent param, Integer curPage, Integer pageSize) {
        if(curPage != null && pageSize != null){
            PageHelper.startPage(curPage, pageSize);
        }
        List<DsMaidianEvent> list = dsMaidianEventMapper.list(param);
        PageInfo<DsMaidianEvent> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void deleteById(Long id) {
        dsMaidianEventMapper.deleteByPrimaryKey(id);
        relationshipMapper.deleteByEventId(id);
    }

    @Override
    public void add(DsMaidianEvent event) {
        dsMaidianEventMapper.insert(event);
        if(event.getParamIds() != null){
            for (Long id : event.getParamIds()) {
                DsMaidianEventParamRelationship item = new DsMaidianEventParamRelationship();
                item.setEventId(event.getId());
                item.setParamId(id);
                relationshipMapper.insert(item);
            }
        }
    }

    @Override
    public void update(DsMaidianEvent event) {
        dsMaidianEventMapper.updateByPrimaryKeyWithBLOBs(event);
        relationshipMapper.deleteByEventId(event.getId());
        if(event.getParamIds() != null){
            for (Long id : event.getParamIds()) {
                DsMaidianEventParamRelationship item = new DsMaidianEventParamRelationship();
                item.setEventId(event.getId());
                item.setParamId(id);
                relationshipMapper.insertSelective(item);
            }
        }
    }

    @Override
    public DsMaidianEvent findById(Long id) {
        return dsMaidianEventMapper.selectByPrimaryKey(id);
    }

    @Transactional(value = "dstransactionManager", rollbackFor = {Exception.class})
    @Override
    public void batchInsert(List<DsMaidianEvent> maidianEventList) throws Exception {
        if(CollectionUtils.isEmpty(maidianEventList)){
            return;
        }
        dsMaidianEventMapper.deleteAll();
        relationshipMapper.deleteAll();
        for(DsMaidianEvent item : maidianEventList){
            dsMaidianEventMapper.insert(item);
            for(DsMaidianParam param : item.getParamList()){
                DsMaidianParam maidianParam = maidianParamMapper.getByCode(param.getCode());
                if(maidianParam == null){
                    throw new Exception("不存在对应的param:" + param.getCode());
                }
                DsMaidianEventParamRelationship relationship = new DsMaidianEventParamRelationship();
                relationship.setEventId(item.getId());
                relationship.setParamId(maidianParam.getId());
                relationshipMapper.insert(relationship);
            }
        }
    }

    @Override
    public DsMaidianEvent findByCode(String code) {
        return dsMaidianEventMapper.findByCode(code);
    }

    @Override
    public List<DsMaidianEvent> getEventInfoByProductId(Long productId) {
        DsMaidianEvent param = new DsMaidianEvent();
        param.setProductId(productId);
        return dsMaidianEventMapper.list(param);
    }
	
	@Override
    public List<DsMaidianEvent> queryAll() {
        return dsMaidianEventMapper.queryAll();
    }
}
