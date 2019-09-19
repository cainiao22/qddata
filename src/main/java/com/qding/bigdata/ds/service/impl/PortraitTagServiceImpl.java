package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.BaseDao;
import com.qding.bigdata.ds.dao.PortraitTagDao;
import com.qding.bigdata.ds.model.PortraitDictionaries;
import com.qding.bigdata.ds.model.PortraitTag;
import com.qding.bigdata.ds.model.Tag;
import com.qding.bigdata.ds.service.PortraitTagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2018/12/20 0020.
 */
@Service
public class PortraitTagServiceImpl  extends BaseServiceImpl<PortraitTag> implements PortraitTagService {

    @Autowired
    private PortraitTagDao portraitTagDao;


    @Override
    public BaseDao<PortraitTag> getDao() {
        return portraitTagDao;
    }

    @Override
    public List<PortraitTag> list(PortraitTag t) {
        if(StringUtils.isEmpty(t.getSortAndDesc())){
            t.setSortAndDesc("level asc");
        }
        return this.getDao().list(t);
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


    @Override
    public List<PortraitTag> listByTags(Collection<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return new ArrayList<PortraitTag>();
        }
        return portraitTagDao.listByTags("'"+StringUtils.join(tags, "','")+"'");
    }
}
