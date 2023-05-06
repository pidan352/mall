package com.lyl.service.impl;



import com.lyl.mapper.TbUserMapper;
import com.lyl.pojo.TbUser;
import com.lyl.pojo.TbUserExample;
import com.lyl.service.SysUserService;
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
public class SysUserServiceimpl implements SysUserService {

	@Resource
	private TbUserMapper userMapper;

	/**
	 * 该方法根据用户名查询唯一用户对象，  前提：username必须是唯一约束
	 *
	 * @param
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public TbUser findUserByUsername(String username) throws UsernameNotFoundException {
		TbUserExample userExample = new TbUserExample();
		TbUserExample.Criteria criteria = userExample.createCriteria();
		criteria.andUsernameEqualTo(username);

		List<TbUser> userList = userMapper.selectByExample(userExample);
		if (userList.size() == 0) {
			throw new UsernameNotFoundException("账号不存在");
		}
		return userList.get(0);
	}
}
