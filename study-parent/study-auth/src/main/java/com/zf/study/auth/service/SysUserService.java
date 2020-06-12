package com.zf.study.auth.service;

import com.zf.study.core.entity.SysUser;
import com.zf.study.data.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SysUserService {
	
	@Autowired
	private SysUserMapper sysUserMapper;

	public SysUser selectUserInfoByUserName(String username) {
		// TODO Auto-generated method stub
		return sysUserMapper.selectUserInfoByUserName(username);
	}

	public void insertUserInfo(String username, String password) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		SysUser user = new SysUser();
		user.setUserName(username);
		user.setPassWord(encoder.encode(password));
		user.setStatus(1);
		sysUserMapper.insertUserInfo(user);
	}

	public SysUser getUserByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
