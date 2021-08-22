package com.example.generator.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author panzhi
 * @Description
 * @since 2021-03-11
 */
public class AssetUtil {

    public static void notEmpty(Object param, String message){
        if(Objects.isNull(param)){
            throw new RuntimeException(message);
        }
    }

    public static void notBlank(String param, String message){
        if(StringUtils.isBlank(param)){
            throw new RuntimeException(message);
        }
    }

}