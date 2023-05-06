package com.lyl.filter;

import com.lyl.utils.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 功能：访问资源时拦截认证是否具有合法身份
 * 验证jwt，判断其有效性
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/5/6 0006
 */

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {
		//获取请求头中的token，名称（固定）：“authorization”
		String tokenHeather = request.getHeader("authorization");
		System.out.println("拦截的token" + tokenHeather);

		//判断token的有效性
		if (tokenHeather != null && tokenHeather.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
			UsernamePasswordAuthenticationToken token = getAuthentication(tokenHeather);
			SecurityContextHolder.getContext().setAuthentication(token);
		}
		//放行
		filterChain.doFilter(request, response);
	}

	/**
	 * 判断token的有效性
	 * 若合法则需要将token重新登记回springsecurity的上下文
	 *
	 * @param tokenHeather
	 * @return
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeather) {
		//去掉token前缀
		String token = tokenHeather.replace(JwtTokenUtil.TOKEN_PREFIX, "");
		//判断是否符合有效性的规则（自定义）
		//示例：获取账号
		String username = JwtTokenUtil.getProperties(token);
		if (username != null) {
			return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
		}
		return null;
	}

}
