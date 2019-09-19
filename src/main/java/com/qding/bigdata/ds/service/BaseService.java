package com.qding.bigdata.ds.service;

import java.util.Collection;
import java.util.List;

import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.model.BaseModel;

public interface BaseService<T extends BaseModel> {

	List<T> list(T t);

	List<T> listAll(T t);

	List<T> listByIDs(Collection<String> ids);

	void save(T t);

	void update(T t);

	T getOne(T t);

	Integer count(T t);

	void delete(T t);

	SearchResult<T> listData(T t);
}
