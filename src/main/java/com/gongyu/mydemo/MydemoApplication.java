package com.gongyu.mydemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// 使swagger2生效
@EnableSwagger2
@EnableFeignClients
@SpringBootApplication
@MapperScan(basePackages = {"com.gongyu.mydemo.dao"})
public class MydemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MydemoApplication.class, args);
    }

}
