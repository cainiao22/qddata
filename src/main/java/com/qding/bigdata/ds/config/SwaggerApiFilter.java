package com.qding.bigdata.ds.config;

import io.swagger.annotations.Api;
import springfox.documentation.RequestHandler;

/**
 * @author yanpf
 * @date 2018/8/7 14:57
 * @description
 */


public class SwaggerApiFilter {

    public boolean apply(RequestHandler input) {

        Api api = input.declaringClass().getAnnotation(Api.class);

        return api != null;
    }
}

