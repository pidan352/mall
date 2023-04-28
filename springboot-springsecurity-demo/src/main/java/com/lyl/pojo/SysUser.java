package com.lyl.pojo;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class SysUser implements UserDetails {
	private String id;

	private String usercode;

	private String username;

	private String password;

	private String salt;

	private String locked;

	//判断账号是否过期
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//判断账号是否锁定
	@Override
	public boolean isAccountNonLocked() {
		return this.locked.equals("0");
	}

	//判断密码是否过期
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//是否可用
	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	//获取权限列表
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
}