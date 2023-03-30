package com.lyl.pojo;

/**
 * 功能：分页请求类
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/1/3
 */

public class PageReq {
	private int page;
	private int size;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "PageReq{" +
				"page=" + page +
				", size=" + size +
				'}';
	}
}
