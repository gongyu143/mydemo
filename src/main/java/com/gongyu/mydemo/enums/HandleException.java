package com.gongyu.mydemo.enums;

import com.gongyu.mydemo.bean.result.Response;
import com.gongyu.mydemo.bean.result.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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
        return Response.failure(e.getMessage());
    }

    @ExceptionHandler
    public Response<String> handleException(Exception e) {
        log.info("【系统异常】：{}", e.getMessage(), e);
        return Response.failure(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public Response<Object> handleVaildException(BindException e) {
        log.info("【bind异常】：{}", e.getMessage(), e);
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError) -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return Response.failure(ResponseCode.FAILURE.getMessage(),errorMap);
    }

}
