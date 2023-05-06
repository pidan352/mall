package com.lyl.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyl.pojo.TbUser;
import com.lyl.utils.CommonResult;
import com.lyl.utils.JwtTokenUtil;
import com.lyl.utils.UserDetailImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * 功能：添加用户认证过程的过滤器（即登录逻辑的实现）
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/5/6 0006
 */

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;


	public JwtAuthenticationFilter(String url, AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setFilterProcessesUrl(url);
	}

	//用户认证
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response) throws AuthenticationException {
		//获取前端json数据中的账号密码
		try {
			TbUser user = new ObjectMapper().readValue(request.getInputStream(), TbUser.class);
			//执行认证
			//UsernamePasswordAuthenticationToken参数：账号、密码、权限列表
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
															new ArrayList<>()));
		} catch (IOException e) {
			e.printStackTrace();
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(null, null,
															new ArrayList<>()));
		}
	}

	//认证成功后的回调方法，需要生成jwt返回给客户端
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
											FilterChain chain, Authentication authResult) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		//获取认证的主体信息
		UserDetailImpl userDetails = (UserDetailImpl) authResult.getPrincipal();
		//生成jwt的token
		String token = JwtTokenUtil.createToken("lyl", userDetails.getUsername(), 1800L);
		System.out.println("生成的jwt" + token);
		out.write(new ObjectMapper().writeValueAsString(CommonResult.success(JwtTokenUtil.TOKEN_PREFIX + token)));
		out.flush();
		out.close();
	}

	//认证失败后的回调方法
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
											  AuthenticationException failed) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(new ObjectMapper().writeValueAsString(CommonResult.failed("登录失败")));
		out.flush();
		out.close();
	}
}
