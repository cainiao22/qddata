package com.qding.bigdata.ds.model;

public class LpModule {
    /**
     * 模块主键id
     */
    private Long id;

    /**
     * 模块名称
     */
    private String name;

    /**
     * 模块描述
     */
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

        LpModule lpModule = (LpModule) o;

        if (id != null ? !id.equals(lpModule.id) : lpModule.id != null) return false;
        if (name != null ? !name.equals(lpModule.name) : lpModule.name != null) return false;
        return description != null ? description.equals(lpModule.description) : lpModule.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LpModule{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}