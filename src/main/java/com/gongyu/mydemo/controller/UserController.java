package com.gongyu.mydemo.controller;

import com.gongyu.mydemo.bean.es.User;
import com.gongyu.mydemo.bean.result.Response;
import com.gongyu.mydemo.repository.UserRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "user")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    public Object addUser() {
        List<String> colorList = new ArrayList<>();
        colorList.add("red");
        colorList.add("white");
        colorList.add("black");
        User student = new User("1", "mhh", "济南", 12, new Date(), new String[]{"语文", "数学", "英语"}, colorList);
        User save = userRepository.save(student);
        return Response.success(save,1);
    }

    @GetMapping("/search")
    public Object searchUser() {
        Iterable<User> all = userRepository.findAll();
        List<User> list = new ArrayList<>();
        if (all.iterator().hasNext()) {
            list.add(all.iterator().next());
        }
        return Response.success(list,1);
    }

}
