package com.lyl.pojo;

/**
 * 功能：消息响应类
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2022/12/1
 */

public class RespBean {
	private int code;
	private String message;
	private Object data;

	private RespBean(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	private RespBean(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public static RespBean ok(String message) {
		return new RespBean(200, message);
	}

	public static RespBean ok(String message, Object data) {
		return new RespBean(200, message, data);
	}

	public static RespBean fail(String message, Object data) {
		return new RespBean(500, message, data);
	}

	public static RespBean fail(String message) {
		return new RespBean(500, message);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
