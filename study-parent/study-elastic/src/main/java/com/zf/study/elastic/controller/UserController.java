package com.zf.study.elastic.controller;

import cn.hutool.core.util.ObjectUtil;
import com.zf.study.elastic.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/get")
    public Object getUser(Integer id){
        Object o = userService.getUserById(id);
        return o;
    }
}
