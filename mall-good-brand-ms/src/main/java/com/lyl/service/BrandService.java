package com.lyl.service;


import com.lyl.pojo.TbBrand;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/3/30
 */

public interface BrandService {
	/**
	 * 查询所有数据，查询分页数据使用分页拦截后查询
	 *
	 * @return
	 */
	List<TbBrand> queryBrand();

	/**
	 * 根据id批量删除
	 *
	 * @param idList
	 */
	void deleteByIdList(ArrayList<Long> idList);

	/**
	 * 添加品牌
	 *
	 * @param brand
	 */
	void addBrand(TbBrand brand);

	/**
	 * 保存修改的品牌
	 *
	 * @param brand
	 */
	void updateBrand(TbBrand brand);
}
