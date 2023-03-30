package com.lyl.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyl.pojo.PageReq;
import com.lyl.pojo.PageResp;
import com.lyl.pojo.TbBrand;
import com.lyl.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能：品牌管理
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/3/30
 */

@RestController
public class BrandController {

	@Autowired
	private BrandService brandService;

	@GetMapping("/queryBrandByPage")
	public PageResp<TbBrand> queryBrandByPage(PageReq pageReq) {
		PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
		//分页拦截后返回的是Page
		Page<TbBrand> page = (Page<TbBrand>) brandService.queryBrand();

		PageResp<TbBrand> pageResp = new PageResp<>();
		pageResp.setTotal(page.getTotal());
		pageResp.setList(page.getResult());

		return pageResp;
	}
}
