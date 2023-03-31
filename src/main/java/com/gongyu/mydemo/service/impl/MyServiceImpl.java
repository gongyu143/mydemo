package com.gongyu.mydemo.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.gongyu.mydemo.bean.UserDo;
import com.gongyu.mydemo.bean.event.DemoEvent;
import com.gongyu.mydemo.bean.page.UserParam;
import com.gongyu.mydemo.bean.result.Response;
import com.gongyu.mydemo.bean.result.ResponseCode;
import com.gongyu.mydemo.dao.UserDao;
import com.gongyu.mydemo.enums.Asserts;
import com.gongyu.mydemo.enums.BaseException;
import com.gongyu.mydemo.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author wengwx
 * @date 2023/3/9
 * @des
 */

@Service
@Slf4j
public class MyServiceImpl implements MyService {

    @Autowired
    UserDao userDao;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public List<UserDo> getAll(UserParam param) {
//        Asserts.isTrue(false,ResponseCode.DEMO,"ceshiceshi");
        return userDao.getAll(param);
    }

    @Override
    public Object add(UserDo userDo) {
        // 监听动作
        applicationContext.publishEvent(new DemoEvent(this,userDo));
        if (StringUtils.isBlank(userDo.getRemark())){
            throw new BaseException(ResponseCode.BLANK);
        }
        userDo.setTs(new Timestamp(System.currentTimeMillis()));
        userDo.setGmtCreate(LocalDateTime.now());
        userDao.add(userDo);
        return Response.success();
    }

    @Override
    public String test() {
        return "a";
    }


}
