package com.lyl.config;


import com.lyl.component.RestAuthenticationEntryPoin;
import com.lyl.component.RestAuthorizationHandler;
import com.lyl.filter.JwtAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/28 0028
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
				//.addFilterBefore(new JwtAuthenticationFilter("/doLogin", authenticationManager()),
				//				 UsernamePasswordAuthenticationFilter.class)
				//访问资源时拦截认证是否具有合法身份
				.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.csrf().disable()
				//报错处理组件
				.exceptionHandling()
				//资源访问受限
				.accessDeniedHandler(new RestAuthorizationHandler())
				//未登录或token已失效
				.authenticationEntryPoint(new RestAuthenticationEntryPoin());
	}
}
