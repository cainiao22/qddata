package com.qding.bigdata.ds.VO;

import java.util.ArrayList;
import java.util.List;

/**
 * 路径分析树图结构
 */
public class PathAnalysisVO {
    public String name;
    public String value;
    public List<PathAnalysisVO> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<PathAnalysisVO> getChildren() {
        return children;
    }

    public void setChildren(List<PathAnalysisVO> children) {
        this.children = children;
    }

    public PathAnalysisVO() {
        this.setChildren(new ArrayList<PathAnalysisVO>());
    }
    public PathAnalysisVO(String name) {
        this.name = name;
    }

    public PathAnalysisVO(String name, String value, List<PathAnalysisVO> children) {
        this.name = name;
        this.value = value;
        this.children = children;
    }

}
