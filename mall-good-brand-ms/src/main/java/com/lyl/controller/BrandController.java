package com.lyl.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyl.pojo.PageReq;
import com.lyl.pojo.PageResp;
import com.lyl.pojo.RespBean;
import com.lyl.pojo.TbBrand;
import com.lyl.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
	public RespBean queryBrandByPage(PageReq pageReq) {
		try {
			if (pageReq.getPage() == 0 || pageReq.getSize() == 0) {
				pageReq.setPage(1);
				pageReq.setSize(5);
			}
			PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
			//分页拦截后返回的是Page
			Page<TbBrand> page = (Page<TbBrand>) brandService.queryBrand();

			PageResp<TbBrand> pageResp = new PageResp<>();
			pageResp.setTotal(page.getTotal());
			pageResp.setList(page.getResult());

			return RespBean.ok("查询成功", pageResp);
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.fail("数据查询出错");
		}
	}

	@PostMapping("/deleteBrand")
	public RespBean deleteBrand(@RequestBody ArrayList<Long> idList) {
		try {
			brandService.deleteByIdList(idList);
			return RespBean.ok("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.fail("删除失败");
		}
	}

	@PostMapping("/addBrand")
	public RespBean addBrand(@RequestBody TbBrand brand) {
		try {
			brandService.addBrand(brand);
			return RespBean.ok("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.fail("添加失败");
		}
	}

	@PostMapping("/editBrand")
	public RespBean editBrand(@RequestBody TbBrand brand) {
		try {
			brandService.updateBrand(brand);
			return RespBean.ok("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.fail("修改失败");
		}
	}
}
