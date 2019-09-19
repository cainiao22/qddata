package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.DIMProject;
import com.qding.bigdata.ds.model.PortraitDictionaries;
import com.qding.bigdata.ds.model.PortraitTag;

import java.util.List;

public interface PortraitDictionariesService extends BaseService<PortraitDictionaries> {

//    PortraitTag getByTagName(String tagName);
//
//    List<PortraitTag> sortToTree(List<PortraitTag> list);
//
//    List<PortraitTag> listByTags(Collection<String> tags);

//    List<PortraitDictionaries> getDictionariesList(PortraitDictionaries portraitDictionaries);

     List<DIMProject> getDimProjectCity();
     List<DIMProject> getDimProjectProperty();
     List<DIMProject> getDimProjectCommunity();
     void addDinctionaries(List<DIMProject> ldm,PortraitTag portraitTag);

}
