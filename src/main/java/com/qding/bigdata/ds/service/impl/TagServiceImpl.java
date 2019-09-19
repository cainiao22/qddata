package com.qding.bigdata.ds.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import com.qding.bigdata.ds.dao.PortraitTagDao;
import com.qding.bigdata.ds.model.PortraitTag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.service.TagService;

@Service
public class TagServiceImpl extends BaseServiceImpl<PortraitTag> implements TagService {

	@Autowired
	private PortraitTagDao tagDao;

	@Override
	public BaseDao<PortraitTag> getDao() {
		return tagDao;
	}

	@Override
	public void save(PortraitTag t) {
		setLevel(t);
		super.save(t);
	}

	@Override
	public void update(PortraitTag t) {
		setLevel(t);
		super.update(t);
	}

	@Override
	public PortraitTag getByTagName(String tagName) {
		PortraitTag q = new PortraitTag();
		q.setTag(tagName);
		List<PortraitTag> list = this.list(q);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return new PortraitTag();
	}
	private void setLevel(PortraitTag tag) {
	    String pid = tag.getPid();
	    if (pid.equals("-1")) {
	    	tag.setLevel(1);
	    } else {

			PortraitTag pTag = new PortraitTag();
	      pTag.setId(pid);
	      pTag = this.getOne(pTag);

	      tag.setLevel(pTag.getLevel() + 1);
	    }

	  }

	@Override
	public List<PortraitTag> sortToTree(List<PortraitTag> list) {

		LinkedHashMap<String, List<PortraitTag>> subNodeMap = new LinkedHashMap<>();

		for (PortraitTag tag : list) {
			String pid = tag.getPid();
			if (!subNodeMap.containsKey(pid)) {
				subNodeMap.put(pid, new ArrayList<>());
			}
			subNodeMap.get(pid).add(tag);
		}

		List<PortraitTag> newList = new ArrayList<>();
		PortraitTag rootModule = new PortraitTag();
		rootModule.setId("-1");
		putAllSubTags(subNodeMap, rootModule, newList);
		return newList;

	}

	@Override
	public List<PortraitTag> listByTags(Collection<String> tags) {
		if (tags == null || tags.isEmpty()) {
			return new ArrayList<>();
		}
		return tagDao.listByTags("'"+StringUtils.join(tags, "','")+"'");
	}

	private void putAllSubTags(LinkedHashMap<String, List<PortraitTag>> subNodeMap, PortraitTag tag, List<PortraitTag> newList) {

		List<PortraitTag> subTagList = subNodeMap.get(tag.getId());
		if (subTagList == null) {
			return;
		}
		for (PortraitTag curTag : subTagList) {
			newList.add(curTag);
			if (subNodeMap.containsKey(curTag.getId())) {
				putAllSubTags(subNodeMap, curTag, newList);
			}
		}

	}

	@Override
	public List<PortraitTag> list(PortraitTag t) {
		if(StringUtils.isEmpty(t.getSortAndDesc())){
			t.setSortAndDesc("level asc,sortno asc");
		}
		return this.getDao().list(t);
	}
	
	
}
