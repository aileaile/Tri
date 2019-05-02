package com.LL.Triangle.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 对象转json字符串
     * @Author ll
     */
    public static String Obj2String (Object obj){
        String str = "error";
        try {
            str = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            Logger logger = LoggerFactory.getLogger(JsonUtil.class);
            logger.error("ERR:",e);
        }
        return str;
    }

    /**
     * 对象转json字节
     * @Author ll
     */
    public static byte[] Obj2Bytes (Object obj){
        byte[] bytes = null;
        try {
            bytes = objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            Logger logger = LoggerFactory.getLogger(JsonUtil.class);
            logger.error("ERR:",e);
        }
        return bytes;
    }

    /**
     * json字符串转对象
     * @Author ll
     */
    public static Object String2Obj (String jsonStr,Class<?> clazz){
        Object obj = null;
        try {
            obj = objectMapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            Logger logger = LoggerFactory.getLogger(JsonUtil.class);
            logger.error("ERR:",e);
        }
        return obj;
    }

    /**
     * json字节转对象
     * @Author ll
     */
    public static Object Bytes2Obj (byte[] bytes,Class<?> clazz){
        Object obj = null;
        try {
            obj = objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            Logger logger = LoggerFactory.getLogger(JsonUtil.class);
            logger.error("ERR:",e);
        }
        return obj;
    }

    /**
     * json字符串获取指定节点字符
     * @Author ll
     */
    public static String getNode(String json,String key){
        String result = null;
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            result = jsonNode.get(key).asText();
        } catch (IOException e) {
            Logger logger = LoggerFactory.getLogger(JsonUtil.class);
            logger.error("ERR:",e);
        }
        return result;
    }

}
