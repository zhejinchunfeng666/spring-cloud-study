package com.zf.study.auth.scso.auth.config;

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
	
	    @Override
	    public void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    // Disable CSRF
                    .csrf().disable()

                    //.anonymous().disable()// Disable Anonymous

                    // Disable SESSION，Will not occur JSESSIONID
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    .and()
                    .authorizeRequests()
                    .antMatchers("/oauth/logout","/login","/logout","/auth/user").permitAll()
                    //.antMatchers("/oauth/token").permitAll()
                    //.anyRequest().authenticated()
                    .anyRequest().fullyAuthenticated()
                    .and()
                    .httpBasic()
                    .and()
                    // Disable caching
                    .headers().cacheControl();
	    }

	    @Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		  resources.resourceId(AUTH_RESOURCE_SERVER).stateless(true);
          super.configure(resources);
		}

}
