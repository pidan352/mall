package com.lyl.service;


import com.lyl.pojo.TbBrand;

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
}
