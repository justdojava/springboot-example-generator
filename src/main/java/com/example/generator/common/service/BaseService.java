package com.example.generator.common.service;

import com.example.generator.common.dao.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author panzhi
 * @Description
 * @since 2021-03-11
 */
public abstract class BaseService<M extends BaseMapper<T>, T> {

    @Autowired
    protected M baseMapper;

    /**
     * 新增
     * @param entity
     * @return boolean
     */
    @Transactional(rollbackFor = {Exception.class})
    public boolean insert(T entity){
        return returnBool(baseMapper.insertPrimaryKeySelective(entity));
    }

    /**
     * 批量新增
     * @param list
     * @return boolean
     */
    @Transactional(rollbackFor = {Exception.class})
    public boolean insertList(List<T> list){
        return returnBool(baseMapper.insertList(list));
    }

    /**
     * 通过ID修改记录（如果想全部更新，只需保证字段都不为NULL）
     * @param entity
     * @return boolean
     */
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateById(T entity){
        return returnBool(baseMapper.updatePrimaryKeySelective(entity));
    }

    /**
     * 通过ID批量修改记录（如果想全部更新，只需保证字段都不为NULL）
     * @param list
     * @return boolean
     */
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateBatchByIds(List<T> list){
        return returnBool(baseMapper.updateBatchByIds(list));
    }

    /**
     * 根据ID删除
     * @param id 主键ID
     * @return boolean
     */
    @Transactional(rollbackFor = {Exception.class})
    public boolean deleteById(Serializable id){
        return returnBool(baseMapper.deleteByPrimaryKey(id));
    }

    /**
     * 根据ID查询
     * @param id 主键ID
     * @return
     */
    public T selectById(Serializable id){
        return baseMapper.selectByPrimaryKey(id);
    }

    /**
     * 按需查询
     * @param entity
     * @return
     */
    public List<T> selectByPrimaryKeySelective(T entity){
        return baseMapper.selectByPrimaryKeySelective(entity);
    }

    /**
     * 批量查询
     * @param ids
     * @return
     */
    public List<T> selectByIds(List<? extends Serializable> ids){
        return baseMapper.selectByIds(ids);
    }

    /**
     * 根据条件查询
     * @param columnMap
     * @return
     */
    public List<T> selectByMap(Map<String, Object> columnMap){
        return baseMapper.selectByMap(columnMap);
    }

    /**
     * 判断数据库操作是否成功
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected boolean returnBool(Integer result) {
        return null != result && result >= 1;
    }

}

