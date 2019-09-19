package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.LpUserRole;

import java.util.List;

public interface LpUserRoleDao {
    /**
     * 依据主键删除一条用户角色关联信息
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入一条用户角色信息 字段不能为空
     * @param record
     * @return
     */
    int insert(LpUserRole record);

    /**
     * 插入一条用户角色信息
     * @param record
     * @return
     */
    int insertSelective(LpUserRole record);

    /**
     * 依据主键查询一条用户角色信息
     * @param id
     * @return
     */
    LpUserRole selectByPrimaryKey(Long id);

    /**
     * 依据主键更新 用户角色关联表不为空数据
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(LpUserRole record);

    /**
     * 依据主键更新用户角色表单条数据
     * @param record
     * @return
     */
    int updateByPrimaryKey(LpUserRole record);

    /**
     * 查询全部用户角色关联信息
     * @return
     */
    List<LpUserRole> selectAll();

    /**
     * 查询用户角色关联信息 依据用户id 或者角色id 或者用户名称
     * @param param
     * @return
     */
    List<LpUserRole> select(LpUserRole param);
}