package com.qding.bigdata.ds.Exception;

/**
 * @author yanpf
 * @date 2018/2/6 12:09
 * @description
 */
public class CommonDataSearchEmptyException extends Exception {

    public CommonDataSearchEmptyException(){
        super("通用接口返回结果为null,没有数据");
    }
}
