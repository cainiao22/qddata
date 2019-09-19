package com.qding.bigdata.ds.model;

public class Module extends BaseModel {

  /**
   *
   */
  private static final long serialVersionUID = 5307179440607522919L;
  private String moduleName;
  private String moduleUrl;
  private String roleLevelsStr;
  private String pid;
  private Integer level;
  private Integer type;
  private Integer sortNo;
  private String outerUrl;

  public String getModuleName() {
    return moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public String getModuleUrl() {
    return moduleUrl;
  }

  public void setModuleUrl(String moduleUrl) {
    this.moduleUrl = moduleUrl;
  }



  public String getRoleLevelsStr() {
    return roleLevelsStr;
  }

  public void setRoleLevelsStr(String roleLevelsStr) {
    this.roleLevelsStr = roleLevelsStr;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

 

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getOuterUrl() {
    return outerUrl;
  }

  public void setOuterUrl(String outerUrl) {
    this.outerUrl = outerUrl;
  }

  public Integer getSortNo() {
    return sortNo;
  }

  public void setSortNo(Integer sortNo) {
    this.sortNo = sortNo;
  }


}
