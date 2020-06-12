package com.zf.study.auth.controller;

import com.zf.study.auth.scso.security.MyUserDetailService;
import com.zf.study.auth.scso.security.TokenUserInfo;
import com.zf.study.core.web.vo.StudyEcho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@RestController
public class LoginController {

    @Autowired
    private MyUserDetailService myUserDetailService;

    @RequestMapping("/logout")
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new StudyEcho<>(null);
    }


//    @RequestMapping("/login")
    public Object login(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //获取用户名
        String username = auth.getName();
        //获取密码
        String password = (String) auth.getCredentials();
        //判断用户是否存在
        TokenUserInfo tokenUserInfo = (TokenUserInfo) myUserDetailService.loadUserByUsername(username);
        if(tokenUserInfo == null){
            throw new BadCredentialsException("用户不存在");
        }
        String pwd = tokenUserInfo.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(password,pwd)){
            throw new BadCredentialsException("用户密码不正确");
        }
        Collection<? extends GrantedAuthority> authorities = tokenUserInfo.getAuthorities();
        return new UsernamePasswordAuthenticationToken(tokenUserInfo,password,authorities);
    }
}
