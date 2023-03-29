package com.gongyu.mydemo.controller;

import com.gongyu.mydemo.bean.es.User;
import com.gongyu.mydemo.bean.result.Response;
import com.gongyu.mydemo.repository.UserRepository;
import com.gongyu.mydemo.service.UserService;
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
    UserService userService;


    @GetMapping("/add")
    public Object addUser() {
        Object o = userService.addUser();
        return Response.success(o,1);
    }

    @GetMapping("/search")
    public Object searchUser() {
        Object all = userService.findAll();
        return Response.success(all,1);
    }

}
