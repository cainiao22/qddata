package com.qding.bigdata.ds.model;

import java.util.Map;

/**
 * @author yanpf
 * @date 2018/5/30 19:17
 * @description
 */
public class HttpDataParam {

    private String url;

    private Map<String, String> params;

    private Boolean post;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Boolean getPost() {
        return post;
    }

    public void setPost(Boolean post) {
        this.post = post;
    }
}
