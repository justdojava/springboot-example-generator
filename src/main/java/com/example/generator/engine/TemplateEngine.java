package com.example.generator.engine;

import com.example.generator.builder.ConfigBuilder;
import com.example.generator.common.dao.BaseMapper;
import com.example.generator.common.request.BaseRequest;
import com.example.generator.common.service.BaseService;
import com.example.generator.config.GlobalConfig;
import com.example.generator.config.StrategyConfig;
import com.example.generator.constant.ConstValue;
import com.example.generator.constant.StringPool;
import com.example.generator.entity.TableField;
import com.example.generator.entity.TableInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author panzhi
 * @Description
 * @since 2021-03-11
 */
public class TemplateEngine {

    /**
     * 模版生成器
     */
    private Configuration configuration;

    /**
     * 配置信息
     */
    private ConfigBuilder configBuilder;

    /**
     * 模板引擎初始化
     * @param configBuilder
     * @return
     */
    public TemplateEngine init(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding(StringPool.UTF8);
        configuration.setClassForTemplateLoading(TemplateEngine.class, "/");
        return this;
    }

    /**
     * 处理输出目录
     * @return
     */
    public TemplateEngine mkdirs() {
        getConfigBuilder().getPathInfo().forEach((key, value) -> {
            File dir = new File(value);
            if (!dir.exists()) {
                boolean result = dir.mkdirs();
                if (result) {
                    System.out.println("创建目录： [" + value + "]");
                }
            }
        });
        return this;
    }

    /**
     * 输出 java xml 文件
     * @return
     */
    public TemplateEngine batchOutput() {
        try {
            List<TableInfo> tableInfoList = configBuilder.getTableInfoList();
            for (TableInfo tableInfo : tableInfoList) {
                Map<String, Object> objectMap = getObjectMap(tableInfo);
                Map<String, String> pathInfo = configBuilder.getPathInfo();
                // Entity.java
                if (StringUtils.isNotBlank(tableInfo.getEntityName()) && StringUtils.isNotBlank(pathInfo.get(ConstValue.ENTITY_PATH))) {
                    String entityFile = String.format((pathInfo.get(ConstValue.ENTITY_PATH) + File.separator + ConstValue.TEMPLATE_JAVA_SUFFIX), tableInfo.getEntityName());
                    if (isCreate(entityFile)) {
                        writer(objectMap, ConstValue.TEMPLATE_ENTITY, entityFile);
                    }
                }
                // Request.java
                if(!configBuilder.getGlobalConfig().isRequestAddOrUpdateEnable()){
                    if (StringUtils.isNotBlank(tableInfo.getRequestName()) && StringUtils.isNotBlank(pathInfo.get(ConstValue.REQUEST_PATH))) {
                        String requestFile = String.format((pathInfo.get(ConstValue.REQUEST_PATH) + File.separator + ConstValue.TEMPLATE_JAVA_SUFFIX), tableInfo.getRequestName());
                        if (isCreate(requestFile)) {
                            writer(objectMap, ConstValue.TEMPLATE_REQUEST, requestFile);
                        }
                    }
                } else {
                    // RequestAdd.java RequestUpd.java
                    if (StringUtils.isNotBlank(tableInfo.getRequestAddName()) && StringUtils.isNotBlank(pathInfo.get(ConstValue.REQUEST_PATH))) {
                        String requestAddFile = String.format((pathInfo.get(ConstValue.REQUEST_PATH) + File.separator + ConstValue.TEMPLATE_JAVA_SUFFIX), tableInfo.getRequestAddName());
                        if (isCreate(requestAddFile)) {
                            writer(objectMap, ConstValue.TEMPLATE_REQUEST_ADD, requestAddFile);
                        }
                    }
                    if (StringUtils.isNotBlank(tableInfo.getRequestUpdateName()) && StringUtils.isNotBlank(pathInfo.get(ConstValue.REQUEST_PATH))) {
                        String requestUpdFile = String.format((pathInfo.get(ConstValue.REQUEST_PATH) + File.separator + ConstValue.TEMPLATE_JAVA_SUFFIX), tableInfo.getRequestUpdateName());
                        if (isCreate(requestUpdFile)) {
                            writer(objectMap, ConstValue.TEMPLATE_REQUEST_UPDATE, requestUpdFile);
                        }
                    }
                }
                // RequestSearch.java
                if (StringUtils.isNotBlank(tableInfo.getRequestSearchName()) && StringUtils.isNotBlank(pathInfo.get(ConstValue.REQUEST_PATH))) {
                    String requestSearchFile = String.format((pathInfo.get(ConstValue.REQUEST_PATH) + File.separator + ConstValue.TEMPLATE_JAVA_SUFFIX), tableInfo.getRequestSearchName());
                    if (isCreate(requestSearchFile)) {
                        writer(objectMap, ConstValue.TEMPLATE_REQUEST_SEARCH, requestSearchFile);
                    }
                }
                // DTO.java
                if (StringUtils.isNotBlank(tableInfo.getDtoName()) && StringUtils.isNotBlank(pathInfo.get(ConstValue.DTO_PATH))) {
                    String dtoFile = String.format((pathInfo.get(ConstValue.DTO_PATH) + File.separator + ConstValue.TEMPLATE_JAVA_SUFFIX), tableInfo.getDtoName());
                    if (isCreate(dtoFile)) {
                        writer(objectMap, ConstValue.TEMPLATE_DTO, dtoFile);
                    }
                }
                // Mapper.xml
                if (StringUtils.isNotBlank(tableInfo.getXmlName()) && StringUtils.isNotBlank(pathInfo.get(ConstValue.XML_PATH))) {
                    String xmlFile = String.format((pathInfo.get(ConstValue.XML_PATH) + File.separator + ConstValue.TEMPLATE_XML_SUFFIX), tableInfo.getXmlName());
                    if (isCreate(xmlFile)) {
                        writer(objectMap, ConstValue.TEMPLATE_XML, xmlFile);
                    }
                }
                // Menu.json
                if (StringUtils.isNotBlank(pathInfo.get(ConstValue.MENU_PATH))) {
                    String menuJsonFile = pathInfo.get(ConstValue.MENU_PATH) + File.separator + ConstValue.TEMPLATE_JSON_SUFFIX;
                    if (isCreate(menuJsonFile)) {
                        writer(objectMap, ConstValue.TEMPLATE_MENU_JSON, menuJsonFile);
                    }
                }
                // Dao.java
                if (StringUtils.isNotBlank(tableInfo.getDaoName()) && StringUtils.isNotBlank(pathInfo.get(ConstValue.DAO_PATH))) {
                    String daoFile = String.format((pathInfo.get(ConstValue.DAO_PATH) + File.separator + ConstValue.TEMPLATE_JAVA_SUFFIX), tableInfo.getDaoName());
                    if (isCreate(daoFile)) {
                        writer(objectMap, ConstValue.TEMPLATE_DAO, daoFile);
                    }
                }
                // Service.java
                if (StringUtils.isNotBlank(tableInfo.getServiceName()) && StringUtils.isNotBlank(pathInfo.get(ConstValue.SERVICE_PATH))) {
                    String serviceFile = String.format((pathInfo.get(ConstValue.SERVICE_PATH) + File.separator + ConstValue.TEMPLATE_JAVA_SUFFIX), tableInfo.getServiceName());
                    if (isCreate(serviceFile)) {
                        writer(objectMap, ConstValue.TEMPLATE_SERVICE, serviceFile);
                    }
                }
                // Api.java
                if (StringUtils.isNotBlank(tableInfo.getApiName()) && StringUtils.isNotBlank(pathInfo.get(ConstValue.API_PATH))) {
                    String apiFile = String.format((pathInfo.get(ConstValue.API_PATH) + File.separator + ConstValue.TEMPLATE_JAVA_SUFFIX), tableInfo.getApiName());
                    if (isCreate(apiFile)) {
                        writer(objectMap, ConstValue.TEMPLATE_API, apiFile);
                    }
                }
                // Provider.java
                if (StringUtils.isNotBlank(tableInfo.getProviderName()) && StringUtils.isNotBlank(pathInfo.get(ConstValue.PROVIDER_PATH))) {
                    String providerFile = String.format((pathInfo.get(ConstValue.PROVIDER_PATH) + File.separator + ConstValue.TEMPLATE_JAVA_SUFFIX), tableInfo.getProviderName());
                    if (isCreate(providerFile)) {
                        writer(objectMap, ConstValue.TEMPLATE_PROVIDER, providerFile);
                    }
                }
                // Junit.java
                if (StringUtils.isNotBlank(tableInfo.getJunitName()) && StringUtils.isNotBlank(pathInfo.get(ConstValue.JUNIT_PATH))) {
                    String junitFile = String.format((pathInfo.get(ConstValue.JUNIT_PATH) + File.separator + ConstValue.TEMPLATE_JAVA_SUFFIX), tableInfo.getJunitName());
                    if (isCreate(junitFile)) {
                        writer(objectMap, ConstValue.TEMPLATE_JUNIT, junitFile);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("无法创建文件，请检查配置信息，错误信息：" +  e.getMessage());
        }
        return this;
    }

    /**
     * 打开输出目录
     */
    public void open() {
        String outDir = getConfigBuilder().getGlobalConfig().getOutputDir();
        if (getConfigBuilder().getGlobalConfig().isOpen()
                && StringUtils.isNotEmpty(outDir)) {
            try {
                String osName = System.getProperty("os.name");
                if (osName != null) {
                    if (osName.contains("Mac")) {
                        Runtime.getRuntime().exec("open " + outDir);
                    } else if (osName.contains("Windows")) {
                        Runtime.getRuntime().exec("cmd /c start " + outDir);
                    } else {
                        System.out.println("文件输出目录:" + outDir);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 将模板转化成为文件
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     */
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
        Template template = configuration.getTemplate(templatePath);
        FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFile));
        template.process(objectMap, new OutputStreamWriter(fileOutputStream, StringPool.UTF8));
        fileOutputStream.close();
        System.out.println("模板:" + templatePath + ";  文件:" + outputFile);
    };



    /**
     * 渲染对象 MAP 信息
     * @param tableInfo 表信息对象
     * @return
     */
    private Map<String, Object> getObjectMap(TableInfo tableInfo) {
        Map<String, Object> objectMap = new HashMap<>();
        ConfigBuilder config = getConfigBuilder();
        objectMap.put("package", config.getPackageInfo());
//        objectMap.put("resourceList", config.getResourceList());
        GlobalConfig globalConfig = config.getGlobalConfig();
        objectMap.put("projectModuleCode", globalConfig.getProjectModuleCode());
        objectMap.put("isRequestAddOrUpdateEnable", globalConfig.isRequestAddOrUpdateEnable());
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        objectMap.put("tableName", tableInfo.getName());
        objectMap.put("tableComment", tableInfo.getComment());
        objectMap.put("primaryId", tableInfo.getPrimaryId());
        objectMap.put("columns", tableInfo.getFields());
        objectMap.put("fieldsImportPackage", getFieldImportPackage(tableInfo.getFields()));
        objectMap.put("entityClass", tableInfo.getEntityName());
        objectMap.put("requestClass", tableInfo.getRequestName());
        objectMap.put("requestAddClass", tableInfo.getRequestAddName());
        objectMap.put("requestUpdateClass", tableInfo.getRequestUpdateName());
        objectMap.put("requestSearchClass", tableInfo.getRequestSearchName());
        objectMap.put("dtoClass", tableInfo.getDtoName());
        objectMap.put("daoClass", tableInfo.getDaoName());
        objectMap.put("serviceClass", tableInfo.getServiceName());
        objectMap.put("apiClass", tableInfo.getApiName());
        objectMap.put("providerClass", tableInfo.getProviderName());
        objectMap.put("junitClass", tableInfo.getJunitName());
        StrategyConfig strategyConfig = config.getStrategyConfig();
        //继承类封装处理
        if(StringUtils.isNotBlank(strategyConfig.getSuperEntityClass())){
            objectMap.put("superEntityClass", getSuperClassName(strategyConfig.getSuperEntityClass()));
            objectMap.put("superEntityClassPackage", strategyConfig.getSuperEntityClass());
        }
        if(StringUtils.isNotBlank(strategyConfig.getSuperRequestClass())){
            objectMap.put("superRequestClass", getSuperClassName(strategyConfig.getSuperRequestClass()));
            objectMap.put("superRequestClassPackage", strategyConfig.getSuperRequestClass());
        } else {
            objectMap.put("superRequestClass", BaseRequest.class.getSimpleName());
            objectMap.put("superRequestClassPackage", BaseRequest.class.getCanonicalName());
        }
        if(StringUtils.isNotBlank(strategyConfig.getSuperDtoClass())){
            objectMap.put("superDtoClass", getSuperClassName(strategyConfig.getSuperDtoClass()));
            objectMap.put("superDtoClassPackage", strategyConfig.getSuperDtoClass());
        }
        if(StringUtils.isNotBlank(strategyConfig.getSuperDaoClass())){
            objectMap.put("superDaoClass", getSuperClassName(strategyConfig.getSuperDaoClass()));
            objectMap.put("superDaoClassPackage", strategyConfig.getSuperDaoClass());
        } else {
            objectMap.put("superDaoClass", BaseMapper.class.getSimpleName());
            objectMap.put("superDaoClassPackage", BaseMapper.class.getCanonicalName());
        }
        if(StringUtils.isNotBlank(strategyConfig.getSuperServiceClass())){
            objectMap.put("superServiceClass", getSuperClassName(strategyConfig.getSuperServiceClass()));
            objectMap.put("superServiceClassPackage", strategyConfig.getSuperServiceClass());
        } else {
            objectMap.put("superServiceClass", BaseService.class.getSimpleName());
            objectMap.put("superServiceClassPackage", BaseService.class.getCanonicalName());
        }
        return objectMap;
    }


    private Set<String> getFieldImportPackage(List<TableField> fields){
        Set<String> fieldImportPackage =  new HashSet<>();
        if(Objects.nonNull(fields)){
            for (TableField field : fields) {
                if(StringUtils.isNotBlank(field.getPropertyTypePackage())){
                    fieldImportPackage.add(field.getPropertyTypePackage());
                }
            }
        }
        return fieldImportPackage;
    }


    /**
     * 检测文件是否存在
     *
     * @return 是否
     */
    private boolean isCreate(String filePath) {
        // 全局判断【默认】
        File file = new File(filePath);
        boolean exist = file.exists();
        if (!exist) {
            mkDir(file.getParentFile());
        }
        return !exist || configBuilder.getGlobalConfig().isFileOverride();
    }

    /**
     * 新建文件目录
     * @param file 文件
     */
    private void mkDir(File file) {
        if (file.getParentFile().exists()) {
            file.mkdir();
        } else {
            mkDir(file.getParentFile());
            file.mkdir();
        }
    }

    /**
     * 获取类名
     *
     * @param classPath
     * @return
     */
    private String getSuperClassName(String classPath) {
        if (StringUtils.isEmpty(classPath)) {
            return null;
        }
        return classPath.substring(classPath.lastIndexOf(StringPool.DOT) + 1);
    }



    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    public void setConfigBuilder(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
    }


}
