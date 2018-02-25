package com.LL.Triangle.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String Obj2String (Object obj){
        String str = "error";
        try {
            str = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static byte[] Obj2Bytes (Object obj){
        byte[] bytes = null;
        try {
            bytes = objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static Object String2Obj (String jsonStr,Class<?> clazz){
        Object obj = null;
        try {
            obj = objectMapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Object Bytes2Obj (byte[] bytes,Class<?> clazz){
        Object obj = null;
        try {
            obj = objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
