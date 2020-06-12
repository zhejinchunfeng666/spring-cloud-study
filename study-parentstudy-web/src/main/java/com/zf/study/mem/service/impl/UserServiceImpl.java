package com.zf.study.mem.service.impl;

import com.zf.study.mem.entity.User;
import com.zf.study.mem.mapper.UserMapper;
import com.zf.study.mem.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zf
 * @since 2020-04-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
