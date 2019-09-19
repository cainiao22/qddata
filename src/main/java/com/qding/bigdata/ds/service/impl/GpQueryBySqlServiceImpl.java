package com.qding.bigdata.ds.service.impl;

import java.util.*;

import com.qding.bigdata.ds.model.ExportTitle;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.qding.bigdata.ds.dao.QueryBySqlDao;
import com.qding.bigdata.ds.service.QueryBySqlService;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

@Service
public class GpQueryBySqlServiceImpl  implements QueryBySqlService{

	private QueryBySqlDao queryBySqlDao;

	@Resource(name = "sqlSessionExecutor")
	public void setQueryBySqlDao(SqlSession sqlSession) {
		this.queryBySqlDao = sqlSession.getMapper(QueryBySqlDao.class);
	}

	@Override
	public List<Object[]> queryBySql(String sql) {
		 List<Object[]> resultList=new ArrayList<Object[]>();
		List<Map<String, Object>> list = queryBySqlDao.queryBySql(sql);
		if(list.isEmpty()){
			return resultList;
		}
		resultList.add(list.get(0).keySet().toArray());
		for (Map<String, Object> map : list) {
			resultList.add(map.values().toArray());
		}
		return resultList;
	}

	@Override
	public List<Object[]> queryBySqlForTitle(String sql, Map<String, ExportTitle> titles) {
		List<Object[]> resultList=new ArrayList<Object[]>();
		List<Map<String, Object>> list = queryBySqlDao.queryBySql(sql);
		if(CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(titles)){
			return resultList;
		}
		List<Map.Entry> entryList = new ArrayList<Map.Entry>();
		for(Map.Entry e : titles.entrySet()){
			entryList.add(e);
		}
		Collections.sort(entryList, new Comparator<Map.Entry>() {
			@Override
			public int compare(Map.Entry o1, Map.Entry o2) {
				return ((ExportTitle)o1.getValue()).compareTo((ExportTitle)o2.getValue());
			}
		});
		LinkedHashMap<String, ExportTitle> sortedTitle = new LinkedHashMap<String, ExportTitle>();
		for (Map.Entry<String, ExportTitle> entry : entryList) {
			sortedTitle.put(entry.getKey(), entry.getValue());
		}
		List<String> titleList = new ArrayList<String>();
		for (ExportTitle title : sortedTitle.values()) {
			titleList.add(title.getName());
		}
		resultList.add(titleList.toArray());
		for (Map<String, Object> map : list) {
			List<Object> values = new ArrayList<Object>();
			for (String key : sortedTitle.keySet()) {
				String v = map.get(key) == null ? null : map.get(key).toString();
				//如果数字超过10为会被excel转为科学记数法
				if(v != null && v.length()>10){
					v += "\t";
				}
				values.add(v);
			}
			resultList.add(values.toArray());
		}
		return resultList;
	}

}
