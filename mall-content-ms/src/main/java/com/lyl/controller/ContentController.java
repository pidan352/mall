package com.lyl.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyl.pojo.*;
import com.lyl.service.ContentServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/11
 */

@Controller
public class ContentController {

	@Autowired
	private ContentServie contentServie;

	@GetMapping("/queryContentByPage")
	public RespBean queryBrandByPage(PageReq pageReq) {
		try {
			if (pageReq.getPage() == 0 || pageReq.getSize() == 0) {
				pageReq.setPage(1);
				pageReq.setSize(5);
			}
			PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
			//分页拦截后返回的是Page
			Page<TbContent> page = (Page<TbContent>) contentServie.queryContent();

			PageResp<TbContent> pageResp = new PageResp<>();
			pageResp.setTotal(page.getTotal());
			pageResp.setList(page.getResult());

			return RespBean.ok("查询成功", pageResp);
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.fail("数据查询出错");
		}
	}
}
