package com.lyl.service.impl;

import com.lyl.mapper.TbContentMapper;
import com.lyl.pojo.TbContent;
import com.lyl.service.ContentServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/11
 */

@Service
public class ContentServiceImpl implements ContentServie {

	@Resource
	private TbContentMapper contentMapper;

	/**
	 * 查询
	 *
	 * @return
	 */
	@Override
	public List<TbContent> queryContent() {
		return contentMapper.selectByExample(null);
	}
}
