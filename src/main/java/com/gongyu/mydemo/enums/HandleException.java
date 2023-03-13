package com.gongyu.mydemo.enums;

import com.gongyu.mydemo.bean.result.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class HandleException {

    @ExceptionHandler
    public Response handleBaseException(BaseException e) {
        log.info(e.getMessage());
        return Response.failure(e.getCode(), e.getMessage());
    }


    @ExceptionHandler
    public Response<String> handleRuntimeException(RuntimeException e) {
        log.info("**************RuntimeException**************");
        log.info("【运行异常】：{}", e.getMessage(), e);
        return Response.failure("运行异常");
    }

    @ExceptionHandler
    public Response<String> handleException(Exception e) {
        log.info("【系统异常】：{}", e.getMessage(), e);
        return Response.failure("系统异常");
    }


}
