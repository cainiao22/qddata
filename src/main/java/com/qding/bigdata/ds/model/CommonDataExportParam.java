package com.qding.bigdata.ds.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yanpf
 * @date 2018/4/23 10:18
 * @description
 */
public class CommonDataExportParam {

    private Map<String, String> params;

    private LinkedHashMap<String, ExportTitle> titles;

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public LinkedHashMap<String, ExportTitle> getTitles() {
        return titles;
    }

    public void setTitles(LinkedHashMap<String, ExportTitle> titles) {
        this.titles = titles;
    }
}
