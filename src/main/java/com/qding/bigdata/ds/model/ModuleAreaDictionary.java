package com.qding.bigdata.ds.model;

public class ModuleAreaDictionary {
    private Long id;

    private String moduleName;

    private Integer level;

    private String name;

    private String desc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    @Override
    public String toString() {
        return "ModuleAreaDictionary{" +
                "id=" + id +
                ", moduleName='" + moduleName + '\'' +
                ", level=" + level +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}