package com.qding.bigdata.ds.dao;


import java.util.List;

import com.qding.bigdata.ds.model.MetaTableMonitor;

public interface MetaTableMonitorDao extends BaseDao<MetaTableMonitor> {

    /**
     * 根据metableId获取对于的规则列表
     * @param metaTableId
     * @return
     */
    List<MetaTableMonitor> getListByMetaTableId(String metaTableId);
}