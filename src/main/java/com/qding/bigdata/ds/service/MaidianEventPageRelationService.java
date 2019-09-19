package com.qding.bigdata.ds.service;

import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.model.DsMaidianEventPageRelationship;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/4/1 19:12
 * @description
 */
public interface MaidianEventPageRelationService {

    PageInfo<DsMaidianEventPageRelationship> list(DsMaidianEventPageRelationship param, Integer curPage, Integer pageSize);

    void deleteById(Long id);

    void add(DsMaidianEventPageRelationship event);

    void update(DsMaidianEventPageRelationship event);

    void batchInsert(List<DsMaidianEventPageRelationship> eventPageRelationshipList);
}
