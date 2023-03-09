package com.gongyu.mydemo.bean.result;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Response<T> implements Serializable {

    /**
     * 状态码
     */
    @JsonProperty("StatusCode")
    private int statusCode;

    /**
     * 提示消息（不展示）
     */
//    @JsonIgnore
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 返回数据集元素的个数
     */
    @JsonProperty("Total")
    private Integer total;

    /**
     * 成功操作，携带默认信息
     */
    public static <T> Response<T> success() {
        return success(ResponseCode.SUCCESS.getMessage());
    }



    /**
     * 成功操作, 携带成功状态码、自定义消息、数据和数据集中元素个数
     */
    public static <T> Response<T> success(String message, T data, Integer total) {
        return success(ResponseCode.SUCCESS.getCode(), message, data, total);
    }

    /**
     * 成功操作, 携带成功状态码、自定义消息、数据
     */
    public static <T> Response<T> success(String message, T data) {
        return success(ResponseCode.SUCCESS.getCode(), message, data, null);
    }

    /**
     * 成功操作, 携带成功状态码、自定义消息
     */
    public static <T> Response<T> success(String message) {
        return success(message, null);
    }

    /**
     * 成功操作, 携带成功状态码、默认消息、数据
     */
    public static <T> Response<T> success(T data,int total) {
        return success(ResponseCode.SUCCESS.getMessage(), data,total);
    }


//    /**
//     * 成功操作, 携带成功状态码、默认消息、数据
//     */
//    public static <T> Response<T> success(T data,Long total) {
//        return success(data,total);
//    }

    /**
     * 成功操作, 携带自定义成功状态码、自定义消息和数据
     */
    public static <T> Response<T> success(int code, String message) {
        return success(code, message, null, null);
    }

    /**
     * 成功操作, 携带自定义状态码、消息、数据和数据集中元素个数
     */
    public static <T> Response<T> success(int code, String message, T data, Integer total) {
        Response<T> result = new Response<T>();
        result.setStatusCode(code);
        result.setMsg(message);
        result.setData(data);
        result.setTotal(total);
        return result;
    }

    /**
     * 失败操作, 携带默认数据
     */
    public static <T> Response<T> failure() {
        return failure(ResponseCode.FAILURE.getMessage());
    }

    /**
     * 失败操作, 携带默认数据
     */
    public static <T> Response<T> failure(int code) {
        switch (code) {

        }
        return failure(code,ResponseCode.FAILURE.getMessage());
    }

    /**
     * 失败操作, 携带失败状态码、自定义消息
     */
    public static <T> Response<T> failure(String message) {
        return failure(message, null);
    }

    /**
     * 失败操作, 携带失败状态码、自定义消息、数据
     */
    public static <T> Response<T> failure(String message, T data) {
        return failure(ResponseCode.FAILURE.getCode(), message, data);
    }

    /**
     * 失败操作, 携带自定义状态码、自定义消息
     */
    public static <T> Response<T> failure(int code, String message) {
        return failure(code, message, null);
    }

    /**
     * 失败操作, 携带自定义状态码、消息、数据
     */
    public static <T> Response<T> failure(int code, String message, T data) {
        Response<T> result = new Response<T>();
        result.setStatusCode(code);
        result.setMsg(message);
        result.setData(data);
        return result;
    }
}

