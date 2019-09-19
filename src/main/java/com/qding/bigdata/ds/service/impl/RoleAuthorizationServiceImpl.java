package com.qding.bigdata.ds.service.impl;

import com.qding.bigdata.ds.dao.RoleAuthorizationDao;
import com.qding.bigdata.ds.service.RoleAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Created by syj on 2018/6/21.
 */
@Service
public class RoleAuthorizationServiceImpl implements RoleAuthorizationService {
    @Autowired
    RoleAuthorizationDao roleAuthorizationDao;

}
