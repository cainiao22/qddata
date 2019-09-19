package com.qding.bigdata.ds.model;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;

/**
 * @author yanpf
 * @date 2019/1/22 10:16
 * @description
 */
public class MostCommonDataExportParam {

    private LinkedHashMap<String, String> params;

    private LinkedHashMap<String, ExportTitle> titles;

    public LinkedHashMap<String, String> getParams() {
        return params;
    }

    public <T> T getObjParams(Class<T> clazz) {
        String jsonString = JSON.toJSONString(params);
        T object = JSON.parseObject(jsonString, clazz);
        return object;
    }

    public void setParams(LinkedHashMap<String, String> params) {
        this.params = params;
    }

    public LinkedHashMap<String, ExportTitle> getTitles() {
        return titles;
    }

    public void setTitles(LinkedHashMap<String, ExportTitle> titles) {
        this.titles = titles;
    }
}
