package com.zf.study.mem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.study.mem.entity.User;
import com.zf.study.mem.mapper.UserExtMapper;
import com.zf.study.mem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserExtMapper, User> implements UserService {

    @Autowired
   UserExtMapper userExtMapper;

    @Override
    public Object getUser(int i) {
        return userExtMapper.selectById(1);
    }
}
