package com.lyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/3/30
 */

@SpringBootApplication
@EnableEurekaServer
public class EurekaApp {
	public static void main(String[] args) {
		SpringApplication.run(EurekaApp.class, args);
	}
}
