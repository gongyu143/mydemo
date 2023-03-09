package com.gongyu.mydemo.service.impl;

import com.gongyu.mydemo.bean.UserDo;
import com.gongyu.mydemo.bean.page.UserParam;
import com.gongyu.mydemo.dao.UserDao;
import com.gongyu.mydemo.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<UserDo> getAll(UserParam param) {
        return userDao.getAll(param);
    }
}
