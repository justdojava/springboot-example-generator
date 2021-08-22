package com.example.generator.config.converts;

import com.example.generator.config.rules.IColumnType;

/**
 * @author panzhi
 * @Description 字段类型转换器
 * @since 2021-03-11
 */
public interface ITypeConvert {

    /**
     * 执行数据库类型转换
     * @param fieldName
     * @param fieldType
     * @return
     */
    String convertJdbcType(String fieldName, String fieldType);

    /**
     * 执行实体类型转换
     * @param fieldName
     * @param fieldType
     * @return
     */
    IColumnType convertPropertyType(String fieldName, String fieldType);

    /**
     * 支持自定义类型转化
     * @param fieldName
     * @param fieldType
     * @return
     */
    default IColumnType customerType(String fieldName,String fieldType){
        return null;
    };
}

