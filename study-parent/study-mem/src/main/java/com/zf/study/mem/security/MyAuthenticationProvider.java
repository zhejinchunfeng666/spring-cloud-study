package com.zf.study.mem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取用户名
        String username = authentication.getName();
        //获取密码
        String password = (String) authentication.getCredentials();
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

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
