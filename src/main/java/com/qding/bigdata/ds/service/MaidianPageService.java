package com.qding.bigdata.ds.service;

import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.model.DsMaidianPage;

import java.util.List;


/**
 * @author yanpf
 * @date 2019/3/28 17:42
 * @description
 */
public interface MaidianPageService {

    PageInfo<DsMaidianPage> list(DsMaidianPage param, Integer curPage, Integer pageSize);

    void deleteById(Long id);

    void add(DsMaidianPage page);

    void update(DsMaidianPage page);

    DsMaidianPage findById(Long id);

    void batchInsert(List<DsMaidianPage> maidianPageList) throws Exception;

    DsMaidianPage findByCode(String code);

    List<DsMaidianPage> queryAll();

    List<DsMaidianPage> getPageInfoByProductId(Long productId);
	
	List<DsMaidianPage> query(DsMaidianPage page);
}
