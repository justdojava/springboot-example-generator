package com.example.generator;

import com.example.generator.common.dao.BaseMapper;
import com.example.generator.common.request.BaseRequest;
import com.example.generator.common.service.BaseService;
import com.example.generator.config.DataSourceConfig;
import com.example.generator.config.GlobalConfig;
import com.example.generator.config.PackageConfig;
import com.example.generator.config.StrategyConfig;
import com.example.generator.config.converts.ITypeHandler;
import com.example.generator.config.converts.apdater.MySqlTypeConvert;
import com.example.generator.config.rules.IColumnType;
import com.example.generator.config.type.DbType;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author panzhi
 * @Description
 * @since 2021-03-11
 */
public class CodeGeneratorDemo {

    /**
     * 作者
     */
    private static final String author = "panzhi";
    /**
     * 项目位置
     */
    private static final String projectPath = "/Users/panzhi/Documents/pz/coding/yjp-git/yjgj-git/yjgj-microservice-baseservice/basic-service-provider";//项目在硬盘上的基础路径
    /**
     * 生成类文件存放位置
     */
    private static final String classOutPath = projectPath + "/src/main/java";
    /**
     * 生成xxxMapper.xml文件存放位置
     */
    private static final String mapperXmlPath = projectPath + "/src/main/resources/META-INF/mapping";
    /**
     * 模块结构（模块路径）
     */
    private static final String modulePath = "com..project";

    /**
     * 模块名
     */
    private static final String moduleName = "user";

    /**
     * 指定表，支持正则批量生成
     */
    private static final String[] tableNames = {
            "test_db"
    };

    /**
     * 指定表和实体名称，如果两者都填写，默认优先取上面数组
     */
    private static final Map<String, String> tableMap = ImmutableMap.<String, String>builder()
            .put("test_db", "TestEntity")
            .build();

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(classOutPath);
//        gc.setXmlOutputDir(mapperXmlPath);
        gc.setAuthor(author);
        gc.setFileOverride(true);//是否覆盖旧文件
        gc.setOpen(true);//生成之后是否打开文件夹
        gc.setyApiEnable(false);//是否开启自动生成接口文档
        gc.setyApiToken("");//如果开启，必须填写接口文档token
        gc.setProjectModuleCode("hello");
        gc.setRequestAddOrUpdateEnable(false);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！默认可以不填写
        gc.setRequestAddName("%sRequestAdd");
        gc.setRequestUpdateName("%sRequestUpd");
        gc.setRequestName("%sRequest");
        gc.setRequestSearchName("%sRequestSearch");
        gc.setDtoName("%sDTO");
        gc.setXmlName("%sMapper");
        gc.setDaoName("%sMapper");
        gc.setServiceName("%sService");
        gc.setApiName("%sApi");
        gc.setProviderName("%sApiImpl");
        gc.setJunitName("%sJunit");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig().setUrl("jdbc:mysql://127.0.0.1:3306/yjgj_base")
                .setDriverName("com.mysql.jdbc.Driver")
                .setUsername("root")
                .setPassword("Hello@123456")
                .setDbType(DbType.MYSQL)
                .setTypeConvert(new MySqlTypeConvert(){

                    @Override
                    public IColumnType customerType(String fieldName, String fieldType) {
                        //支持针对某个特定字段进行实体类型转换，例如数据库类型为int，实体为Date
                        return null;
                    }
                });
        mpg.setDataSource(dsc);

        // 包路径配置，支持自定义各个包名，默认可以不填写
        PackageConfig pc = new PackageConfig();
        pc.setParent(modulePath);
        pc.setModuleName(moduleName);
        pc.setEntity("entity");
        pc.setRequest("request");
        pc.setDto("dto");
        pc.setXml("META-INF.mapper");
        pc.setMenuJson("META-INF.config");
        pc.setDao("dao");
        pc.setService("service");
        pc.setApi("api");
        pc.setProvider("dubbo");
        pc.setJunit("junit");
        mpg.setPackageInfo(pc);

        // 实体生成策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setSuperEntityClass(null);
        strategy.setSuperDtoClass(null);
        strategy.setSuperRequestClass(BaseRequest.class.getCanonicalName());
        strategy.setSuperDaoClass(BaseMapper.class.getCanonicalName());
        strategy.setSuperServiceClass(BaseService.class.getCanonicalName());
        strategy.setTablePrefix("ts_", "tb_", "td_");
        strategy.setIncludeTable(tableNames);
        strategy.setTableMap(tableMap);
        strategy.setTypeHandler(new ITypeHandler(){

            @Override
            public boolean enable(String fieldName) {
                //是否开启某个字段mybatis类型适配转换
                return false;
            }

            @Override
            public String processTypeHandler(String fieldName) {
                //返回需要转换的类名（含包路径）
                return null;
            }
        });
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}

