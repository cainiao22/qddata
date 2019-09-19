package com.qding.bigdata.ds.enums;

/**
 * 用户来源枚举
 */
public enum UserSource {
    COMPASS("COMPASS",Short.valueOf("0")),
    BOSS("BOSS",Short.valueOf("1")),
    MIS("MIS",Short.valueOf("2"));
    private  String name;
    private  Short value;

    private UserSource(String name, Short value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getValue() {
        return value;
    }

    public void setValue(Short value) {
        this.value = value;
    }
}
