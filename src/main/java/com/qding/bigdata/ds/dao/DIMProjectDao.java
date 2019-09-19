package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.DIMProject;
import com.qding.bigdata.ds.model.DIMProjectWithBLOBs;

import java.util.List;

public interface DIMProjectDao {
    int insert(DIMProjectWithBLOBs record);

    int insertSelective(DIMProjectWithBLOBs record);

    List<DIMProject> getCity();
    List<DIMProject> getProperty();
    List<DIMProject> getCommunity();
}