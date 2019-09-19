package com.qding.bigdata.ds.common;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanpf on 2017/7/20.
 */
public class SearchResult<T> implements Serializable {

    private Integer total;

    private Integer totalPage;

    private Integer currentPage;

    private Integer pageCount;

    private List<T> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer totalCount) {
        this.total = totalCount;
        this.totalPage = (totalCount + pageCount - 1) / pageCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> data) {
        this.rows = data;
    }
}
