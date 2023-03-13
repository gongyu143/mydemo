package com.gongyu.mydemo.enums;

import com.gongyu.mydemo.bean.result.BaseStatus;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class BaseException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(BaseException.class);
    private int code;

    private String message;

    public BaseException(){}

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(String message){
        super(message);
        this.message = message;
    }

    public BaseException(BaseStatus baseStatus){
        super(baseStatus.getMessage());
        this.code = baseStatus.getCode();
        this.message = String.format(baseStatus.getMessage());
    }



}
