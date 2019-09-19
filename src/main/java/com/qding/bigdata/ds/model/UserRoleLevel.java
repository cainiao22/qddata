package com.qding.bigdata.ds.model;

public class UserRoleLevel extends BaseModel {
  /**
   * 
   */
  private static final long serialVersionUID = 9206421998603994668L;

  private String id;

  private String roleLevelId;

  private String userId;

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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId == null ? null : userId.trim();
  }

  public UserRoleLevel(String id, String roleLevelId, String userId) {
    super();
    this.id = id;
    this.roleLevelId = roleLevelId;
    this.userId = userId;
  }

  public UserRoleLevel(String roleLevelId, String userId) {
    super();
    this.roleLevelId = roleLevelId;
    this.userId = userId;
  }

  public UserRoleLevel() {
    super();
  }
  
}
