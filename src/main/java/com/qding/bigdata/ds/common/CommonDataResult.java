package com.qding.bigdata.ds.common;

/**
 * @author yanpf
 * @date 2018/1/30 15:01
 * @description
 */
public class CommonDataResult<T> extends Result {

    private String name;

    public CommonDataResult(int code, String errorMsg) {
        super(code, errorMsg);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
