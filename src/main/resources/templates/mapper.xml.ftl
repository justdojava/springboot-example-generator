<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${package.dao}.${daoClass}">

	<!--BaseResultMap-->
    <resultMap id="BaseResultMap" type="${package.entity}.${entityClass}">
        <!--@Table `${tableName}`-->
    <#list columns as pro>
	<#if pro.propertyName == primaryId>
		<id column="${primaryId}" property="${primaryId}" jdbcType="${pro.fieldType}"/>
	<#else>
		<#if pro.isTypeHandler>
		<result column="${pro.fieldName}" property="${pro.propertyName}" jdbcType="${pro.fieldType}"
		        typeHandler="${pro.typeHandler}" />
        <#else>
	    <result column="${pro.fieldName}" property="${pro.propertyName}" jdbcType="${pro.fieldType}"/>
        </#if>
	</#if>
    </#list>
    </resultMap>
	
	<!--Base_Column_List-->
	<sql id="Base_Column_List">
		<#list columns as pro>
			<#if pro_index == 0>
			`${pro.fieldName}`
			<#else>
			,`${pro.fieldName}`
			</#if>
		</#list>
    </sql>

	<!--批量插入-->
	<insert id="insertList" parameterType="java.util.List">
		insert into `${tableName}` (
			<#list columns as pro>
				<#if pro_index == 0>
				`${pro.fieldName}`
				<#elseif pro_index == 1>
				,`${pro.fieldName}`
				<#else>
				,`${pro.fieldName}`
				</#if>
			</#list>
		)
		values
		<foreach collection ="list" item="obj" separator =",">
			<trim prefix=" (" suffix=")" suffixOverrides=",">
                <#list columns as pro>
	                <#if pro.isTypeHandler>
		            ${r"#{obj." + pro.propertyName + r",jdbcType=" + pro.fieldType + r",typeHandler=" + pro.typeHandler + r"}"},
                    <#else>
	                ${r"#{obj." + pro.propertyName + r"}"},
	                </#if>
                </#list>
			</trim>
		</foreach >
	</insert>

	<!--按需新增-->
	<insert id="insertPrimaryKeySelective" parameterType="${package.entity}.${entityClass}">
		insert into `${tableName}`
		<trim prefix="(" suffix=")" suffixOverrides=",">
            <#list columns as pro>
			<if test="${pro.propertyName} != null">
				`${pro.fieldName}`,
			</if>
			</#list>
		</trim>
		<trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list columns as pro>
			<if test="${pro.propertyName} != null">
                <#if pro.isTypeHandler>
                ${r"#{" + pro.propertyName + r",jdbcType=" + pro.fieldType + r",typeHandler=" + pro.typeHandler + r"}"},
                <#else>
                ${r"#{" + pro.propertyName + r",jdbcType=" + pro.fieldType + r"}"},
                </#if>
			</if>
            </#list>
		</trim>
	</insert>

	<!-- 按需修改-->
	<update id="updatePrimaryKeySelective" parameterType="${package.entity}.${entityClass}">
		update `${tableName}`
		<set>
            <#list columns as pro>
            <#if pro.fieldName != primaryId>
			<if test="${pro.propertyName} != null">
                <#if pro.isTypeHandler>
	            `${pro.fieldName}` = ${r"#{" + pro.propertyName + r",jdbcType=" + pro.fieldType + r",typeHandler=" + pro.typeHandler + r"}"},
                <#else>
	            `${pro.fieldName}` = ${r"#{" + pro.propertyName + r",jdbcType=" + pro.fieldType +r"}"},
                </#if>
			</if>
            </#if>
            </#list>
		</set>
		where ${primaryId} = ${r"#{" + "${primaryId}" + r",jdbcType=BIGINT}"}
	</update>

	<!-- 按需批量修改-->
	<update id="updateBatchByIds" parameterType="java.util.List">
		update `${tableName}`
		<trim prefix="set" suffixOverrides=",">
        <#list columns as pro>
        <#if pro.fieldName != primaryId && pro.fieldName != primaryId>
			<trim prefix="${pro.fieldName}=case" suffix="end,">
				<foreach collection="list" item="obj" index="index">
					<if test="obj.${pro.propertyName} != null">
                        <#if pro.isTypeHandler>
                        when id = ${r"#{" + "obj.id" + r"}"}
                        then  ${r"#{obj." + pro.propertyName + r",jdbcType=" + pro.fieldType + r",typeHandler=" + pro.typeHandler + r"}"}
                        <#else>
                        when id = ${r"#{" + "obj.id" + r"}"}
                        then  ${r"#{obj." + pro.propertyName + r",jdbcType=" + pro.fieldType + r"}"}
                        </#if>
					</if>
				</foreach>
			</trim>
        </#if>
        </#list>
		</trim>
		where
		<foreach collection="list" separator="or" item="obj" index="index" >
			id = ${r"#{" + "obj.id" + r"}"}
		</foreach>
	</update>

	<!-- 删除-->
	<delete id="deleteByPrimaryKey" parameterType="java.lang.Long">
		delete from `${tableName}`
		where `${primaryId}` = ${r"#{" + "${primaryId}" + r",jdbcType=BIGINT}"}
	</delete>

	<!-- 查询详情 -->
    <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="java.lang.Long">
        select
        <include refid="Base_Column_List"/>
        from `${tableName}`
	    where `${primaryId}` = ${r"#{" + "${primaryId}" + r",jdbcType=BIGINT}"}
    </select>

	<!-- 按需查询 -->
	<select id="selectByPrimaryKeySelective" resultMap="BaseResultMap" parameterType="${package.entity}.${entityClass}">
		select
		<include refid="Base_Column_List"/>
		from `${tableName}`
		<include refid="whereSql"/>
		order by ${primaryId} desc
	</select>

    <!-- 批量查询-->
    <select id="selectByIds" resultMap="BaseResultMap" parameterType="java.util.List">
		select
		<include refid="Base_Column_List"/>
		from `${tableName}`
        <where>
	        <if test="ids != null and ids.size() > 0">
		        and `${primaryId}` in
		        <foreach item="item" index="index" collection="ids" open="(" separator="," close=")">
                    ${r"#{" + "item" + r"}"}
		        </foreach>
	        </if>
        </where>
	    order by ${primaryId} desc
	</select>

	<!-- 根据条件查询 -->
	<select id="selectByMap" resultMap="BaseResultMap" parameterType="java.util.Map">
		select
		<include refid="Base_Column_List"/>
		from `${tableName}`
		order by ${primaryId} desc
	</select>

	<!-- 查询${entityClass}总和 -->
	<select id="countPage" resultType="int" parameterType="${package.request}.${requestSearchClass}">
		select count(${primaryId})
		from `${tableName}`
        <include refid="pageWhereSql"/>
	</select>
    
    <!-- 查询${entityClass}列表 -->
    <select id="selectPage" resultMap="BaseResultMap" parameterType="${package.request}.${requestSearchClass}">
        select
        <include refid="Base_Column_List"/>
        from `${tableName}`
        <include refid="pageWhereSql"/>
        order by ${primaryId} desc
	    <if test="pageEnable">
		    limit ${r"#{" + "start,jdbcType=INTEGER" + r"}"},${r"#{" + "end,jdbcType=INTEGER" + r"}"}
	    </if>
    </select>

	<sql id="whereSql">
		<!--@sql select * from ${tableName} -->
		<where>
<#--            <#list columns as pro>-->
<#--            <#if pro.propertyName != 'isDelete'-->
<#--            && pro.propertyName != 'createUserid'-->
<#--            && pro.propertyName != 'createUserName'-->
<#--            && pro.propertyName != 'createTime'-->
<#--            && pro.propertyName != 'createtime'-->
<#--            && pro.propertyName != 'updateUserid'-->
<#--            && pro.propertyName != 'updateUserName'-->
<#--            && pro.propertyName != 'updateTime'-->
<#--            && pro.propertyName != 'lastupdatetime'-->
<#--            >-->
<#--			<if test="${pro.propertyName} != null">-->
<#--				and ${pro.fieldName} = ${r"#{" + pro.propertyName + r",jdbcType=" + pro.fieldType +r"}"}-->
<#--			</if>-->
<#--            </#if>-->
<#--            </#list>-->
		</where>
	</sql>

    <sql id="pageWhereSql">
        <!--@sql select * from ${tableName} -->
        <where>
<#--            <#list columns as pro>-->
<#--            <#if pro.propertyName != 'isDelete'-->
<#--            && pro.propertyName != 'createUserid'-->
<#--            && pro.propertyName != 'createUserName'-->
<#--            && pro.propertyName != 'createTime'-->
<#--            && pro.propertyName != 'createtime'-->
<#--            && pro.propertyName != 'updateUserid'-->
<#--            && pro.propertyName != 'updateUserName'-->
<#--            && pro.propertyName != 'updateTime'-->
<#--            && pro.propertyName != 'lastupdatetime'-->
<#--            && pro.propertyName != primaryId-->
<#--            >-->
<#--            <if test="${pro.propertyName} != null">-->
<#--	            and ${pro.fieldName} = ${r"#{" + pro.propertyName + r",jdbcType=" + pro.fieldType +r"}"}-->
<#--            </if>-->
<#--            </#if>-->
<#--            </#list>-->
        </where>
    </sql>
    
</mapper>
