package com.qding.bigdata.ds.model;

import java.util.List;

public class User extends BaseModel {

  private static final long serialVersionUID = 4634972749267003018L;

  private String userName;
  private String password;
  private String newPassword;
  private String realName;
  private String rolesStr;
  private List<Role> roles;

  /**
   * 用户手机号
   */
  private String mobile;

  /**
   * 用户来源 (0.compass/1.boss/2.mis)
   */
  private Short userSource;
 

public String getNewPassword() {
	return newPassword;
}

public void setNewPassword(String newPassword) {
	this.newPassword = newPassword;
}

public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getRolesStr() {
    return rolesStr;
  }

  public void setRolesStr(String rolesStr) {
    this.rolesStr = rolesStr;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  /**
   * 获取用户手机号
   * @return
   */
  public String getMobile() {
    return mobile;
  }

  /**
   * 设置用户手机号
   * @param mobile
   */
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  /**
   * 获取用户来源
   * @return
   */
  public Short getUserSource() {
    return userSource;
  }

  /**
   * 设置用户来源
   * @param userSource
   */
  public void setUserSource(Short userSource) {
    this.userSource = userSource;
  }
}
