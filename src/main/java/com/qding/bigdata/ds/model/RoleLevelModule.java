package com.qding.bigdata.ds.model;

public class RoleLevelModule extends BaseModel {
  /**
  * 
  */
  private static final long serialVersionUID = -7099306842621560437L;

  private String id;

  private String roleLevelId;

  private String moduleId;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id == null ? null : id.trim();
  }

  public String getRoleLevelId() {
    return roleLevelId;
  }

  public void setRoleLevelId(String roleLevelId) {
    this.roleLevelId = roleLevelId == null ? null : roleLevelId.trim();
  }

  public String getModuleId() {
    return moduleId;
  }

  public void setModuleId(String moduleId) {
    this.moduleId = moduleId == null ? null : moduleId.trim();
  }

  public RoleLevelModule(String roleLevelId, String moduleId) {
    super();
    this.roleLevelId = roleLevelId;
    this.moduleId = moduleId;
  }

  public RoleLevelModule() {
    super();
  }
  
}
