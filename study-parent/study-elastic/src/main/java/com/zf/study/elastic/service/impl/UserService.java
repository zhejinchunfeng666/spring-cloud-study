package com.zf.study.elastic.service.impl;

import com.zf.study.data.mapper.UserMapper;
import com.zf.study.elastic.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Object getUserById(Integer id) {
        return userMapper.selectUserById(id);
    }
}
