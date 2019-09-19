package com.qding.bigdata.ds.common;

import java.util.Collection;

/**
 * Created by yanpf on 2017/11/13.
 * 分页result
 */
public class PageResult<T extends Collection> extends Result<T> {

    /**
     * 总记录条数
     */
    private Integer total;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 当前页码
     */
    private Integer currentPage;

    /**
     * 当前页的数量
     */
    private Integer pageCount;

    /**
     * 每页显示
     */
    private Integer pageSize;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer totalCount) {
        this.total = totalCount;
        if(pageSize != null && pageSize != 0) {
            this.totalPage = (totalCount + pageSize - 1) / pageSize;
        }
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public PageResult(int code, String errorMsg) {
        super(code, errorMsg);
    }

    public PageResult(){
        this(0, "");
    }
}
