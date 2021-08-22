package com.example.generator.config.rules;

/**
 * @author panzhi
 * @Description
 * @since 2021-03-11
 */
public interface IColumnType {

    /**
     *
     * 获取字段类型
     * @return 字段类型
     */
    String getType();

    /**
     *
     * 获取字段类型完整名
     * @return 字段类型完整名
     */
    String getPkg();
}
