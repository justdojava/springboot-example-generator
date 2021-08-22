package com.example.generator.config.converts;

import com.example.generator.constant.StringPool;

/**
 * @author panzhi
 * @Description 字段适配转换器
 * @since 2021-03-11
 */
public interface ITypeHandler {

    /**
     * 是否启用转化
     * @param fieldName
     * @return
     */
    default boolean enable(String fieldName){
        return false;
    }

    /**
     * Mybatis字段类型转化
     * @param fieldName
     * @return
     */
    default String processTypeHandler(String fieldName){
        return StringPool.EMPTY;
    }
}

