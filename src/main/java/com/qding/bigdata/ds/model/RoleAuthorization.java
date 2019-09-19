package com.qding.bigdata.ds.model;

public class RoleAuthorization {
    private Long id;

    private Long roleId;

    private String regionId;

    private String regionName;

    private String parentId;

    private Integer regionLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(Integer regionLevel) {
        this.regionLevel = regionLevel;
    }

    @Override
    public String toString() {
        return "RoleAuthorization{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", regionId='" + regionId + '\'' +
                ", regionName='" + regionName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", regionLevel=" + regionLevel +
                '}';
    }
}