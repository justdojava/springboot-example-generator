package com.example.generator.builder;

import com.example.generator.config.DataSourceConfig;
import com.example.generator.config.GlobalConfig;
import com.example.generator.config.PackageConfig;
import com.example.generator.config.StrategyConfig;
import com.example.generator.config.rules.IColumnType;
import com.example.generator.config.type.IDbQuery;
import com.example.generator.constant.ConstValue;
import com.example.generator.constant.StringPool;
import com.example.generator.entity.TableField;
import com.example.generator.entity.TableInfo;
import com.example.generator.util.NamingStrategyHelper;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author panzhi
 * @Description
 * @since 2021-03-11
 */
public class ConfigBuilder {

    /**
     * SQL连接
     */
    private Connection connection;
    /**
     * SQL语句类型
     */
    private IDbQuery dbQuery;

    /**
     * 全局配置信息
     */
    private GlobalConfig globalConfig;

    /**
     * 数据库配置
     */
    private DataSourceConfig dataSourceConfig;

    /**
     * 包配置详情
     */
    private Map<String, String> packageInfo;

    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig;

    /**
     * 路径配置信息(封装文件输出路径)
     */
    private Map<String, String> pathInfo;

    /**
     * 数据库表信息(封装文件配置信息)
     */
    private List<TableInfo> tableInfoList;

    /**
     * 在构造器中处理配置
     * @param globalConfig      全局配置
     * @param dataSourceConfig  数据源配置
     * @param packageConfig     包配置
     * @param strategyConfig    表配置
     */
    public ConfigBuilder(GlobalConfig globalConfig, DataSourceConfig dataSourceConfig, PackageConfig packageConfig, StrategyConfig strategyConfig) {
        this.globalConfig = (Objects.isNull(globalConfig) ? new GlobalConfig() : globalConfig);
        this.dataSourceConfig = (Objects.isNull(dataSourceConfig) ? new DataSourceConfig() : dataSourceConfig);
        this.strategyConfig = (Objects.isNull(strategyConfig) ? new StrategyConfig() : strategyConfig);
        // 包路径配置
        handlerPackage(globalConfig.getOutputDir(), globalConfig.getXmlOutputDir(), (Objects.isNull(packageConfig) ? new PackageConfig() : packageConfig));
        // 数据源配置
        handlerDataSource(dataSourceConfig);
        // 表结构生成实体配置
        handlerStrategy(strategyConfig);
    }


    /**
     * 处理包配置
     * @param outputDir
     * @param xmlOutputDir
     * @param config
     */
    private void handlerPackage(String outputDir, String xmlOutputDir, PackageConfig config) {
        // 包信息
        packageInfo = new HashMap<>();
        packageInfo.put(ConstValue.PACKAGE_ENTITY, joinPackage(config.getParent(), config.getEntity()));
        packageInfo.put(ConstValue.PACKAGE_REQUEST, joinPackage(config.getParent(), config.getRequest()));
        packageInfo.put(ConstValue.PACKAGE_DTO, joinPackage(config.getParent(), config.getDto()));
        packageInfo.put(ConstValue.PACKAGE_DAO, joinPackage(config.getParent(), config.getDao()));
        packageInfo.put(ConstValue.PACKAGE_SERVICE, joinPackage(config.getParent(), config.getService()));
        packageInfo.put(ConstValue.PACKAGE_API, joinPackage(config.getParent(), config.getApi()));
        packageInfo.put(ConstValue.PACKAGE_PROVIDER, joinPackage(config.getParent(), config.getProvider()));
        packageInfo.put(ConstValue.PACKAGE_JUNIT, joinPackage(config.getParent(), config.getJunit()));
        packageInfo.put(ConstValue.PACKAGE_XML, joinPackage(config.getParent(), config.getXml()));
        packageInfo.put(ConstValue.PACKAGE_MENU_JSON, joinPackage(config.getParent(), config.getMenuJson()));

        // 生成路径信息
        pathInfo = new HashMap<>(6);
        pathInfo.put(ConstValue.ENTITY_PATH, joinPath(outputDir, packageInfo.get(ConstValue.PACKAGE_ENTITY)));
        pathInfo.put(ConstValue.REQUEST_PATH, joinPath(outputDir, packageInfo.get(ConstValue.PACKAGE_REQUEST)));
        pathInfo.put(ConstValue.DTO_PATH, joinPath(outputDir, packageInfo.get(ConstValue.PACKAGE_DTO)));
        pathInfo.put(ConstValue.DAO_PATH, joinPath(outputDir, packageInfo.get(ConstValue.PACKAGE_DAO)));
        pathInfo.put(ConstValue.SERVICE_PATH, joinPath(outputDir, packageInfo.get(ConstValue.PACKAGE_SERVICE)));
        pathInfo.put(ConstValue.API_PATH, joinPath(outputDir, packageInfo.get(ConstValue.PACKAGE_API)));
        pathInfo.put(ConstValue.PROVIDER_PATH, joinPath(outputDir, packageInfo.get(ConstValue.PACKAGE_PROVIDER)));
        pathInfo.put(ConstValue.JUNIT_PATH, joinPath(outputDir, packageInfo.get(ConstValue.PACKAGE_JUNIT)));
        if(StringUtils.isNotBlank(xmlOutputDir)){
            pathInfo.put(ConstValue.XML_PATH, xmlOutputDir);
            pathInfo.put(ConstValue.MENU_PATH, xmlOutputDir);
        } else {
            pathInfo.put(ConstValue.XML_PATH, joinPath(outputDir, packageInfo.get(ConstValue.PACKAGE_XML)));
            pathInfo.put(ConstValue.MENU_PATH, joinPath(outputDir, packageInfo.get(ConstValue.PACKAGE_MENU_JSON)));
        }
    }

    /**
     * 处理数据源配置
     * @param config
     */
    private void handlerDataSource(DataSourceConfig config) {
        connection = config.getConn();
        dbQuery = config.getDbQuery();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(dbQuery.dataBase());
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                dataSourceConfig.setDataBaseName(results.getString(1));
            }
            preparedStatement.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 处理数据库表 加载数据库表、列、注释相关数据集
     * @param config
     */
    private void handlerStrategy(StrategyConfig config) {
        String[] includeTables = config.getIncludeTable();
        Map<String, String> tableMap = config.getTableMap();
        if((Objects.isNull(includeTables)) && Objects.isNull(tableMap)){
            throw new RuntimeException("需要生成的表配置项不能为空！");
        }
        //需要反向生成的表信息
        List<TableInfo> includeTableList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(dbQuery.tablesSql());
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                String tableName = results.getString(dbQuery.tableName());
                if (StringUtils.isNotEmpty(tableName)) {
                    String tableComment = results.getString(dbQuery.tableComment());
                    if (config.isSkipView() && "VIEW".equals(tableComment)) {
                        // 跳过视图
                        continue;
                    }
                    TableInfo tableInfo = new TableInfo();
                    tableInfo.setName(tableName);
                    tableInfo.setComment(tableComment);
                    if (includeTables != null && includeTables.length > 0) {
                        for (String includeTable : includeTables) {
                            // 忽略大小写等于 或 正则 true
                            if (tableNameMatches(includeTable, tableName)) {
                                includeTableList.add(tableInfo);
                            }
                        }
                    } else if (Objects.nonNull(tableMap)) {
                        if(tableMap.containsKey(tableName)){
                            tableInfo.setEntityName(tableMap.get(tableName));
                            includeTableList.add(tableInfo);
                        }
                    }
                } else {
                    System.err.println("当前数据库为空！！！");
                }
            }
            includeTableList.forEach(ti -> convertTableFields(ti));
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        tableInfoList = processTable(includeTableList, config);
    }


    /**
     * 将字段信息与表信息关联
     * @param tableInfo
     * @return
     */
    private TableInfo convertTableFields(TableInfo tableInfo) {
        List<TableField> fieldList = new ArrayList<>();
        try {
            String tableFieldsSql = String.format(dbQuery.tableFieldsSql(), tableInfo.getName(), dataSourceConfig.getDataBaseName());
            PreparedStatement preparedStatement = connection.prepareStatement(tableFieldsSql);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                TableField field = new TableField();
                // 获取表字段信息
                field.setFieldName(results.getString(dbQuery.fieldName()));
                field.setFieldType(results.getString(dbQuery.fieldType()));
                field.setFieldLength(Integer.valueOf(results.getInt(dbQuery.fieldLength())).intValue());
                field.setComment(results.getString(dbQuery.fieldComment()));
                String key = results.getString(dbQuery.fieldKey());
                // 获取主键信息
                boolean isId = StringUtils.isNotEmpty(key) && "PRI".equals(key.toUpperCase());
                if(isId){
                    tableInfo.setPrimaryId(field.getFieldName());
                }
                //类型转换
                field.setFieldType(dataSourceConfig.getTypeConvert().convertJdbcType(field.getFieldName(), field.getFieldType()));
                field.setPropertyName(processName(field.getFieldName(), null));
                IColumnType columnType = dataSourceConfig.getTypeConvert().convertPropertyType(field.getFieldName(), field.getFieldType());
                field.setPropertyType(columnType.getType());
                field.setPropertyTypePackage(columnType.getPkg());
                boolean isTypeHandler = strategyConfig.getTypeHandler().enable(field.getFieldName());
                field.setIsTypeHandler(isTypeHandler);
                if(isTypeHandler){
                    String typeHandlerClass = strategyConfig.getTypeHandler().processTypeHandler(field.getFieldName());
                    field.setTypeHandler(typeHandlerClass);
                }
                fieldList.add(field);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("SQL Exception：" + e.getMessage());
        }
        tableInfo.setFields(fieldList);
        return tableInfo;
    }


    /**
     * 处理表对应的类名称
     * @param tableList
     * @param config
     * @return
     */
    private List<TableInfo> processTable(List<TableInfo> tableList, StrategyConfig config) {
        String[] tablePrefix = config.getTablePrefix();
        for (TableInfo tableInfo : tableList) {
            String entityName = (StringUtils.isNotBlank(tableInfo.getEntityName()) ? tableInfo.getEntityName() : NamingStrategyHelper.capitalFirstUpper(processName(tableInfo.getName(), tablePrefix)));
            if (StringUtils.isNotBlank(globalConfig.getEntityName())) {
                tableInfo.setEntityName(String.format(globalConfig.getEntityName(), entityName));
            } else {
                tableInfo.setEntityName(entityName);
            }
            if (StringUtils.isNotBlank(globalConfig.getRequestName())) {
                tableInfo.setRequestName(String.format(globalConfig.getRequestName(), entityName));
            } else {
                tableInfo.setRequestName(entityName + ConstValue.GLOBAL_REQUEST);
            }
            if (StringUtils.isNotBlank(globalConfig.getRequestAddName())) {
                tableInfo.setRequestAddName(String.format(globalConfig.getRequestAddName(), entityName));
            } else {
                tableInfo.setRequestAddName(entityName + ConstValue.GLOBAL_REQUEST_ADD);
            }
            if (StringUtils.isNotBlank(globalConfig.getRequestUpdateName())) {
                tableInfo.setRequestUpdateName(String.format(globalConfig.getRequestUpdateName(), entityName));
            } else {
                tableInfo.setRequestUpdateName(entityName + ConstValue.GLOBAL_REQUEST_UPDATE);
            }
            if (StringUtils.isNotBlank(globalConfig.getRequestSearchName())) {
                tableInfo.setRequestSearchName(String.format(globalConfig.getRequestSearchName(), entityName));
            } else {
                tableInfo.setRequestSearchName(entityName + ConstValue.GLOBAL_REQUEST_SEARCH);
            }
            if (StringUtils.isNotBlank(globalConfig.getDtoName())) {
                tableInfo.setDtoName(String.format(globalConfig.getDtoName(), entityName));
            } else {
                tableInfo.setDtoName(entityName + ConstValue.GLOBAL_DTO);
            }
            if (StringUtils.isNotBlank(globalConfig.getXmlName())) {
                tableInfo.setXmlName(String.format(globalConfig.getXmlName(), entityName));
            } else {
                tableInfo.setXmlName(entityName + ConstValue.GLOBAL_XML);
            }
            if (StringUtils.isNotBlank(globalConfig.getDaoName())) {
                tableInfo.setDaoName(String.format(globalConfig.getDaoName(), entityName));
            } else {
                tableInfo.setDaoName(entityName + ConstValue.GLOBAL_DAO);
            }
            if (StringUtils.isNotBlank(globalConfig.getServiceName())) {
                tableInfo.setServiceName(String.format(globalConfig.getServiceName(), entityName));
            } else {
                tableInfo.setServiceName(entityName + ConstValue.GLOBAL_SERVICE);
            }
            if (StringUtils.isNotBlank(globalConfig.getApiName())) {
                tableInfo.setApiName(String.format(globalConfig.getApiName(), entityName));
            } else {
                tableInfo.setApiName(entityName + ConstValue.GLOBAL_API);
            }
            if (StringUtils.isNotBlank(globalConfig.getProviderName())) {
                tableInfo.setProviderName(String.format(globalConfig.getProviderName(), entityName));
            } else {
                tableInfo.setProviderName(entityName + ConstValue.GLOBAL_PROVIDER);
            }
            if (StringUtils.isNotBlank(globalConfig.getJunitName())) {
                tableInfo.setJunitName(String.format(globalConfig.getJunitName(), entityName));
            } else {
                tableInfo.setJunitName(entityName + ConstValue.GLOBAL_JUNIT);
            }
        }
        return tableList;
    }

    /**
     * 处理表/字段名称
     * @param name
     * @param prefix
     * @return
     */
    private String processName(String name, String[] prefix) {
        boolean removePrefix = false;
        if (prefix != null && prefix.length >= 1) {
            removePrefix = true;
        }
        String propertyName;
        if (removePrefix) {
            propertyName = NamingStrategyHelper.removePrefixAndCamel(name, prefix);
        } else {
            // 下划线转驼峰
            propertyName = NamingStrategyHelper.underlineToCamel(name);
        }
        return propertyName;
    }

    /**
     * <p>
     * 表名匹配
     * </p>
     *
     * @param setTableName 设置表名
     * @param dbTableName  数据库表单
     * @return
     */
    private boolean tableNameMatches(String setTableName, String dbTableName) {
        return setTableName.equals(dbTableName)
                || matches(setTableName, dbTableName);
    }

    /**
     * <p>
     * 连接父子包名
     * </p>
     *
     * @param parent     父包名
     * @param subPackage 子包名
     * @return 连接后的包名
     */
    private String joinPackage(String parent, String subPackage) {
        if (StringUtils.isEmpty(parent)) {
            return subPackage;
        }
        return parent + StringPool.DOT + subPackage;
    }


    /**
     * 连接路径字符串
     * @param parentDir
     * @param packageName
     * @return
     */
    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isBlank(parentDir)) {
            parentDir = System.getProperty(ConstValue.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        return parentDir + packageName;
    }

    /**
     * 正则表达式匹配
     * @param regex
     * @param input
     * @return
     */
    private boolean matches(String regex, String input) {
        if (null == regex || null == input) {
            return false;
        }
        return Pattern.matches(regex, input);
    }

    // ==================================  相关配置  ==================================


    public Connection getConnection() {
        return connection;
    }

    public ConfigBuilder setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public IDbQuery getDbQuery() {
        return dbQuery;
    }

    public ConfigBuilder setDbQuery(IDbQuery dbQuery) {
        this.dbQuery = dbQuery;
        return this;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public ConfigBuilder setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }

    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    public ConfigBuilder setDataSourceConfig(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
        return this;
    }

    public Map<String, String> getPackageInfo() {
        return packageInfo;
    }

    public ConfigBuilder setPackageInfo(Map<String, String> packageInfo) {
        this.packageInfo = packageInfo;
        return this;
    }

    public StrategyConfig getStrategyConfig() {
        return strategyConfig;
    }

    public ConfigBuilder setStrategyConfig(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
        return this;
    }

    public Map<String, String> getPathInfo() {
        return pathInfo;
    }

    public ConfigBuilder setPathInfo(Map<String, String> pathInfo) {
        this.pathInfo = pathInfo;
        return this;
    }

    public List<TableInfo> getTableInfoList() {
        return tableInfoList;
    }

    public ConfigBuilder setTableInfoList(List<TableInfo> tableInfoList) {
        this.tableInfoList = tableInfoList;
        return this;
    }
}

