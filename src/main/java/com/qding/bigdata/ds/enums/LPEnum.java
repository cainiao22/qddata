package com.qding.bigdata.ds.enums;

/**
 * Created by syj on 2018/6/22.
 */
public enum LPEnum {
    LEVEL("level","区域级别") ,
    REGION_ID("region_id","区域id") ,
    REGION_NAME("region_name","区域名称") ,
    PID("pid","区域父id"),
    projectList("projectList","社区列表"),
    cityList("cityList","城市列表") ,
    areaList("areaList","片区列表") ;

   private String value;
   private String name;

    LPEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
