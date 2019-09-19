package com.qding.bigdata.ds.model;
public class RoleLevel extends Role {
    /**
     *
     */
    private static final long serialVersionUID = -2352872900919788332L;
    private Integer level;
    private String roleId;
    private String cnNme;
    private String enNme;

    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getCnNme() {
		return cnNme;
	}
	public void setCnNme(String cnNme) {
		this.cnNme = cnNme;
	}
	public String getEnNme() {
		return enNme;
	}
	public void setEnNme(String enNme) {
		this.enNme = enNme;
	}
	public RoleLevel(Integer level, String roleId) {
      super();
      this.level = level;
      this.roleId = roleId;
    }
    public RoleLevel() {
      super();
    }


}
