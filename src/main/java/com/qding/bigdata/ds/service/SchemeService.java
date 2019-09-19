package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.enums.OperateType;
import com.qding.bigdata.ds.model.SchemeInfo;

/**
 * Created by QDHL on 2017/7/25.
 */
public interface SchemeService  extends  BaseService<SchemeInfo>{
     void executeCreateTableSql(String sql);

     SchemeInfo buidGPSchemeFromMysql(SchemeInfo info, OperateType type);
     /**
      * 查询同步scheme的记录
      * @param id
      * @return
      */
     SchemeInfo queryByID(String id);

     SearchResult<SchemeInfo> queryList(SchemeInfo param);

     boolean hasSchemeRecoder(SchemeInfo info);
      SchemeInfo savaOrUpdate(SchemeInfo info);

}
