package ${package.junit};

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.ActiveProfiles;
import com.yijiupi.basic.generator.menu.MenuJsonGenerator;
import com.yijiupi.basic.yapi.SwaggerGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

import com.yijiupi.basic.request.BaseResponse;
import com.yijiupi.basic.request.IdRequest;
import com.yijiupi.basic.utils.JacksonUtils;
import ${package.api}.${apiClass};
import ${package.provider}.${providerClass};
import ${package.request}.${requestSearchClass};
<#if isRequestAddOrUpdateEnable>
import ${package.request}.${requestAddClass};
import ${package.request}.${requestUpdateClass};
<#else>
import ${package.request}.${requestClass};
</#if>


/**
 * <p>
 * ${entityClass} 单元测试（请将当前类移动到src/test/java下）
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ${junitClass} {

	private static final Logger log = LoggerFactory.getLogger(${junitClass}.class);

	//公共参数
	private static String loginUserId = "";
	private static String loginUserName = "";
	private static String submitToken = "";
	private static String projectModuleCode = "";

	@Autowired
	private ${apiClass} ${apiClass?uncap_first};

	@Test
	public void selectPage(){
		${requestSearchClass} request = new ${requestSearchClass}();
		//公共参数
		request.setLoginUserId(loginUserId);
		request.setLoginUserName(loginUserName);
		request.setPage(1);
		request.setPageSize(20);

		//业务参数...

		log.info("请求参数：{}", JacksonUtils.toJson(request));
		BaseResponse baseResponse = ${apiClass?uncap_first}.selectPage(request);
		log.info("返回参数：{}", JacksonUtils.toJson(baseResponse));
	}

	@Test
	public void selectAll(){
		${requestSearchClass} request = new ${requestSearchClass}();
		//公共参数
		request.setLoginUserId(loginUserId);
		request.setLoginUserName(loginUserName);
		request.setPage(1);
		request.setPageSize(20);

		//业务参数...

		log.info("请求参数：{}", JacksonUtils.toJson(request));
		BaseResponse baseResponse = ${apiClass?uncap_first}.selectAll(request);
		log.info("返回参数：{}", JacksonUtils.toJson(baseResponse));
	}

	@Test
	public void getDetail(){
		IdRequest request = new IdRequest();
		//公共参数
		request.setLoginUserId(loginUserId);
		request.setLoginUserName(loginUserName);

		//业务参数...

		log.info("请求参数：{}", JacksonUtils.toJson(request));
		BaseResponse baseResponse = ${apiClass?uncap_first}.getDetail(request);
		log.info("返回参数：{}", JacksonUtils.toJson(baseResponse));
	}

	@Test
	public void add(){
		<#if isRequestAddOrUpdateEnable>
        ${requestAddClass} request = new ${requestAddClass}();
		<#else>
        ${requestClass} request = new ${requestClass}();
		</#if>
		//公共参数
		request.setLoginUserId(loginUserId);
		request.setLoginUserName(loginUserName);
		request.setSubmitToken(submitToken);

		//业务参数...

		log.info("请求参数：{}", JacksonUtils.toJson(request));
		BaseResponse baseResponse = ${apiClass?uncap_first}.add(request);
		log.info("返回参数：{}", JacksonUtils.toJson(baseResponse));
	}

	@Test
	public void edit(){
		<#if isRequestAddOrUpdateEnable>
	    ${requestUpdateClass} request = new ${requestUpdateClass}();
		<#else>
	    ${requestClass} request = new ${requestClass}();
		</#if>
		//公共参数
		request.setLoginUserId(loginUserId);
		request.setLoginUserName(loginUserName);
		request.setSubmitToken(submitToken);

		//业务参数...

		log.info("请求参数：{}", JacksonUtils.toJson(request));
		BaseResponse baseResponse = ${apiClass?uncap_first}.edit(request);
		log.info("返回参数：{}", JacksonUtils.toJson(baseResponse));
	}

	@Test
	public void delete(){
		IdRequest request = new IdRequest();
		//公共参数
		request.setLoginUserId(loginUserId);
		request.setLoginUserName(loginUserName);
		request.setSubmitToken(submitToken);

		//业务参数...

		log.info("请求参数：{}", JacksonUtils.toJson(request));
		BaseResponse baseResponse = ${apiClass?uncap_first}.delete(request);
		log.info("返回参数：{}", JacksonUtils.toJson(baseResponse));
	}

	@Test
	public void createYApiDocument(){
		//生成接口文档
		String yApiToken = "";
		SwaggerGenerator.generateAndImport(projectModuleCode, yApiToken, ${providerClass}.class);
	}

	@Test
	public void createMenuJsonDocument(){
		//生成菜单Json文档
		String resourceName = "菜单名称";//一级菜单名称
		String frontUrl = "/hello.html";//一级菜单路径
		MenuJsonGenerator.createMenuJson(projectModuleCode, resourceName, frontUrl, ${providerClass}.class);
	}

}
