package com.zf.study.mem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zf.study.mem.entity.User;
import com.zf.study.mem.mapper.UserExtMapper;
import com.zf.study.mem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mem")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserExtMapper userExtMapper;

    /**
     * 查询单个用户
     * @param id
     * @return
     */
    @RequestMapping("/getUser")
    public Object getUser(@Param("id") String id){
        log.info("id:{}",id);
       User user = userService.lambdaQuery().eq(StringUtils.isNotEmpty(id), User::getId, Integer.valueOf(id)).one();
        return  user;
    }

    /**
     * 查询多个用户
     * @return
     */
    @RequestMapping("/getUserList")
    public List<User> getList(){
        List<User> list = userService.lambdaQuery().list();
        return list;
    }

    /**
     * 分页查询用户
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping("/getUserByPage")
    public IPage<User> getListByPage(@Param("pageNo") Long pageNo ,@Param("pageSize") Long pageSize){
        log.info("pageNo:{},pageSize:{}",pageNo,pageSize);
        IPage<User> userPage = userService.lambdaQuery().page(new Page<User>(pageNo, pageSize));
        List<User> userList = userPage.getRecords();
        return userPage;
    }

    @RequestMapping("/insertList")
    public String getIndex(){
        List<User> userList = new ArrayList();
        User user1 = new User(2,"zzf","11");
        User user2 = new User(3,"z1","15");
        User user3 = new User(4,"z2","16");
        User user4 = new User(5,"z4","22");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userService.saveBatch(userList);
        return "success";
    }

    @RequestMapping("/getIndex")
    public Object getUser2(){
        return userExtMapper.selectById(1);
    }
}
