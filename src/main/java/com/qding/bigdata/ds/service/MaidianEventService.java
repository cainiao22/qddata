package com.qding.bigdata.ds.service;

import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.model.DsMaidianEvent;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/3/29 16:01
 * @description
 */
public interface MaidianEventService {

    PageInfo<DsMaidianEvent> list(DsMaidianEvent param, Integer curPage, Integer pageSize);

    void deleteById(Long id);

    void add(DsMaidianEvent event);

    void update(DsMaidianEvent event);

    DsMaidianEvent findById(Long id);

    void batchInsert(List<DsMaidianEvent> maidianEventList) throws Exception;

    DsMaidianEvent findByCode(String code);

    List<DsMaidianEvent> getEventInfoByProductId(Long productId);
	
	List<DsMaidianEvent> queryAll();
}
