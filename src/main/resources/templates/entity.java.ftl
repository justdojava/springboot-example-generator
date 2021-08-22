package ${package.entity};

import java.io.Serializable;

<#list fieldsImportPackage as importPackage>
import ${importPackage};
</#list>

<#if superEntityClass??>
import ${superEntityClassPackage};
</#if>


/**
 * <p>
 * ${tableComment}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if superEntityClass??>
public class ${entityClass} extends ${superEntityClass} implements Serializable {
<#else>
public class ${entityClass} implements Serializable {
</#if>

	private static final long serialVersionUID = 1L;
	
	<#--属性遍历-->
	<#list columns as pro>

	/**
	 * ${pro.comment}
	 */
	private ${pro.propertyType} ${pro.propertyName};
	</#list>

	<#--属性get||set方法-->
	<#list columns as pro>
	public ${pro.propertyType} get${pro.propertyName?cap_first}() {
		return this.${pro.propertyName};
	}

	public ${entityClass} set${pro.propertyName?cap_first}(${pro.propertyType} ${pro.propertyName}) {
		this.${pro.propertyName} = ${pro.propertyName};
		return this;
	}
	</#list>
}
