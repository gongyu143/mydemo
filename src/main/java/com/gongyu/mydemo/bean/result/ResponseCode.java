package com.gongyu.mydemo.bean.result;


import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @author wengwx
 * @date 2023/3/9
 * @des
 */
@Getter
@AllArgsConstructor
public enum ResponseCode implements BaseStatus {
    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    BLANK(300,"不能为空哦"),
    /**
     * 失败
     */
    FAILURE(500, "操作失败");

    int code;
    String message;
}

