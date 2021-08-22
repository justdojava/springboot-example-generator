package com.example.generator.util;

import com.example.generator.constant.StringPool;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author panzhi
 * @Description
 * @since 2021-03-11
 */
public class NamingStrategyHelper {

    /**
     * 去掉下划线前缀且将后半部分转成驼峰格式
     * @param name
     * @param tablePrefix
     * @return
     */
    public static String removePrefixAndCamel(String name, String[] tablePrefix) {
        return underlineToCamel(removePrefix(name, tablePrefix));
    }

    /**
     * 去掉指定的前缀
     * @param name
     * @param prefix
     * @return
     */
    public static String removePrefix(String name, String... prefix) {
        if (StringUtils.isEmpty(name)) {
            return StringPool.EMPTY;
        }
        if (null != prefix) {
            // 判断是否有匹配的前缀，然后截取前缀
            // 删除前缀
            return Arrays.stream(prefix).filter(pf -> name.toLowerCase()
                    .matches(StringPool.HAT + pf.toLowerCase() + ".*"))
                    .findFirst().map(pf -> name.substring(pf.length())).orElse(name);
        }
        return name;
    }

    /**
     * 下划线转驼峰命名
     * @param name
     * @return
     */
    public static String underlineToCamel(String name) {
        // 快速检查
        if (StringUtils.isEmpty(name)) {
            // 没必要转换
            return StringPool.EMPTY;
        }
        // 如果不包含下划线，首字母小些
        if (!StringUtils.contains(name, StringPool.UNDERLINE)) {
            return capitalFirstLower(name);
        }
        String tempName = name.toLowerCase();
        StringBuilder result = new StringBuilder();
        // 用下划线将原始字符串分割
        String[] camels = tempName.split(StringPool.UNDERLINE);
        // 跳过原始字符串中开头、结尾的下换线或双重下划线
        // 处理真正的驼峰片段
        Arrays.stream(camels).filter(camel -> StringUtils.isNotEmpty(camel)).forEach(camel -> {
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel);
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(capitalFirstUpper(camel));
            }
        });
        return result.toString();
    }

    /**
     * 实体首字母大写
     * @param name
     * @return
     */
    public static String capitalFirstUpper(String name) {
        if (StringUtils.isNotEmpty(name)) {
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return StringPool.EMPTY;
    }

    /**
     * 实体首字母小写
     * @param name
     * @return
     */
    public static String capitalFirstLower(String name) {
        if (StringUtils.isNotEmpty(name)) {
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        return StringPool.EMPTY;
    }


}

