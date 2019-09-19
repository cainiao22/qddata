package com.qding.bigdata.ds.model;

import java.util.List;
public class Role extends BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = -7983442664191357132L;
    private String cnName;
    private String enName;
    private String modulesStr;
    private String usersStr;
    private List<Module> modules;
    private List<RoleLevel> subRoleLevels;
  
    public String getCnName() {
      return cnName;
    }
    public void setCnName(String cnName) {
      this.cnName = cnName;
    }
    public String getEnName() {
      return enName;
    }
    public void setEnName(String enName) {
      this.enName = enName;
    }
    public String getModulesStr() {
        return modulesStr;
    }
    public void setModulesStr(String modulesStr) {
        this.modulesStr = modulesStr;
    }
    public String getUsersStr() {
        return usersStr;
    }
    public void setUsersStr(String usersStr) {
        this.usersStr = usersStr;
    }
    public List<Module> getModules() {
        return modules;
    }
    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
    public List<RoleLevel> getSubRoleLevels() {
        return subRoleLevels;
    }
    public void setSubRoleLevels(List<RoleLevel> subRoleLevels) {
        this.subRoleLevels = subRoleLevels;
    }


}
