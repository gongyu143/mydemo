package com.gongyu.mydemo.service.impl;

import com.gongyu.mydemo.bean.es.User;
import com.gongyu.mydemo.bean.result.Response;
import com.gongyu.mydemo.repository.UserRepository;
import com.gongyu.mydemo.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Object addUser() {
        List<String> colorList = new ArrayList<>();
        colorList.add("唱");
        colorList.add("跳");
        colorList.add("rap");
        User student = new User("3", "阿学", "白琳", 26, new Date(), new String[]{"高", "富", "帅"}, colorList);
        User save = userRepository.save(student);
        return Response.success("添加成功",save);
    }

    @Override
    public List<User> findAll() {
        Iterable<User> all = userRepository.findAll();
        List<User> list = new ArrayList<>();
        Iterator<User> iterator = all.iterator();
        while (iterator.hasNext()) {
            User next = iterator.next();
            list.add(next);
        }
        return list;
    }

    @Override
    public List<User> findByNamePage(String name) {
        Pageable pageable  = PageRequest.of(1,1);
        List<User> bysName = userRepository.findStudentBysNameContaining(name, pageable);
        return bysName;
    }

    @Override
    public List<User> findByName(String name) {
        List<User> userList = userRepository.findStudentBysName(name);

        return userList;
    }

    @Override
    public List<User> findfuzzy(String name) {
        return userRepository.findStudentBysNameContaining(name);
    }

    @Override
    public Object update() {
        Optional<User> user = userRepository.findById("2");
        user.get().setSAge(39);
        User save = userRepository.save(user.get());
        return Response.success("更新成功",save);
    }

    @Override
    public Object delete() {
        userRepository.deleteById("1");
        return Response.success("删除成功");
    }


}
