package com.lyl.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyl.pojo.RespBean;
import com.lyl.pojo.SysUser;
import com.lyl.service.SysUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.io.PrintWriter;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/28 0028
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource
	private SysUserService userService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}

	//指定加密算法，在springsecurity的加密算法：BCrypt, 在springsecurity中密码必须要加密认证！
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//登录成功的回调
		http.authorizeRequests()
				.anyRequest().authenticated()//所有访问都需认证，认证不通过的跳转到登录页面，springsecurity有自带的默认登录页面
				.and()
				.formLogin()//表单验证登录
				.usernameParameter("username")//默认就是username，下同
				.passwordParameter("password")
				.loginProcessingUrl("/doLogin")//设置默认登录页面登录的地址,不用自己写controller去接请求
				.successHandler((request, response, authentication) -> {//登录成功的回调
					//使用JSON响应给前端
					response.setContentType("application/json;charset=utf-8");
					//获取请求登录用户的信息
					SysUser user = (SysUser) authentication.getPrincipal();
					RespBean respBean = RespBean.ok("登录成功", user);
					String json = new ObjectMapper().writeValueAsString(respBean);
					System.out.println(json);

					PrintWriter out = response.getWriter();
					out.write(json);
					out.flush();
					out.close();
				})
				.failureHandler((request, response, exception) -> {//登录失败的回调
					response.setContentType("application/json;charset=UTF-8");
					PrintWriter out = response.getWriter();

					RespBean respBean = RespBean.fail("登录失败");
					//判断异常类型
					if (exception instanceof LockedException) {
						respBean.setMessage("账号被锁定");
					} else if (exception instanceof CredentialsExpiredException) {
						respBean.setMessage("密码过期");
					} else if (exception instanceof DisabledException) {
						respBean.setMessage("账号被禁用");
					} else if (exception instanceof BadCredentialsException) {
						respBean.setMessage("账号或密码错误");
					}

					out.write(new ObjectMapper().writeValueAsString(respBean));
					out.flush();
					out.close();
				})
				.permitAll()
				.and()
				.logout()
				.logoutSuccessHandler((request, response, authentication) -> {//注销后的回调

				})
				.permitAll()
				.and()
				.csrf().disable();//如果不禁用，post请求的参数会被拦截
	}
}
