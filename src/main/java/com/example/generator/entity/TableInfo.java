package com.example.generator.entity;

import com.example.generator.constant.ConstValue;

import java.util.List;

/**
 * @author panzhi
 * @Description
 * @since 2021-03-11
 */
public class TableInfo {

    /**
     * 表名称
     */
    private String name;

    /**
     * 表注释
     */
    private String comment;

    /**
     * 主键ID
     */
    private String primaryId = ConstValue.TABLE_PRIMARY;

    /**
     * 各个类
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

    /**
     * 属性字段
     */
    private List<TableField> fields;

    public String getName() {
        return name;
    }

    public TableInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public TableInfo setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getPrimaryId() {
        return primaryId;
    }

    public TableInfo setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public TableInfo setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public String getRequestAddName() {
        return requestAddName;
    }

    public TableInfo setRequestAddName(String requestAddName) {
        this.requestAddName = requestAddName;
        return this;
    }

    public String getRequestUpdateName() {
        return requestUpdateName;
    }

    public TableInfo setRequestUpdateName(String requestUpdateName) {
        this.requestUpdateName = requestUpdateName;
        return this;
    }

    public String getRequestName() {
        return requestName;
    }

    public TableInfo setRequestName(String requestName) {
        this.requestName = requestName;
        return this;
    }

    public String getRequestSearchName() {
        return requestSearchName;
    }

    public TableInfo setRequestSearchName(String requestSearchName) {
        this.requestSearchName = requestSearchName;
        return this;
    }

    public String getDtoName() {
        return dtoName;
    }

    public TableInfo setDtoName(String dtoName) {
        this.dtoName = dtoName;
        return this;
    }

    public String getXmlName() {
        return xmlName;
    }

    public TableInfo setXmlName(String xmlName) {
        this.xmlName = xmlName;
        return this;
    }

    public String getDaoName() {
        return daoName;
    }

    public TableInfo setDaoName(String daoName) {
        this.daoName = daoName;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public TableInfo setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getApiName() {
        return apiName;
    }

    public TableInfo setApiName(String apiName) {
        this.apiName = apiName;
        return this;
    }

    public String getProviderName() {
        return providerName;
    }

    public TableInfo setProviderName(String providerName) {
        this.providerName = providerName;
        return this;
    }

    public String getJunitName() {
        return junitName;
    }

    public TableInfo setJunitName(String junitName) {
        this.junitName = junitName;
        return this;
    }

    public List<TableField> getFields() {
        return fields;
    }

    public TableInfo setFields(List<TableField> fields) {
        this.fields = fields;
        return this;
    }
}

