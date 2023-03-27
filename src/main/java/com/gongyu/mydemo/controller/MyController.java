package com.gongyu.mydemo.controller;

import com.gongyu.mydemo.bean.UserDo;
import com.gongyu.mydemo.bean.page.PageResult;
import com.gongyu.mydemo.bean.page.UserParam;
import com.gongyu.mydemo.bean.result.Response;
import com.gongyu.mydemo.component.FeignDemo;
import com.gongyu.mydemo.enums.ApiIdempotent;
import com.gongyu.mydemo.service.MyService;
import com.gongyu.mydemo.service.token.TokenService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author wengwx
 * @date 2023/3/9 
 * @des  
 */

@RestController
@RequestMapping("/wwx")
@Api(tags = "demo")
public class MyController {

    private final MyService myService;
    private final TokenService tokenService;
    private final RedisTemplate redisTemplate;
    private final FeignDemo feignDemo;

    @Autowired
    public MyController(MyService myService,
                 TokenService tokenService,
                 RedisTemplate redisTemplate,
                        FeignDemo feignDemo) {
        this.myService = myService;
        this.tokenService = tokenService;
        this.redisTemplate = redisTemplate;
        this.feignDemo = feignDemo;
    }

    @GetMapping("/user")
    @Operation(summary = "分页查询")
    public Response user() {
        String user = feignDemo.getUser();
        return Response.success(user);
    }



    @GetMapping("/page")
    @Operation(summary = "分页查询")
    public Response query(UserParam param) {
        List<UserDo> all = myService.getAll(param);
        PageResult pageResult = new PageResult(all);
        return Response.success(pageResult,pageResult.getTotal().intValue());
    }

    @PostMapping("/add")
    @Operation(summary = "添加")
    public Response query(@RequestBody @Validated UserDo user) {
        myService.add(user);
        return Response.success();
    }


    @PostMapping("/get/token")
    public String  getToken(){
        String token = tokenService.createToken();
        if (StringUtils.isNotEmpty(token)) {
            return token;
        }
        return "";
    }


    @ApiIdempotent
    @PostMapping("/test/Idempotence")
    public String testIdempotence() {
        String businessResult = myService.test();
        if (StringUtils.isNotEmpty(businessResult)) {

            return businessResult;
        }
        return "";
    }

}
