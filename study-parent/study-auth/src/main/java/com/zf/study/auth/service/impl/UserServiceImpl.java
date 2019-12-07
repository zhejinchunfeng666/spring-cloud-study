package com.zf.study.auth.service.impl;

import com.zf.study.auth.service.UserService;
import com.zf.study.data.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Object selectById(Integer i) {
        return userMapper.selectUserById(i);
    }
}
