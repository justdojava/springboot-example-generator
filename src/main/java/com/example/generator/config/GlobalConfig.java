package com.example.generator.config;

/**
 * @author panzhi
 * @Description
 * @since 2021-03-11
 */
public class GlobalConfig {

    /**
     * 生成类文件存放位置
     */
    private String outputDir;

    /**
     * 生成xxxMapper.xml文件存放位置，默认会输出到META-INF/mapper
     */
    private String xmlOutputDir;

    /**
     * 是否覆盖源文件
     */
    private boolean fileOverride = true;

    /**
     * 是否打开输出目录
     */
    private boolean open = true;

    /**
     * 开发人员
     */
    private String author;

    /**
     * 是否开启文档生成器
     */
    private boolean yApiEnable = false;

    /**
     * 开启文档生成器之后，需要指定token
     */
    private String yApiToken;

    /**
     * 请求实体，add和update是否分开
     */
    private boolean requestAddOrUpdateEnable = true;

    /**
     * 项目编码
     */
    private String projectModuleCode;

    /**
     * 各层文件名称方式，例如： %sAction 生成 UserAction
     * %s 为占位符
     */
    private String entityName;

    private String requestAddName;

    private String requestUpdateName;

    private String requestName;

    private String requestSearchName;

    private String dtoName;

    private String xmlName;

    private String daoName;

    private String serviceName;

    private String apiName;

    private String providerName;

    private String junitName;

    public String getOutputDir() {
        return outputDir;
    }

    public GlobalConfig setOutputDir(String outputDir) {
        this.outputDir = outputDir;
        return this;
    }

    public String getXmlOutputDir() {
        return xmlOutputDir;
    }

    public GlobalConfig setXmlOutputDir(String xmlOutputDir) {
        this.xmlOutputDir = xmlOutputDir;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public GlobalConfig setAuthor(String author) {
        this.author = author;
        return this;
    }

    public boolean isyApiEnable() {
        return yApiEnable;
    }

    public GlobalConfig setyApiEnable(boolean yApiEnable) {
        this.yApiEnable = yApiEnable;
        return this;
    }

    public String getyApiToken() {
        return yApiToken;
    }

    public GlobalConfig setyApiToken(String yApiToken) {
        this.yApiToken = yApiToken;
        return this;
    }

    public boolean isRequestAddOrUpdateEnable() {
        return requestAddOrUpdateEnable;
    }

    public GlobalConfig setRequestAddOrUpdateEnable(boolean requestAddOrUpdateEnable) {
        this.requestAddOrUpdateEnable = requestAddOrUpdateEnable;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public GlobalConfig setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public String getRequestAddName() {
        return requestAddName;
    }

    public GlobalConfig setRequestAddName(String requestAddName) {
        this.requestAddName = requestAddName;
        return this;
    }

    public String getRequestUpdateName() {
        return requestUpdateName;
    }

    public GlobalConfig setRequestUpdateName(String requestUpdateName) {
        this.requestUpdateName = requestUpdateName;
        return this;
    }

    public String getRequestName() {
        return requestName;
    }

    public GlobalConfig setRequestName(String requestName) {
        this.requestName = requestName;
        return this;
    }

    public String getRequestSearchName() {
        return requestSearchName;
    }

    public GlobalConfig setRequestSearchName(String requestSearchName) {
        this.requestSearchName = requestSearchName;
        return this;
    }

    public String getDtoName() {
        return dtoName;
    }

    public GlobalConfig setDtoName(String dtoName) {
        this.dtoName = dtoName;
        return this;
    }

    public String getXmlName() {
        return xmlName;
    }

    public GlobalConfig setXmlName(String xmlName) {
        this.xmlName = xmlName;
        return this;
    }

    public String getDaoName() {
        return daoName;
    }

    public GlobalConfig setDaoName(String daoName) {
        this.daoName = daoName;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public GlobalConfig setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getApiName() {
        return apiName;
    }

    public GlobalConfig setApiName(String apiName) {
        this.apiName = apiName;
        return this;
    }

    public String getProviderName() {
        return providerName;
    }

    public GlobalConfig setProviderName(String providerName) {
        this.providerName = providerName;
        return this;
    }

    public String getJunitName() {
        return junitName;
    }

    public GlobalConfig setJunitName(String junitName) {
        this.junitName = junitName;
        return this;
    }

    public boolean isFileOverride() {
        return fileOverride;
    }

    public GlobalConfig setFileOverride(boolean fileOverride) {
        this.fileOverride = fileOverride;
        return this;
    }

    public boolean isOpen() {
        return open;
    }

    public GlobalConfig setOpen(boolean open) {
        this.open = open;
        return this;
    }

    public String getProjectModuleCode() {
        return projectModuleCode;
    }

    public GlobalConfig setProjectModuleCode(String projectModuleCode) {
        this.projectModuleCode = projectModuleCode;
        return this;
    }
}

