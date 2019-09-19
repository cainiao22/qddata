package com.qding.bigdata.ds.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.model.BaseModel;
import com.qding.bigdata.ds.service.BaseService;
import com.qding.bigdata.ds.util.UUIDUtil;

public abstract class BaseServiceImpl<T extends BaseModel> implements BaseService<T> {

	protected Log log = LogFactory.getLog(this.getClass());

	public abstract BaseDao<T> getDao();

	@Override
	public List<T> listAll(T t) {
		t.setPage(1);
		t.setPageCount(Integer.MAX_VALUE);
		return list(t);
	}

	@Override
	public List<T> list(T t) {
		t.setOffset((t.getPage() - 1) * t.getPageCount());
		if (StringUtils.isBlank(t.getSortAndDesc())) {
			t.setSortAndDesc("create_time desc");
		}
		List<T> list = this.getDao().list(t);
		return list;
	}

	@Override
	public void save(T t) {
		t.setId(UUIDUtil.createId());
		t.setCreateTime(new Date());
		t.setUpdateTime(new Date());
		this.getDao().save(t);
	}

	@Override
	public void update(T t) {
		t.setUpdateTime(new Date());
		this.getDao().updateById(t);

	}

	@Override
	public T getOne(T t) {
		return this.getDao().getById(t);

	}

	@Override
	public Integer count(T t) {
		return this.getDao().count(t);

	}

	@Override
	public void delete(T t) {
		this.getDao().delete(t);

	}

	@Override
	public List<T> listByIDs(Collection<String> ids) {
		if (ids == null || ids.isEmpty()) {
			return new ArrayList<T>();
		}
		return this.getDao().listByIDs("'" + StringUtils.join(ids, "','") + "'");
	}

	@Override
	public SearchResult<T> listData(T t) {
		List<T> list = this.list(t);
		SearchResult<T> result = new SearchResult<T>();
		result.setPageCount(t.getPageCount());
		result.setCurrentPage(t.getPage());
		result.setTotal(this.count(t));
		result.setRows(list);
		return result;
	}

}
