package com.gongyu.mydemo.service;

import com.gongyu.mydemo.bean.es.User;

import java.awt.print.Pageable;
import java.util.List;

public interface UserService {

    Object addUser();

    List<User> findAll();


    List<User> findByNamePage(String name);

    List<User> findByName(String name);

    List<User> findfuzzy(String name);

    Object update();

    Object delete();

}
