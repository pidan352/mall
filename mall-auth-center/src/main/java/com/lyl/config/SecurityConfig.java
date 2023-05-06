package com.lyl.config;


import com.lyl.component.RestAuthenticationEntryPoin;
import com.lyl.component.RestAuthorizationHandler;
import com.lyl.filter.JwtAuthenticationFilter;
import com.lyl.filter.JwtAuthorizationFilter;
import com.lyl.pojo.TbUser;
import com.lyl.service.SysUserService;
import com.lyl.utils.UserDetailImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

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

	//指定加密算法，在springsecurity的加密算法：BCrypt, 在springsecurity中密码必须要加密认证！
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		//重写UserDetailsService中的验证方法
		return username -> {
			System.out.println("认证账号：" + username);
			TbUser user = userService.findUserByUsername(username);
			if (user != null) {
				Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
				return new UserDetailImpl(user, authorities);
			}
			return null;
		};
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//登录成功的回调
		http.cors()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //无状态认证
				.and()
				.authorizeRequests().antMatchers("/doLogin")
				.permitAll() //doLogin资源不会被拦截
				.anyRequest().authenticated() //除了前面的资源外都要拦截
				.and()
				//添加用户认证过程的过滤器（即登录逻辑的实现）
				.addFilterBefore(new JwtAuthenticationFilter("/doLogin", authenticationManager()),
								 UsernamePasswordAuthenticationFilter.class)
				//访问资源时拦截认证是否具有合法身份
				//.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.csrf().disable()
				//报错处理组件
				.exceptionHandling()
				//资源访问受限
				.accessDeniedHandler(new RestAuthorizationHandler())
				//未登录或token已失效
				.authenticationEntryPoint(new RestAuthenticationEntryPoin());
	}
}
