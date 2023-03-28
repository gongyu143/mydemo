package com.gongyu.mydemo.component;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "userdemo",url = "http://localhost:9111/")
public interface FeignDemo {

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/api/user/")
    String getUser();

}
