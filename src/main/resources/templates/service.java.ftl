package ${package.service};

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Objects;
import com.yijiupi.basic.utils.IdGenerator;
import com.yijiupi.basic.utils.PageUtils;
import com.yijiupi.basic.request.IdRequest;

import ${superServiceClassPackage};
import ${package.dao}.${daoClass};
import ${package.entity}.${entityClass};
import ${package.request}.${requestSearchClass};
<#if isRequestAddOrUpdateEnable>
import ${package.request}.${requestAddClass};
import ${package.request}.${requestUpdateClass};
<#else>
import ${package.request}.${requestClass};
</#if>
import ${package.dto}.${dtoClass};


/**
 * <p>
 * ${entityClass} 服务层
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
public class ${serviceClass} extends ${superServiceClass}<${daoClass}, ${entityClass}> {

	private static final Logger log = LoggerFactory.getLogger(${serviceClass}.class);

	/**
	 * 分页列表查询
	 * @param request
	 */
	public PageUtils<${dtoClass}> selectPage(${requestSearchClass} request) {
		List<${dtoClass}> resultList = Lists.newArrayList();
		int count = super.baseMapper.countPage(request);
		List<${entityClass}> dbList = count > 0 ? super.baseMapper.selectPage(request) : Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(dbList)){
			dbList.forEach(source->{
				${dtoClass} target = new ${dtoClass}();
				BeanUtils.copyProperties(source, target);
				resultList.add(target);
			});
		}
		return new PageUtils<>(resultList, count, request);
	}

	/**
	 * 获取数据集（下拉框）
	 * @param request
	 */
	public List<${dtoClass}> selectAll(${requestSearchClass} request){
		List<${dtoClass}> resultList = Lists.newArrayList();
		List<${entityClass}> dbList = super.baseMapper.selectPage(request);
		if(CollectionUtils.isNotEmpty(dbList)){
			dbList.forEach(source->{
				${dtoClass} result = new ${dtoClass}();
				BeanUtils.copyProperties(source, result);
				resultList.add(result);
			});
		}
		return resultList;
	}

	/**
	 * 获取详情
	 * @param request
	 */
	public ${dtoClass} getDetail(IdRequest request){
		${entityClass} entity = baseMapper.selectByPrimaryKey(request.getId());
		if(Objects.nonNull(entity)){
			${dtoClass} result = new ${dtoClass}();
			BeanUtils.copyProperties(entity, result);
			return result;
		}
		return null;
	}



	/**
	 * 新增操作
	 * @param request
	 */
    @Transactional(rollbackFor = Exception.class)
	<#if isRequestAddOrUpdateEnable>
	public void add(${requestAddClass} request){
	<#else>
	public void add(${requestClass} request){
	</#if>
		${entityClass} entity = new ${entityClass}();
		BeanUtils.copyProperties(request, entity);
        entity.setId(IdGenerator.getId(${entityClass}.class.getName()));
        baseMapper.insertPrimaryKeySelective(entity);
	}

	/**
	 * 编辑操作
	 * @param request
	 */
    @Transactional(rollbackFor = Exception.class)
	<#if isRequestAddOrUpdateEnable>
	public void update(${requestUpdateClass} request){
	<#else>
	public void update(${requestClass} request){
	</#if>
		${entityClass} entity = new ${entityClass}();
		BeanUtils.copyProperties(request, entity);
        baseMapper.updatePrimaryKeySelective(entity);
	}

	/**
	 * 删除操作
	 * @param request
	 */
    @Transactional(rollbackFor = Exception.class)
	public void delete(IdRequest request){
        baseMapper.deleteByPrimaryKey(request.getId());
	}

}
