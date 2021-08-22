package com.example.generator.common.dao;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author panzhi
 * @Description
 * @since 2021-03-11
 */
public interface BaseMapper<T> {

    /**
     * 批量插入
     * @param list
     * @return
     */
    int insertList(@Param("list") List<T> list);

    /**
     * 按需插入一条记录
     * @param entity
     * @return
     */
    int insertPrimaryKeySelective(T entity);

    /**
     * 按需修改一条记录（通过主键ID）
     * @return
     */
    int updatePrimaryKeySelective(T entity);

    /**
     * 批量按需修改记录（通过主键ID）
     * @param list
     * @return
     */
    int updateBatchByIds(@Param("list") List<T> list);

    /**
     * 根据ID删除
     * @param id 主键ID
     * @return
     */
    int deleteByPrimaryKey(@Param("id") Serializable id);

    /**
     * 根据ID查询
     * @param id 主键ID
     * @return
     */
    T selectByPrimaryKey(@Param("id") Serializable id);

    /**
     * 按需查询
     * @param entity
     * @return
     */
    List<T> selectByPrimaryKeySelective(T entity);

    /**
     * 批量查询
     * @param ids 主键ID集合
     * @return
     */
    List<T> selectByIds(@Param("ids") List<? extends Serializable> ids);

    /**
     * 查询（根据 columnMap 条件）
     * @param columnMap 表字段 map 对象
     * @return
     */
    List<T> selectByMap(Map<String, Object> columnMap);
}

