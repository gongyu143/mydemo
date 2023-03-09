package com.gongyu.mydemo.bean.result;


import lombok.Getter;
/**
 * @author wengwx
 * @date 2023/3/9
 * @des
 */

public enum ResponseCode implements BaseStatus {
    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 失败
     */
    FAILURE(500, "操作失败");




    /**
     * 状 态 码
     */
    @Getter
    private final int code;

    /**
     * 携 带 消 息
     */
    @Getter
    private final String message;

    /**
     * 构 造 方 法
     */
    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

