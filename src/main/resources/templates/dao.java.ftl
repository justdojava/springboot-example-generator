package ${package.dao};

import java.util.List;

import ${superDaoClassPackage};
import ${package.entity}.${entityClass};
import ${package.request}.${requestSearchClass};

/**
 * <p>
 * ${entityClass} 数据操作层
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${daoClass} extends ${superDaoClass}<${entityClass}> {

	int countPage(${requestSearchClass} request);

	List<${entityClass}> selectPage(${requestSearchClass} request);
}