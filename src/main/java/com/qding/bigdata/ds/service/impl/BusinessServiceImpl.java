package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.BusinessMapper;
import com.qding.bigdata.ds.model.Business;
import com.qding.bigdata.ds.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/4/1 17:22
 * @description
 */

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    BusinessMapper businessMapper;

    @Override
    public List<Business> getAll() {
        return businessMapper.getAll();
    }
}
