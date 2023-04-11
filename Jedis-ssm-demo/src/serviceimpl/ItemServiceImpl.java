package serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.ItemMapper;
import pojo.Item;
import pojo.ItemExample;
import service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper im;

	@Override
	public List<Item> findAllItems() {
		return im.selectByExample(null);
	}

	@Override
	public List<Item> queryItems(Item item) {
		ItemExample iExample = new ItemExample();
		ItemExample.Criteria criteria = iExample.createCriteria();
		criteria.andNameLike("%" + item.getName() + "%");
		return im.selectByExample(iExample);
	}

	@Override
	public void deleteItems(int[] ids) {
		for (int i = 0; i < ids.length; i++) {
			im.deleteByPrimaryKey(ids[i]);
		}
	}

	@Override
	public void addItem(Item item) {
		im.insert(item);
	}

	@Override
	public Item findItemById(Integer id) {
		return im.selectByPrimaryKey(id);
	}

	@Override
	public void updateItem(Item item) {
		im.updateByPrimaryKey(item);
	}

}
