package com.zf.study.zuul.oauth2;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.csrf().disable()
	     // 基于token，所以不需要session
	     .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    }
}
