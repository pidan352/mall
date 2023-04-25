package com.lyl.service.impl;

import com.lyl.mapper.TbContentCategoryMapper;
import com.lyl.pojo.TbContentCategory;
import com.lyl.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/24 0024
 */

@Service
public class CategoryServiceImpl implements CategoryService {

	@Resource
	private TbContentCategoryMapper categoryMapper;

	@Override
	public List<TbContentCategory> queryCategory() {
		return categoryMapper.selectByExample(null);
	}
}
