package com.qding.bigdata.ds.model;

public class ReportConfig extends BaseModel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  private String name;
  private String config;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getConfig() {
    return config;
  }

  public void setConfig(String config) {
    this.config = config;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }


}
