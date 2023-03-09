package com.gongyu.mydemo.controller;

import com.gongyu.mydemo.bean.UserDo;
import com.gongyu.mydemo.bean.page.PageResult;
import com.gongyu.mydemo.bean.page.UserParam;
import com.gongyu.mydemo.bean.result.Response;
import com.gongyu.mydemo.service.MyService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    MyService myService;

    @GetMapping("/q")
    @Operation(summary = "分页查询")
    public Object query(UserParam param) {

        List<UserDo> all = myService.getAll(param);
        PageResult pageResult = new PageResult(all);
        return Response.success(pageResult,pageResult.getTotal().intValue());

    }

}
