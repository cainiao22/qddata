package com.qding.bigdata.ds.model;

import java.util.Map;

/**
 * @author yanpf
 * @date 2018/7/19 17:07
 * @description
 */
public class ExportParam {

    public ExportParam(){
        System.out.println("exportParam inited ");
    }

    private TrackParam params;

    private Map<String, ExportTitle> titles;

    public TrackParam getParams() {
        return params;
    }

    public void setParams(TrackParam params) {
        this.params = params;
    }

    public Map<String, ExportTitle> getTitles() {
        return titles;
    }

    public void setTitles(Map<String, ExportTitle> titles) {
        this.titles = titles;
    }
}
