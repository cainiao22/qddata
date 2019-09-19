package com.qding.bigdata.ds.common;

import java.io.Serializable;

/**
 * Created by yanpf on 2017/7/26.
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 2308273315969771694L;

    protected int code;

    /**
     * 任务执行耗时
     */
    protected Long timeUsed;

    protected String errorMsg;

    /**
     * 备注信息，不需要返回给调用方，系统内部使用，用完之后设置为空
     */
    protected String remark;

    protected T data;

    public Result(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static Result failed(int code, String errorMsg){
        return new Result(code, errorMsg);
    }

    public static <T> Result success(T data){
        Result result = new Result<T>(0, "成功");
        result.setData(data);
        return result;
    }

    public static <T> Result success(T data, Long timeUsed){
        Result result = new Result<T>(0, "成功");
        result.setData(data);
        result.setTimeUsed(timeUsed);
        return result;
    }

    public Long getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(Long timeUsed) {
        this.timeUsed = timeUsed;
    }

    public static Result success() {
        return success(null);
    }
}
