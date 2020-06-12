package com.zf.study.auth.controller;

import com.zf.study.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/getUser")
    public Object getUser(){
       return userService.selectById(1);
    }

    @RequestMapping("/auth/user")
    public Principal user(Principal user) {
        log.info("user info: "+user);
        return user;
    }

}
