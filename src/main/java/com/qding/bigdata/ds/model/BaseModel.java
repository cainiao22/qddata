package com.qding.bigdata.ds.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSONObject;

public class BaseModel implements Serializable {

	/**
   *
   */
	private static final long serialVersionUID = 5132800972831969123L;
	private String id;
	private Integer page = 1;
	private Integer pageCount = 10;
	private Integer offset = 0;
	private Integer status ;
	private String sortAndDesc;

	private Date createTime;
	private Date updateTime;

	private String keyword;

	/**
	 * 安照时间范围查询用，开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date queryStartTime;
	/**
	 * 按照时间范围查询用，结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date queryEndTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		if (page == null || page < 1) {
			page = 1;
		}
		this.page = page;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSortAndDesc() {
		return sortAndDesc;
	}

	public void setSortAndDesc(String sortAndDesc) {
		this.sortAndDesc = sortAndDesc;
	}

	public Integer getOffset() {
		this.offset = (page - 1) * pageCount;
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getQueryStartTime() {
		return queryStartTime;
	}

	public void setQueryStartTime(Date queryStartTime) {
		this.queryStartTime = queryStartTime;
	}

	public Date getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(Date queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ":"
				+ JSONObject.toJSONString(this);
	}


}
