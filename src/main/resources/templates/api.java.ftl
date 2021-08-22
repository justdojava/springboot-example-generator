package ${package.api};

import com.yijiupi.basic.request.BaseResponse;
import com.yijiupi.basic.request.IdRequest;
import com.yijiupi.basic.utils.PageUtils;
import java.util.List;
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
 * ${entityClass} Api层
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${apiClass} {

	/**
	 * 分页列表
	 * @param request
	 * @return
	 */
	BaseResponse<PageUtils<${dtoClass}>> selectPage(${requestSearchClass} request);

	/**
	 * 查询数据集（下拉框）
	 * @param request
	 * @return
	 */
	BaseResponse<List<${dtoClass}>> selectAll(${requestSearchClass} request);

	/**
	 * 查询详情
	 * @param request
	 * @return
	 */
	BaseResponse<${dtoClass}> getDetail(IdRequest request);

	/**
	 * 新增操作
	 * @param request
	 * @return
	 */
	<#if isRequestAddOrUpdateEnable>
	BaseResponse add(${requestAddClass} request);
	<#else>
	BaseResponse add(${requestClass} request);
	</#if>

	/**
	 * 编辑操作
	 * @param request
	 * @return
	 */
	<#if isRequestAddOrUpdateEnable>
	BaseResponse edit(${requestUpdateClass} request);
	<#else>
	BaseResponse edit(${requestClass} request);
	</#if>

	/**
	 * 删除操作
	 * @param request
     * @return
	 */
	BaseResponse delete(IdRequest request);
}
