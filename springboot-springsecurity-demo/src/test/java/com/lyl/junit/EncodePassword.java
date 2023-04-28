package com.lyl.junit;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 功能：使用BCrypt加密密码
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/28 0028
 */

@SpringBootTest
public class EncodePassword {

	@Test
	public void test() {
		String password = "123456";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encode = encoder.encode(password);
		System.out.println(encode);
	}
}
