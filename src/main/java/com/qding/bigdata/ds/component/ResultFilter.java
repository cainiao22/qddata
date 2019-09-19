package com.qding.bigdata.ds.component;

/**
 * @author yanpf
 * @date 2018/7/17 11:47
 * @description
 */
public interface ResultFilter<T, V, P> {

    V doAfter(T value, P params);
}
