package com.lyl.service;


import com.lyl.pojo.TbUser;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/5/6 0006
 */

public interface SysUserService {
	TbUser findUserByUsername(String username);
}
