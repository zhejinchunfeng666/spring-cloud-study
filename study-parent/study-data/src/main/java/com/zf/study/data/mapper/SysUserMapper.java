package com.zf.study.data.mapper;


import com.zf.study.core.entity.SysUser;

public interface SysUserMapper {

	SysUser selectUserInfoByUserName(String username);

	void insertUserInfo(SysUser user);

}
