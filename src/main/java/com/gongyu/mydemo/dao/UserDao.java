package com.gongyu.mydemo.dao;

import com.gongyu.mydemo.bean.UserDo;
import com.gongyu.mydemo.bean.page.UserParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wengwx
 * @date 2023/3/9
 * @des
 */


@Mapper
@Repository
public interface UserDao {

    List<UserDo> getAll(UserParam param);

    int add(UserDo userDo);
}
