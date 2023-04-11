package service;

import java.util.List;

import pojo.Item;

public interface ItemService {

	List<Item> queryItems(Item item);

	void deleteItems(int[] ids);

	void addItem(Item item);

	Item findItemById(Integer id);

	void updateItem(Item item);

	List<Item> findAllItems();
}
