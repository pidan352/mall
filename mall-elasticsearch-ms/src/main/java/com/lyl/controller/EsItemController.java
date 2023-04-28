package com.lyl.controller;

import com.lyl.document.EsItem;
import com.lyl.mapper.TbItemMapper;
import com.lyl.pojo.RespBean;
import com.lyl.pojo.TbItem;
import com.lyl.repository.EsItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/28 0028
 */

@RestController
public class EsItemController {

	@Resource
	private EsItemRepository itemRepository;

	@Resource
	private TbItemMapper itemMapper;

	@GetMapping("/queryEsItemByKeyWord")
	public RespBean queryEsItemByKeyWord(String keyWord) {
		try {
			Page<EsItem> page = itemRepository.findByKeyword(keyWord, null);
			List<EsItem> itemList = page.getContent();
			return RespBean.ok("SUCCESS", itemList);
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.fail("FAIL");
		}
	}

	@GetMapping("/itemDetail")
	public RespBean itemDetail(Long goods_id) {
		try {
			TbItem tbItem = itemMapper.selectByPrimaryKey(goods_id);
			return RespBean.ok("SUCCESS", tbItem);
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.fail("FAIL");
		}
	}
}
