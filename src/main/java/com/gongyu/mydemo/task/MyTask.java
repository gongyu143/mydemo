package com.gongyu.mydemo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @author wengwx
 * @date 2023/3/9
 * @des  启动类
 */
@Component
@Slf4j
public class MyTask {



    @PostConstruct
    public void task() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

}
