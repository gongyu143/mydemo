package com.gongyu.mydemo.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.gongyu.mydemo.bean.UserDo;
import com.gongyu.mydemo.bean.page.UserParam;
import com.gongyu.mydemo.bean.result.Response;
import com.gongyu.mydemo.bean.result.ResponseCode;
import com.gongyu.mydemo.dao.UserDao;
import com.gongyu.mydemo.enums.BaseException;
import com.gongyu.mydemo.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public Object add(UserDo userDo) {

        if (StringUtils.isBlank(userDo.getRemark())){
            throw new BaseException(ResponseCode.BLANK);
        }
        userDao.add(userDo);
        return Response.success();
    }
}
