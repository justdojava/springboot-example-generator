package ${package.provider};

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yijiupi.basic.acpect.DubboConfiguration;
import com.yijiupi.basic.request.BaseResponse;
import com.yijiupi.basic.request.IdRequest;
import com.yijiupi.basic.utils.PageUtils;
import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import ${package.api}.${apiClass};
import ${package.service}.${serviceClass};
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
 * ${entityClass} Api实现层
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@DubboConfiguration
@Service
@Api(value = "${tableComment}")
public class ${providerClass} implements ${apiClass} {

	@Autowired
	private ${serviceClass} ${serviceClass?uncap_first};

	@Override
    @ApiOperation(value = "查询分页" , notes="权限编码：${projectModuleCode}:${apiClass}:selectPage")
	public BaseResponse<PageUtils<${dtoClass}>> selectPage(${requestSearchClass} request){
		PageUtils<${dtoClass}> pageUtils = ${serviceClass?uncap_first}.selectPage(request);
		return BaseResponse.getSuccessResponse(pageUtils);
	}

	@Override
	@ApiOperation(value = "下拉框查询" , notes="权限编码：${projectModuleCode}:${apiClass}:selectAll")
	public BaseResponse<List<${dtoClass}>> selectAll(${requestSearchClass} request){
		List<${dtoClass}> resultList = ${serviceClass?uncap_first}.selectAll(request);
		return BaseResponse.getSuccessResponse(resultList);
	}

	@Override
    @ApiOperation(value = "获取详情" , notes="权限编码：${projectModuleCode}:${apiClass}:getDetail")
	public BaseResponse<${dtoClass}> getDetail(IdRequest request){
		${dtoClass} result = ${serviceClass?uncap_first}.getDetail(request);
		return BaseResponse.getSuccessResponse(result);
	}

//	@SubmitToken
	@Override
    @ApiOperation(value = "新增" , notes="权限编码：${projectModuleCode}:${apiClass}:add")
	<#if isRequestAddOrUpdateEnable>
	public BaseResponse add(${requestAddClass} request){
	<#else>
	public BaseResponse add(${requestClass} request){
	</#if>
		${serviceClass?uncap_first}.add(request);
		return BaseResponse.getSuccessResponse();
	}

//	@SubmitToken
	@Override
	@ApiOperation(value = "修改" , notes="权限编码：${projectModuleCode}:${apiClass}:edit")
	<#if isRequestAddOrUpdateEnable>
	public BaseResponse edit(${requestUpdateClass} request){
	<#else>
	public BaseResponse edit(${requestClass} request){
	</#if>
		${serviceClass?uncap_first}.update(request);
		return BaseResponse.getSuccessResponse();
	}

//	@SubmitToken
	@Override
    @ApiOperation(value = "删除" , notes="权限编码：${projectModuleCode}:${apiClass}:delete")
	public BaseResponse delete(IdRequest request){
		${serviceClass?uncap_first}.delete(request);
		return BaseResponse.getSuccessResponse();
	}
}
