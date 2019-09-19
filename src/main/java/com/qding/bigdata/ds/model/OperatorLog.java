package com.qding.bigdata.ds.model;

import java.util.Date;

public class OperatorLog extends BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = -1963477195773582901L;

    private Date operatorTime;
    private String username;
    private String realname;
    private String moduleurl;
    private String moduleName;
    private Integer count;

    public OperatorLog() {
        super();
    }

    public OperatorLog(String username, String realname, String moduleurl) {
        super();
        this.username = username;
        this.realname = realname;
        this.moduleurl = moduleurl;
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getModuleurl() {
        return moduleurl;
    }

    public void setModuleurl(String moduleurl) {
        this.moduleurl = moduleurl;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
