package com.gongyu.mydemo.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
/**
 * @author wengwx
 * @date 2023/3/9
 * @des
 */

public class JsonUtils {
    public static <T> String parseObjToJson(T object) {
        String string = null;
        try {
//            string = JSON.toJSONString(object);
            string = JSONObject.toJSONString(object);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return string;
    }


    public static <T> T parseJsonToObj(String json, Class<T> c) {
        try {
            //两个都是可行的，起码我测试的时候是没问题的。
            JSONObject jsonObject = JSONObject.parseObject(json);
//            JSONObject jsonObject = JSON.parseObject(json);
            return JSON.toJavaObject(jsonObject, c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
