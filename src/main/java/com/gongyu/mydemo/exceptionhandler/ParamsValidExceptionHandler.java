package com.gongyu.mydemo.exceptionhandler;

import com.gongyu.mydemo.bean.result.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author llj
 * @date 2023/3/13 21:15
 * @description 此处valid 不生效 感谢llj友情提示
 *
 **/

@RestControllerAdvice()
@Order(1)
public class ParamsValidExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ParamsValidExceptionHandler.class);

    @ExceptionHandler(BindException.class)
    public Response<Object> handleVaildException(BindException e) {
        logger.info(e.getMessage(),e);
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError) -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return Response.success(errorMap,1);
    }

}
