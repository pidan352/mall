package com.lyl.service;

import com.lyl.pojo.TbContent;

import java.util.List;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/11
 */

public interface ContentServie {

	/**
	 * 查询
	 *
	 * @return
	 */
	List<TbContent> queryContent();
}
