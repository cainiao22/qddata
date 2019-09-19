package com.qding.bigdata.ds.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Dimension {
  private String table;
  private String idColumn;
  private String nameColumn;
  
  private Map<String,String> mapData=new LinkedHashMap<String, String>();
  
  
  public Map<String, String> getMapData() {
    return mapData;
  }
  public void setMapData(Map<String, String> mapData) {
    this.mapData = mapData;
  }
  public String getTable() {
    return table;
  }
  public void setTable(String table) {
    this.table = table;
  }
  public String getIdColumn() {
    return idColumn;
  }
  public void setIdColumn(String idColumn) {
    this.idColumn = idColumn;
  }
  public String getNameColumn() {
    return nameColumn;
  }
  public void setNameColumn(String nameColumn) {
    this.nameColumn = nameColumn;
  }
  public Dimension() {
    super();
  }
  public Dimension(String table, String idColumn, String nameColumn) {
    super();
    this.table = table;
    this.idColumn = idColumn;
    this.nameColumn = nameColumn;
  }
  

}
