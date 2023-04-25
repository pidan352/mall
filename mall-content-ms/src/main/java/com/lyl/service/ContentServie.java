package com.lyl.service;

import com.lyl.pojo.TbContent;

import java.util.ArrayList;
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

	/**
	 * 小程序查找广告
	 *
	 * @param id
	 * @return
	 */
	List<TbContent> findByCategoryId(Integer id);

	//增删改的方法需要更新redis中的数据

	/**
	 * 删除
	 *
	 * @param idList
	 */
	void deleteByIdList(ArrayList<Long> idList);

	/**
	 * 添加
	 *
	 * @param content
	 */
	void insert(TbContent content);
}
