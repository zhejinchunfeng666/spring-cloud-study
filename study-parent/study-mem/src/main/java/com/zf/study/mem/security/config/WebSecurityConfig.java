package com.zf.study.mem.security.config;

import com.zf.study.mem.security.MyAuthenticationProvider;
import com.zf.study.mem.security.MyUserDetailService;
import com.zf.study.mem.security.handler.MyAuthenticationFailHander;
import com.zf.study.mem.security.handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 实现访问资源之前的拦截配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //用户验证成功的处理器
    @Autowired
    private MyAuthenticationSuccessHandler myAuthSuccessHander;

    //用户验证失败的处理器
    @Autowired
    private MyAuthenticationFailHander myAuthFailHander;
    /**
     * 自定义的UserDetailService，从数据库中获取相应的用户
     */
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;





    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService)
            .passwordEncoder(new BCryptPasswordEncoder());
        auth.authenticationProvider(myAuthenticationProvider);
    }




    /**
     * 这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
     * */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().loginProcessingUrl("/login").permitAll()
                .successHandler(myAuthSuccessHander).failureHandler(myAuthFailHander).permitAll()
                .and().logout().logoutUrl("/logout").permitAll()
                .and().authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
