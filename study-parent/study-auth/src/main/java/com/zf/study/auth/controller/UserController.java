package com.zf.study.auth.controller;

import com.zf.study.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/getUser")
    public Object getUser(){
       return userService.selectById(1);
    }

}
