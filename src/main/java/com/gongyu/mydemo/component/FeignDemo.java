package com.gongyu.mydemo.component;

import com.gongyu.mydemo.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "userdemo",url = "http://localhost:9111/",configuration = FeignConfiguration.class)
public interface FeignDemo {

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping(value = "/api/user/")
    String getUser();

}
