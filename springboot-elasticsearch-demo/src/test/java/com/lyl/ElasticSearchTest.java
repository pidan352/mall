package com.lyl;

import com.lyl.document.EsItem;
import com.lyl.mapper.TbItemMapper;
import com.lyl.pojo.TbItem;
import com.lyl.repository.EsItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2023/4/27 0027
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticSearchTest {

	@Resource
	private TbItemMapper itemMapper;

	@Resource
	private EsItemRepository esItemRepository;

	@Test
	public void testAdd() {
		List<TbItem> tbItems = itemMapper.selectByExample(null);
		for (TbItem tbItem : tbItems) {
			EsItem esItem = new EsItem();
			esItem.setId(tbItem.getId());
			esItem.setBrand(tbItem.getBrand());
			esItem.setCategory(tbItem.getCategory());
			esItem.setGoodsId(tbItem.getGoodsId());
			esItem.setImage(tbItem.getImage());
			esItem.setPrice(tbItem.getPrice());
			esItem.setSeller(tbItem.getSeller());
			esItem.setTitle(tbItem.getTitle());
			esItem.setSpec(tbItem.getSpec());
			esItem.setSellPoint(tbItem.getSellPoint());

			esItemRepository.save(esItem);
		}
	}

	@Test
	public void testQueryAll() {
		Iterator<EsItem> iterator = esItemRepository.findAll().iterator();

		while (iterator.hasNext()) {
			EsItem esItem = iterator.next();
			System.out.println(esItem);
		}
	}

	@Test
	public void testQueryById() {
		EsItem esItem = esItemRepository.findById(1356541L).get();
		System.out.println(esItem);
	}

	@Test
	public void testQueryTitleAndBrand() {
		Page<EsItem> itemPage = esItemRepository.findByTitleAndBrand("荣耀", "华为", null);
		for (EsItem esItem : itemPage.getContent()) {
			System.out.println(esItem);
		}
	}

	@Test
	public void testQueryKeyword() {
		Page<EsItem> page = esItemRepository.findByKeyword("小米", null);
		for (EsItem esItem : page.getContent()) {
			System.out.println(esItem);
		}
	}

	@Test
	public void testQueryPrefix() {
		Page<EsItem> page = esItemRepository.findEsItemByTitlePrefix("荣耀", null);
		for (EsItem esItem : page.getContent()) {
			System.out.println(esItem);
		}
	}
}
