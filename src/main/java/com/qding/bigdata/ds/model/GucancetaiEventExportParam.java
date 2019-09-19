package com.qding.bigdata.ds.model;

import java.util.Map;

/**
 * @author yanpf
 * @date 2019/1/21 17:33
 * @description
 */

public class GucancetaiEventExportParam {

    private GuancetaiEventQuotaParam params;

    private Map<String, ExportTitle> titles;

    public GuancetaiEventQuotaParam getParams() {
        return params;
    }

    public void setParams(GuancetaiEventQuotaParam params) {
        this.params = params;
    }

    public Map<String, ExportTitle> getTitles() {
        return titles;
    }

    public void setTitles(Map<String, ExportTitle> titles) {
        this.titles = titles;
    }
}
