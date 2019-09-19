package com.qding.bigdata.ds.service;

import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.model.DsMaidianParam;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/4/2 9:35
 * @description
 */
public interface MaidianParamService {

    PageInfo<DsMaidianParam> list(DsMaidianParam param, Integer curPage, Integer pageSize);

    void deleteById(Long id);

    void add(DsMaidianParam param);

    void update(DsMaidianParam param);

    DsMaidianParam findById(Long id);

    void batchInsert(List<DsMaidianParam> dsMaidianParamList);

    List<DsMaidianParam> queryByPageId(String pageId);

    List<DsMaidianParam> queryByEventId(String eventId);

}
