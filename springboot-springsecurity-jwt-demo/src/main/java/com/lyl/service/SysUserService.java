package com.lyl.service;

import com.lyl.pojo.SysUser;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/5/6 0006
 */

public interface SysUserService {
	SysUser findUserByUsername(String username);
}
