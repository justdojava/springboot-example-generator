package ${package.request};

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

<#list fieldsImportPackage as importPackage>
import ${importPackage};
</#list>

import ${superRequestClassPackage};


/**
 * <p>
 * ${tableComment}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@ApiModel
public class ${requestSearchClass} extends ${superRequestClass} implements Serializable {

	private static final long serialVersionUID = 1L;

	<#--属性遍历-->
	<#list columns as pro>
    <#if pro.propertyName != 'isDelete'
    && pro.propertyName != 'createUserid'
    && pro.propertyName != 'createUserName'
    && pro.propertyName != 'createTime'
    && pro.propertyName != 'createtime'
    && pro.propertyName != 'updateUserid'
    && pro.propertyName != 'updateUserName'
    && pro.propertyName != 'updateTime'
    && pro.propertyName != 'lastupdatetime'
    && pro.propertyName != primaryId
    >

	/**
	 * ${pro.comment}
	 */
	@ApiModelProperty(value = "${pro.comment}")
    private ${pro.propertyType} ${pro.propertyName};
    </#if>
	</#list>

	<#--属性get||set方法-->
	<#list columns as pro>
    <#if pro.propertyName != 'isDelete'
    && pro.propertyName != 'createUserid'
    && pro.propertyName != 'createUserName'
    && pro.propertyName != 'createTime'
    && pro.propertyName != 'createtime'
    && pro.propertyName != 'updateUserid'
    && pro.propertyName != 'updateUserName'
    && pro.propertyName != 'updateTime'
    && pro.propertyName != 'lastupdatetime'
    && pro.propertyName != primaryId
    >
	public ${pro.propertyType} get${pro.propertyName?cap_first}() {
		return this.${pro.propertyName};
	}

    public ${requestSearchClass} set${pro.propertyName?cap_first}(${pro.propertyType} ${pro.propertyName}) {
        <#if pro.propertyType == 'String'>
	    this.${pro.propertyName} = ${pro.propertyName} == null ? null : ${pro.propertyName}.trim();
        <#else>
	    this.${pro.propertyName} = ${pro.propertyName};
        </#if>
        return this;
    }
    </#if>
	</#list>
}