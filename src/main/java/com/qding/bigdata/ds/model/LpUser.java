package com.qding.bigdata.ds.model;

public class LpUser {
    /**
     * 用户主键id
     */
    private Long id;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 模块名称
     */
    private String moduleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LpUser lpUser = (LpUser) o;

        if (!id.equals(lpUser.id)) return false;
        if (!userName.equals(lpUser.userName)) return false;
        if (!realName.equals(lpUser.realName)) return false;
        return moduleName.equals(lpUser.moduleName);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userName.hashCode();
        result = 31 * result + realName.hashCode();
        result = 31 * result + moduleName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LpUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                ", moduleName='" + moduleName + '\'' +
                '}';
    }
}