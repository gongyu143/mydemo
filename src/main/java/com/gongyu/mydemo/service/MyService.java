package com.gongyu.mydemo.service;

import com.gongyu.mydemo.bean.UserDo;
import com.gongyu.mydemo.bean.page.UserParam;

import java.util.List;

/**
 * @author wengwx
 * @date 2023/3/9
 * @des
 */

public interface MyService {

    List<UserDo> getAll(UserParam param);

    Object add(UserDo userDo);
}
