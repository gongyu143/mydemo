package com.gongyu.mydemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// 使swagger2生效
@EnableWebSocket
@EnableSwagger2
@EnableFeignClients
@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.gongyu.mydemo.repository")
@MapperScan(basePackages = {"com.gongyu.mydemo.dao"})
public class MydemoApplication {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(MydemoApplication.class, args);
    }

}
