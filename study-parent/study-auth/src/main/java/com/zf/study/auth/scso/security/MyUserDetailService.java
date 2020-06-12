package com.zf.study.auth.scso.security;

import java.util.ArrayList;
import java.util.Collection;

import com.zf.study.auth.service.SysUserService;
import com.zf.study.core.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("myUserDetailService")
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private SysUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		// TODO Auto-generated method stub
		Boolean notLocck = true;
		// 从数据库中取出信息
		SysUser sysUser = userService.selectUserInfoByUserName(username);
		// 判断用户是否存在
		if (sysUser == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		// 添加权限
//		List<SysUserRole> userRoleList = userRoleService.selectRoleListByUserId(sysUser.getId());
//		if (null == userRoleList) {
//			authorities.add(new SimpleGrantedAuthority("admin"));
//		} else {
//			for (SysUserRole sysUserRole : userRoleList) {
//				SysRole sysRole = roleService.selectRoleByRoleId(sysUserRole.getRoleId());
//				authorities.add(new SimpleGrantedAuthority(sysRole.getName()));
//			}
//		}
		authorities.add(new SimpleGrantedAuthority("ROLE_SOA"));
		Integer status = sysUser.getStatus();
		if (null == status) {
			notLocck = true;
		} else {
			notLocck = (status == 1);
		}
		return new TokenUserInfo(sysUser.getUserName(), sysUser.getPassWord(), authorities, true, notLocck, true, true);
	}

}
