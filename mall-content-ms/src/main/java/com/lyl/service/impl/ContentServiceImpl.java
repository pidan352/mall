package com.lyl.service.impl;

import com.lyl.mapper.TbContentMapper;
import com.lyl.pojo.TbContent;
import com.lyl.pojo.TbContentExample;
import com.lyl.service.ContentServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

	@Autowired
	private RedisTemplate redisTemplate;

	//增删改的方法需要更新redis中的数据

	@Override
	public void insert(TbContent content) {
		contentMapper.insert(content);
		redisTemplate.opsForHash().delete("content", content.getCategoryId().intValue());
	}

	@Override
	public void updateById(TbContent content) {
		contentMapper.updateByPrimaryKey(content);
		redisTemplate.opsForHash().delete("content", content.getCategoryId().intValue());
	}

	/**
	 * 根据idList删除广告数据
	 *
	 * @param idList
	 */
	@Override
	public void deleteByIdList(ArrayList<Long> idList) {
		idList.forEach(id -> {
			TbContent tbContent = contentMapper.selectByPrimaryKey(id);
			redisTemplate.opsForHash().delete("content", tbContent.getCategoryId().intValue());
			contentMapper.deleteByPrimaryKey(id);
		});

	}

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
		ArrayList<TbContent> list = (ArrayList<TbContent>) redisTemplate.boundHashOps("content").get(id);

		/**
		 * 数据为空说明为首次加载，需要查询数据并存到redis
		 */
		if (list == null) {
			TbContentExample contentExample = new TbContentExample();
			TbContentExample.Criteria criteria = contentExample.createCriteria();

			criteria.andCategoryIdEqualTo(id.longValue()).andStatusEqualTo("1");
			contentExample.setOrderByClause("sort_order");

			list = (ArrayList<TbContent>) contentMapper.selectByExample(contentExample);
			System.out.println("从数据库中查询");

			redisTemplate.opsForHash().put("content", id, list);
		} else {
			System.out.println("从redis中取数据");
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
