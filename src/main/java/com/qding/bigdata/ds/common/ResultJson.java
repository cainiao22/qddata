package com.qding.bigdata.ds.common;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lzs on 2018/7/6.
 */
public class ResultJson<T> implements Serializable{
    private int code;
    private String msg;
    private List<T> list;

    public ResultJson() {
    }

    public ResultJson(int code, String msg, List<T> list) {
        this.code = code;
        this.msg = msg;
        this.list = list;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
