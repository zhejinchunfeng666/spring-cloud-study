package com.zf.study.mem.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class TokenUserInfo implements Serializable, UserDetails{

     /**
	 * 
	 */
	private static final long serialVersionUID = 5312968429738577456L;
	private String username;
     private String password;
     Collection<GrantedAuthority> authorities = new ArrayList<>();
     private boolean accountNonExpired;
     private boolean accountNonLocked;
     private boolean credentialsNonExpired;
     private boolean enabled;
     public TokenUserInfo(String username, String password,  Collection<GrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked,
                 boolean credentialsNonExpired, boolean enabled) {
           this.username = username;
           this.password = password;
           this.authorities = authorities;
           this.accountNonExpired = accountNonExpired;
           this.accountNonLocked = accountNonLocked;
           this.credentialsNonExpired = credentialsNonExpired;
           this.enabled = enabled;
     }

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

}
