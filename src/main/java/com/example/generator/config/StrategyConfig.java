package com.example.generator.config;

import com.example.generator.config.converts.ITypeHandler;

import java.util.Map;

/**
 * @author panzhi
 * @Description
 * @since 2021-03-11
 */
public class StrategyConfig {

    /**
     * 是否跳过视图
     */
    private boolean skipView = false;

    /**
     * 自定义继承的Entity类全称，带包名
     */
    private String superEntityClass;

    /**
     * 自定义继承的Request类全称，带包名
     */
    private String superRequestClass;

    /**
     * 自定义继承的DTO类全称，带包名
     */
    private String superDtoClass;

    /**
     * 自定义继承的Dao类全称，带包名
     */
    private String superDaoClass;

    /**
     * 自定义继承的Service类全称，带包名
     */
    private String superServiceClass;

    /**
     * 表前缀，反向生成的时候会排除前缀
     */
    private String[] tablePrefix;

    /**
     * 需要生成的表名,允许正则表达式（与 tableMap 二选一配置）
     */
    private String[] includeTable;

    /**
     * 需要生成的表名，填充格式(key-value)：表名-实体，例如，tb_user: User
     */
    private Map<String, String> tableMap;

    /**
     * mybatis指定字段类型转化
     */
    private ITypeHandler typeHandler;

    public boolean isSkipView() {
        return skipView;
    }

    public StrategyConfig setSkipView(boolean skipView) {
        this.skipView = skipView;
        return this;
    }

    public String getSuperRequestClass() {
        return superRequestClass;
    }

    public StrategyConfig setSuperRequestClass(String superRequestClass) {
        this.superRequestClass = superRequestClass;
        return this;
    }

    public String getSuperDtoClass() {
        return superDtoClass;
    }

    public StrategyConfig setSuperDtoClass(String superDtoClass) {
        this.superDtoClass = superDtoClass;
        return this;
    }

    public String getSuperEntityClass() {
        return superEntityClass;
    }

    public StrategyConfig setSuperEntityClass(String superEntityClass) {
        this.superEntityClass = superEntityClass;
        return this;
    }

    public String getSuperServiceClass() {
        return superServiceClass;
    }

    public StrategyConfig setSuperServiceClass(String superServiceClass) {
        this.superServiceClass = superServiceClass;
        return this;
    }

    public String getSuperDaoClass() {
        return superDaoClass;
    }

    public StrategyConfig setSuperDaoClass(String superDaoClass) {
        this.superDaoClass = superDaoClass;
        return this;
    }

    public String[] getTablePrefix() {
        return tablePrefix;
    }

    public StrategyConfig setTablePrefix(String... tablePrefix) {
        this.tablePrefix = tablePrefix;
        return this;
    }

    public String[] getIncludeTable() {
        return includeTable;
    }

    public StrategyConfig setIncludeTable(String[] includeTable) {
        this.includeTable = includeTable;
        return this;
    }

    public Map<String, String> getTableMap() {
        return tableMap;
    }

    public StrategyConfig setTableMap(Map<String, String> tableMap) {
        this.tableMap = tableMap;
        return this;
    }

    public ITypeHandler getTypeHandler() {
        return typeHandler;
    }

    public StrategyConfig setTypeHandler(ITypeHandler typeHandler) {
        this.typeHandler = typeHandler;
        return this;
    }


}

