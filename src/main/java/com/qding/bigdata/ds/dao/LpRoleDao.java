package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.LpRole;

import java.util.List;

public interface LpRoleDao {
    /**
     * 依据主键删除一个角色
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入一条角色数据 字段均需有值
     * @param record
     * @return
     */
    int insert(LpRole record);

    /**
     * 插入一条角色数据
     * @param record
     * @return
     */
    int insertSelective(LpRole record);

    /**
     * 依据主键查询一条角色信息
     * @param id
     * @return
     */
    LpRole selectByPrimaryKey(Long id);

    /**
     * 依据主键更新不为null字段
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(LpRole record);

    /**
     * 依据主键更新单条角色全部字段
     * @param record
     * @return
     */
    int updateByPrimaryKey(LpRole record);

    /**
     * 查询全部角色
     * @return
     */
    List<LpRole> selectAll();

    /**
     * 查询角色信息 依据角色名称和模块名称
     * @param param
     * @return
     */
    List<LpRole> select(LpRole param);
}