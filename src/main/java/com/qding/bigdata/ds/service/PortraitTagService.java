package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.PortraitTag;

import java.util.Collection;
import java.util.List;

public interface PortraitTagService extends BaseService<PortraitTag> {

    PortraitTag getByTagName(String tagName);

    List<PortraitTag> listByTags(Collection<String> tags);


}
