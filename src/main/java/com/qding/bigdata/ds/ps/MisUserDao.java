package com.qding.bigdata.ds.ps;

import java.util.List;
import java.util.Map;


public interface MisUserDao {

    /**
     * 查询mis账户信息
     * @param param
     * @return
     */
    List<Map<String,Object>> listUser(Map<String,Object> param);

    /**
     * 每日查询用户表增量信息
     * @param param
     * @return
     */
    List<Map<String,Object>> listUserByParam(Map<String,Object> param);

    /**
     * 每日查询离职人员用户名
     * @param param
     * @return
     */
    List<Map<String,Object>> selectMisUserLeaveByJob(Map<String,Object> param);

}
