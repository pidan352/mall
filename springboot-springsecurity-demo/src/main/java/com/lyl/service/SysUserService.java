package com.lyl.service;

import com.lyl.mapper.SysUserMapper;
import com.lyl.pojo.SysUser;
import com.lyl.pojo.SysUserExample;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/28 0028
 */

@Service
public class SysUserService implements UserDetailsService {

	@Resource
	private SysUserMapper userMapper;

	/**
	 * 该方法根据用户名查询唯一用户对象，  前提：username必须是唯一约束
	 *
	 * @param
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("用户名：" + username);

		SysUserExample userExample = new SysUserExample();
		SysUserExample.Criteria criteria = userExample.createCriteria();
		criteria.andUsercodeEqualTo(username);

		List<SysUser> userList = userMapper.selectByExample(userExample);
		if (userList.size() == 0) {
			throw new UsernameNotFoundException("账号不存在");
		}
		return userList.get(0);
	}
}
