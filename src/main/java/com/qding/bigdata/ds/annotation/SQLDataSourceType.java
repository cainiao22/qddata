package com.qding.bigdata.ds.annotation;

import com.qding.bigdata.ds.enums.SQLDataSourceTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yanpf on 2017/10/13.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLDataSourceType {
    SQLDataSourceTypeEnum value();
}
