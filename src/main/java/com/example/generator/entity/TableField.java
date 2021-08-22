package com.example.generator.entity;

/**
 * @author panzhi
 * @Description
 * @since 2021-03-11
 */
public class TableField {

    /**
     * 数据库原字段名
     */
    private String fieldName;

    /**
     * 数据库原字段类型
     */
    private String fieldType;

    /**
     * 数据库原字段长度
     */
    private Integer fieldLength;

    /**
     * 转换成实体类的变量-变量注释
     */
    private String comment;

    /**
     * 转换成实体类的-变量名
     */
    private String propertyName;

    /**
     * 转换成实体类的-变量类型
     */
    private String propertyType;

    /**
     * 转换成实体类的-变量类型完整路径
     */
    private String propertyTypePackage;


    /**
     * 判断是否进行mybatis类型处理
     */
    private boolean isTypeHandler;

    /**
     * mybatis类型处理
     */
    private String typeHandler;


    public String getFieldName() {
        return fieldName;
    }

    public TableField setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public String getFieldType() {
        return fieldType;
    }

    public TableField setFieldType(String fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }

    public TableField setFieldLength(Integer fieldLength) {
        this.fieldLength = fieldLength;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public TableField setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public TableField setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public TableField setPropertyType(String propertyType) {
        this.propertyType = propertyType;
        return this;
    }

    public boolean getIsTypeHandler() {
        return isTypeHandler;
    }

    public TableField setIsTypeHandler(boolean typeHandler) {
        isTypeHandler = typeHandler;
        return this;
    }

    public String getTypeHandler() {
        return typeHandler;
    }

    public TableField setTypeHandler(String typeHandler) {
        this.typeHandler = typeHandler;
        return this;
    }

    public String getPropertyTypePackage() {
        return propertyTypePackage;
    }

    public TableField setPropertyTypePackage(String propertyTypePackage) {
        this.propertyTypePackage = propertyTypePackage;
        return this;
    }
}
