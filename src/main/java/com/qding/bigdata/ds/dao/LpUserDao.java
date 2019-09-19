package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.LpUser;

import java.util.List;

public interface LpUserDao {
    /**
     * 依据主键删除用户
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入一条用户信息,全部字段均需有值
     * @param record
     * @return
     */
    int insert(LpUser record);

    /**
     * 插入一条用户信息
     * @param record
     * @return
     */
    int insertSelective(LpUser record);

    /**
     * 依据主键id查询一条用户数据
     * @param id
     * @return
     */
    LpUser selectByPrimaryKey(Long id);

    /**
     *依据主键更新一条用户 不为null字段
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(LpUser record);

    /**
     * 依据主键更新整条用户数据
     * @param record
     * @return
     */
    int updateByPrimaryKey(LpUser record);

    /**
     * 查询全部用户信息
     * @return
     */
    List<LpUser> selectAll();

    /**
     * 依据用户账号和查询一条权限用户信息
     * @param param
     * @return
     */
    LpUser selectLpUserByUserName(LpUser param);
}