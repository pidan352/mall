package com.lyl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/28 0028
 */

@SpringBootApplication
@MapperScan("com.lyl.mapper")
public class JwtApp {
	public static void main(String[] args) {
		SpringApplication.run(JwtApp.class, args);
	}
}
