package com.qding.bigdata.ds.annotation;

/**
 * @author yanpf
 * @date 2018/7/20 15:26
 * @description
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface FormModel {
}
