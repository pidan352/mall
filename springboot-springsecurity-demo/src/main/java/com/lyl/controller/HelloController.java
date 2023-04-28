package com.lyl.controller;

import com.lyl.pojo.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/28 0028
 */

@RestController
public class HelloController {

	@GetMapping("/hello")
	public RespBean hello() {
		return RespBean.ok("hello");
	}
}
