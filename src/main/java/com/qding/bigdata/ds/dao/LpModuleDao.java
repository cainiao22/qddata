package com.qding.bigdata.ds.dao;

import com.qding.bigdata.ds.model.LpModule;

import java.util.List;

public interface LpModuleDao {
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
    int insert(LpModule record);

    /**
     * 插入一条模块信息
     * @param record
     * @return
     */
    int insertSelective(LpModule record);

    /**
     * 依据主键id查询一条模块信息
     * @param id
     * @return
     */
    LpModule selectByPrimaryKey(Long id);

    /**
     * 依据主键更新模块不为null字段
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(LpModule record);

    /**
     * 依据主键更新模块单条全部字段
     * @param record
     * @return
     */
    int updateByPrimaryKey(LpModule record);

    /**
     * 查询全部模块
     * @return
     */
    List<LpModule> selectAll();

    /**
     * 依据模块名称查询一条模块记录
     * @param name
     * @return
     */
    LpModule selectModuleByName(String name);
}