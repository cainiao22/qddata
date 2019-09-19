package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.CountData;
import com.qding.bigdata.ds.model.SystemWebAccess;

import java.util.List;

/**
 * Created by lzs on 2018/7/4.
 */
public interface SystemWebAccessDao {
   public List<SystemWebAccess> listBySource(CountData data);
   public List<SystemWebAccess> listAll(CountData data);
}
