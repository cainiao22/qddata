package com.qding.bigdata.ds.service;

import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.model.DsMaidianParam;
import com.qding.bigdata.ds.model.DsMaidianSkip;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/4/1 17:35
 * @description
 */
public interface MaidianSkipService {

    void add(DsMaidianSkip dsMaidianSkip) throws Exception;

    PageInfo<DsMaidianSkip> list(DsMaidianSkip param, Integer curPage, Integer pageSize);

    void update(DsMaidianSkip param) throws Exception;

    void deleteById(Long id);

    DsMaidianSkip findById(Long id);

    void batchInsert(List<DsMaidianSkip> maidianSkipList) throws Exception;
}
