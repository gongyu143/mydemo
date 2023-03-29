package com.gongyu.mydemo.service.impl;

import com.gongyu.mydemo.bean.es.User;
import com.gongyu.mydemo.repository.UserRepository;
import com.gongyu.mydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Object addUser() {
        List<String> colorList = new ArrayList<>();
        colorList.add("red");
        colorList.add("white");
        colorList.add("black");
        User student = new User("1", "mhh", "济南", 12, new Date(), new String[]{"语文", "数学", "英语"}, colorList);
        User save = userRepository.save(student);
        return save;
    }

    @Override
    public Object findAll() {
        Iterable<User> all = userRepository.findAll();
        List<User> list = new ArrayList<>();
        if (all.iterator().hasNext()) {
            list.add(all.iterator().next());
        }
        return list;
    }
}
