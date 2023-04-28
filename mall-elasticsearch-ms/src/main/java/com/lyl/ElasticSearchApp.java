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
 * @date 2023/4/27 0027
 */

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.lyl.mapper")
public class ElasticSearchApp {
	public static void main(String[] args) {
		SpringApplication.run(ElasticSearchApp.class, args);
	}
}
