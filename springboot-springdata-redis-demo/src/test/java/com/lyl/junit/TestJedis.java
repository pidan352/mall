package com.lyl.junit;

import com.lyl.App;
import com.lyl.pojo.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/11
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJedis {
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 字符串操作
	 */
	@Test
	public void testSetString() {
		stringRedisTemplate.opsForValue().set("msg1", "这是第一个springboot redis应用");
	}

	@Test
	public void testGetString() {
		String msg1 = stringRedisTemplate.opsForValue().get("msg1");
		System.out.println(msg1);
	}

	/**
	 * 对象序列化 对象要有无参构造
	 */
	@Test
	public void testSetObject() {
		//设置序列化器
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

		Person person = new Person();
		person.setName("张三");
		person.setAge(18);

		redisTemplate.opsForValue().set("person1", person);
	}

	@Test
	public void testGetObject() {
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		Person person = (Person) redisTemplate.opsForValue().get("person1");
		System.out.println(person.toString());
	}

	/**
	 * hash
	 */
	@Test
	public void testSetHash() {
		ArrayList<String> userList = new ArrayList<>();
		userList.add("zhansan");
		userList.add("lisi");

		ArrayList<String> fruitList = new ArrayList<>();
		fruitList.add("apple");
		fruitList.add("banana");

		redisTemplate.opsForHash().put("list", "userList", userList);
		redisTemplate.opsForHash().put("list", "fruitList", fruitList);

	}

	@Test
	public void testGetHash() {
		List<String> userList = (List<String>) redisTemplate.opsForHash().get("list", "fruitList");
		userList.forEach(System.out::println);
	}


}
