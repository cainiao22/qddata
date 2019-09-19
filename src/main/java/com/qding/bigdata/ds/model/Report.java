/**
 * 
 */
package com.qding.bigdata.ds.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wangjunping
 *
 */
public class Report {

  private String id;
  private String title;
  private String dataTable;
  private List<QueryField> queryFieldList;
  private List<GroupField> groupFieldList;
  private List<MeterField> meterFieldList;
  private List<SortField> sortFieldList;
  private  int limit ;

  private Map<String, String> paramMap = new HashMap<String, String>();



   



  public String getId() {
    return id;
  }



  public void setId(String id) {
    this.id = id;
  }



  public String getTitle() {
    return title;
  }



  public void setTitle(String title) {
    this.title = title;
  }



  public String getDataTable() {
    return dataTable;
  }



  public void setDataTable(String dataTable) {
    this.dataTable = dataTable;
  }



  public List<QueryField> getQueryFieldList() {
    return queryFieldList;
  }



  public void setQueryFieldList(List<QueryField> queryFieldList) {
    this.queryFieldList = queryFieldList;
  }



  public List<GroupField> getGroupFieldList() {
    return groupFieldList;
  }



  public void setGroupFieldList(List<GroupField> groupFieldList) {
    this.groupFieldList = groupFieldList;
  }



  public List<MeterField> getMeterFieldList() {
    return meterFieldList;
  }



  public void setMeterFieldList(List<MeterField> meterFieldList) {
    this.meterFieldList = meterFieldList;
  }



  public List<SortField> getSortFieldList() {
    return sortFieldList;
  }



  public void setSortFieldList(List<SortField> sortFieldList) {
    this.sortFieldList = sortFieldList;
  }



  public Map<String, String> getParamMap() {
    return paramMap;
  }



  public void setParamMap(Map<String, String> paramMap) {
    this.paramMap = paramMap;
  }



  public int getLimit() {
    return limit;
  }



  public void setLimit(int limit) {
    this.limit = limit;
  }



  @Override
  public String toString() {
    return JSONObject.toJSONString(this);
  }


}
