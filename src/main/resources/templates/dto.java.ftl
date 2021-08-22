package ${package.dto};

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

<#list fieldsImportPackage as importPackage>
import ${importPackage};
</#list>

<#if superDtoClass??>
import ${superDtoClassPackage};
</#if>


/**
 * <p>
 * ${tableComment}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@ApiModel
<#if superDtoClass??>
public class ${dtoClass} extends ${superDtoClass} implements Serializable {
<#else>
public class ${dtoClass} implements Serializable {
</#if>

	private static final long serialVersionUID = 1L;

	<#--属性遍历-->
	<#list columns as pro>

	/**
	 * ${pro.comment}
	 */
	@ApiModelProperty(value = "${pro.comment}")
	private ${pro.propertyType} ${pro.propertyName};
	</#list>

	<#--属性get||set方法-->
	<#list columns as pro>
	public ${pro.propertyType} get${pro.propertyName?cap_first}() {
		return this.${pro.propertyName};
	}

	public ${dtoClass} set${pro.propertyName?cap_first}(${pro.propertyType} ${pro.propertyName}) {
		this.${pro.propertyName} = ${pro.propertyName};
		return this;
	}
	</#list>
}
