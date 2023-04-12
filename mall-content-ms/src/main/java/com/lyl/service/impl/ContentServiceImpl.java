package com.lyl.service.impl;

import com.lyl.mapper.TbContentMapper;
import com.lyl.pojo.TbContent;
import com.lyl.pojo.TbContentExample;
import com.lyl.service.ContentServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

	@Resource
	private RedisTemplate redisTemplate;

	/**
	 * 小程序查询广告内容
	 * 首次访问时，如果redis没有数据，就访问数据库，把数据存到redis
	 * 后续访问时，直接查询redis
	 * <p>
	 * 哈希结构
	 * redis Key  content
	 * field                   		value
	 * categoryid 1 (轮播广告)           List<TbContent>
	 * 2 （今日关注 ）         List<TbContent>
	 */
	@Override
	public List<TbContent> findByCategoryId(Integer id) {

		//尝试从redis缓存中取出数据
		List<TbContent> list = (List<TbContent>) redisTemplate.opsForHash().get("content", id);

		/**
		 * 数据为空说明为首次加载，需要查询数据并存到redis
		 */
		if (list == null) {
			TbContentExample contentExample = new TbContentExample();
			TbContentExample.Criteria criteria = contentExample.createCriteria();

			criteria.andCategoryIdEqualTo(id.longValue()).andStatusEqualTo("1");
			contentExample.setOrderByClause("sort_order");

			list = contentMapper.selectByExample(contentExample);

			redisTemplate.opsForHash().put("content", id, list);
		}
		return list;
	}

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
