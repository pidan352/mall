package com.lyl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyl.mapper.TbBrandMapper;
import com.lyl.pojo.PageReq;
import com.lyl.pojo.PageResp;
import com.lyl.pojo.TbBrand;
import com.lyl.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/3/30
 */

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private TbBrandMapper brandMapper;

	/**
	 * 查询所有数据，查询分页数据使用分页拦截后查询
	 *
	 * @return
	 */
	@Override
	public List<TbBrand> queryBrand() {
		return brandMapper.selectByExample(null);
	}
}
