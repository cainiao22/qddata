package com.qding.bigdata.ds.model;

import java.util.Map;

/**
 * @author yanpf
 * @date 2019/1/21 17:57
 * @description
 */
public class GuancetaiPageExportParam {

    private GuancetaiPageQuotaParam params;

    private Map<String, ExportTitle> titles;

    public GuancetaiPageQuotaParam getParams() {
        return params;
    }

    public void setParams(GuancetaiPageQuotaParam params) {
        this.params = params;
    }

    public Map<String, ExportTitle> getTitles() {
        return titles;
    }

    public void setTitles(Map<String, ExportTitle> titles) {
        this.titles = titles;
    }
}
