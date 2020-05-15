package com.zf.study.mem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.study.mem.entity.User;

public interface UserService extends IService<User> {
    Object getUser(int i);
}
