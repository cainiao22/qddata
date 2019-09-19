package com.qding.bigdata.ds.service;

import java.util.Collection;
import java.util.List;

import com.qding.bigdata.ds.model.PortraitTag;
import com.qding.bigdata.ds.model.Tag;

public interface TagService extends BaseService<PortraitTag> {

	PortraitTag getByTagName(String tagName);

	List<PortraitTag> sortToTree(List<PortraitTag> list);

	List<PortraitTag> listByTags(Collection<String> tags);

}
