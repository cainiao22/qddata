package com.qding.bigdata.ds.model;

public class LpRole {

    /**
     * 角色主键id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 角色描述
     */
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LpRole lpRole = (LpRole) o;

        if (id != null ? !id.equals(lpRole.id) : lpRole.id != null) return false;
        if (roleName != null ? !roleName.equals(lpRole.roleName) : lpRole.roleName != null) return false;
        if (moduleName != null ? !moduleName.equals(lpRole.moduleName) : lpRole.moduleName != null) return false;
        return description != null ? description.equals(lpRole.description) : lpRole.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (moduleName != null ? moduleName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LpRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}