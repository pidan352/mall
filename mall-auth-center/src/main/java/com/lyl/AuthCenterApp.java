package com.lyl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/5/6 0006
 */

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.lyl.mapper")
public class AuthCenterApp {
	public static void main(String[] args) {
		SpringApplication.run(AuthCenterApp.class, args);
	}
}
