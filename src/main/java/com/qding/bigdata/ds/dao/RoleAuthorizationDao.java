package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.RoleAuthorization;

import java.util.List;

public interface RoleAuthorizationDao {
    /**
     * 依据主键删除模块
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);
    /**
     * 插入一条模块 字段不能为空
     * @param record
     * @return
     */
    int insert(RoleAuthorization record);

    /**
     * 插入list集合
     * @param list
     * @return
     */

    int addList(List<RoleAuthorization > list);
    /**
     * 插入一条模块信息
     * @param record
     * @return
     */
    int insertSelective(RoleAuthorization record);
    /**
     * 依据主键id查询一条模块信息
     * @param id
     * @return
     */
    RoleAuthorization selectByPrimaryKey(Long id);
    /**
     * 依据主键更新模块不为null字段
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(RoleAuthorization record);
    /**
     * 依据主键更新模块单条全部字段
     * @param record
     * @return
     */
    int updateByPrimaryKey(RoleAuthorization record);
    /**
     * 查询全部模块
     * @return
     */
    List<RoleAuthorization> selectAll();


    /**
     * 查询角色权限有多少条
     * @param param
     * @return
     */
    int count(RoleAuthorization param);

    /**
     * 依据参数查询角色权限表
     * @param param
     * @return
     */
    List<RoleAuthorization> list(RoleAuthorization param);

    void delete(Long roleId);
}