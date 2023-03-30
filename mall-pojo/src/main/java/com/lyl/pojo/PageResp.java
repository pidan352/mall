package com.lyl.pojo;

import java.util.List;

/**
 * 功能：分页请求相应类
 * 配合前端vue3，只需传数据和总记录数即可
 * 不需要自定义PageInfo类去计算上一页，下一页之类的数据了
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/1/3
 */

public class PageResp<T> {
	private long total;

	private List<T> list;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "PageResp{" +
				"total=" + total +
				", list=" + list +
				'}';
	}
}
