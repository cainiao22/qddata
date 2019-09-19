package com.qding.bigdata.ds.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.qding.bigdata.ds.enums.RedisDataTypeEnum;

/**
 * Created by yanpf on 2017/8/7.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisDataType {
    RedisDataTypeEnum value();
}
