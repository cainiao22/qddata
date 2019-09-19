package com.qding.bigdata.ds.service;

import com.qding.bigdata.ds.model.UserDetailParam;

import java.util.List;
import java.util.Map;

/**
 * @Author yanpf
 * @Date 19-9-2 下午4:46
 * @Description TODO
 **/

public interface GCTUserDetailService {

    List<Map<String, Object>> queryUserDetails(UserDetailParam param);

    Map<String, List<Map<String, Object>>> queryUserTrail(String userId, String queryDate);
}
