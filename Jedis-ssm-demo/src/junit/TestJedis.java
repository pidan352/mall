package junit;

import org.hibernate.validator.internal.util.IdentitySet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.Item;
import redis.clients.jedis.Jedis;
import service.ItemService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/11
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml"})
public class TestJedis {

	@Autowired
	private ItemService itemService;

	/**
	 * 将数据库的数据查询出来然后导入到redis中
	 */
	@Test
	public void testWriteInRedis() {
		List<Item> itemList = itemService.findAllItems();

		//创建Jedits的客户端
		Jedis jedis = new Jedis("192.168.247.129", 6379);

		//需要存储什么数据，定义好redis的数据结构
		/**
		 * 例子：这里将所有商品都查询出来
		 *
		 * 存储所有商品的id
		 * 		List：ids=[1,2,3,...]
		 *
		 * 存储商品信息
		 * 		Hash:	key(redis key,使用item_id结构)
		 * 		  例如：item_1
		 * 		  			field   value
		 * 		  			id		1
		 * 		  			name	华为电视
		 * 		  			price  	4000
		 * 		  	    item_2
		 * 		  	    	id		2
		 * 		  	    	name	小米手机
		 * 		  	    	price	1600
		 *
		 */

		//边遍历边存
		for (Item item : itemList) {
			//List
			jedis.lpush("ids", item.getId().toString());

			//Hash
			HashMap<String, String> map = new HashMap<>();
			map.put("id", item.getId().toString());
			map.put("name", item.getName().toLowerCase());
			map.put("price", item.getPrice().toString());
			jedis.hmset("item_" + item.getId(), map);
		}
	}

	//从redis中读取数据
	@Test
	public void testReadFromRedis() {
		Jedis jedis = new Jedis("192.168.247.129", 6379);

		List<String> idsList = jedis.lrange("ids", 0, -1);
		System.out.println(idsList);

		for (String s : idsList) {
			Map<String, String> itemMap = jedis.hgetAll("item_" + s);
			System.out.println(itemMap.toString());
		}
	}
}
