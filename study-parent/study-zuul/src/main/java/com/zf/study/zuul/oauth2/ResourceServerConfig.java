package com.zf.study.zuul.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	private static final String AUTH_RESOURCE_SERVER = "AUTH-RESOURCE-SERVER";
	
	 public static final String AUTH_USER_ROLE_WEB = "ROLE_WEB";
	 
	 public static final String AUTH_USER_ROLE_SOA = "ROLE_SOA";

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			// Disable CSRF
			.csrf().disable()
			// Disable SESSION，Will not occur JSESSIONID
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and().authorizeRequests()
		    	// web安全管理-start
		    	// 所有鉴权接口URL都统一zf开头，登录接口URL以open开头
				.antMatchers("/api/web/zf/**").hasAnyAuthority(AUTH_USER_ROLE_WEB)
				// 登录允许
				.antMatchers("/api/web/open/**").permitAll()
		    	// web微服务安全管理-end
		    	
		    	// soa微服务安全管理-start
		    	.antMatchers("/api/soa/zf/**").hasAuthority(AUTH_USER_ROLE_SOA)
		    	// 登录允许
		    	.antMatchers("/api/soa/open/**").permitAll()
		    	// soa微服务安全管理-end
		    .anyRequest().fullyAuthenticated();
		
		// cache控制
		http.headers().cacheControl();
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// Authentication request failed: error="access_denied", error_description="Invalid token does not contain resource id (oauth2-resource)"
		resources.resourceId(AUTH_RESOURCE_SERVER).stateless(true);
		super.configure(resources);
	}
}
