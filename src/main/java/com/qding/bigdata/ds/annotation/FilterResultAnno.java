package com.qding.bigdata.ds.annotation;

import com.qding.bigdata.ds.component.ResultFilter;

import java.lang.annotation.*;

/**
 * @author yanpf
 * @date 2018/7/16 17:54
 * @description
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterResultAnno {

    Class<? extends ResultFilter>[] value();
}
