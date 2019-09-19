package com.qding.bigdata.ds.VO;

public class SelectVO {
    public Long id;
    public String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SelectVO(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
