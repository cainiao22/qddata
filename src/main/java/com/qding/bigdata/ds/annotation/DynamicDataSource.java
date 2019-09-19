package com.qding.bigdata.ds.annotation;

import java.lang.annotation.*;

/**
 * @author yanpf
 * @date 2019/5/14 11:04
 * @description
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DynamicDataSource {

    String value() default "";
}
