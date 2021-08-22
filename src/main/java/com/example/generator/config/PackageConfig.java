package com.example.generator.config;

import org.apache.commons.lang3.StringUtils;

/**
 * @author panzhi
 * @Description
 * @since 2021-03-11
 */
public class PackageConfig {

    /**
     * 父包名。如果为空，将下面子包名必须写全部，否则就只需写子包名
     */
    private String parent;

    /**
     * 父包模块名
     */
    private String moduleName = null;

    /**
     * Entity包名
     */
    private String entity = "entity";

    /**
     * Request包名
     */
    private String request = "request";

    /**
     * Dto包名
     */
    private String dto = "dto";

    /**
     * Mapper XML包名
     */
    private String xml = "META-INF.mapper";

    /**
     * Menu json包名
     */
    private String menuJson = "META-INF.config";

    /**
     * Dao包名
     */
    private String dao = "dao";

    /**
     * Service包名
     */
    private String service = "service";

    /**
     * Api包名
     */
    private String api = "api";

    /**
     * Provider包名
     */
    private String provider = "dubbo";

    /**
     * Junit包名
     */
    private String junit = "junit";


    /**
     * 父包名
     */
    public String getParent() {
        if (StringUtils.isNotBlank(moduleName)) {
            return parent + "." + moduleName;
        }
        return parent;
    }

    public PackageConfig setParent(String parent) {
        this.parent = parent;
        return this;
    }

    public String getModuleName() {
        return moduleName;
    }

    public PackageConfig setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public String getEntity() {
        return entity;
    }

    public PackageConfig setEntity(String entity) {
        this.entity = entity;
        return this;
    }

    public String getRequest() {
        return request;
    }

    public PackageConfig setRequest(String request) {
        this.request = request;
        return this;
    }

    public String getDto() {
        return dto;
    }

    public PackageConfig setDto(String dto) {
        this.dto = dto;
        return this;
    }

    public String getDao() {
        return dao;
    }

    public PackageConfig setDao(String dao) {
        this.dao = dao;
        return this;
    }

    public String getService() {
        return service;
    }

    public PackageConfig setService(String service) {
        this.service = service;
        return this;
    }

    public String getApi() {
        return api;
    }

    public PackageConfig setApi(String api) {
        this.api = api;
        return this;
    }

    public String getProvider() {
        return provider;
    }

    public PackageConfig setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public String getJunit() {
        return junit;
    }

    public PackageConfig setJunit(String junit) {
        this.junit = junit;
        return this;
    }

    public String getXml() {
        return xml;
    }

    public PackageConfig setXml(String xml) {
        this.xml = xml;
        return this;
    }

    public String getMenuJson() {
        return menuJson;
    }

    public PackageConfig setMenuJson(String menuJson) {
        this.menuJson = menuJson;
        return this;
    }
}

