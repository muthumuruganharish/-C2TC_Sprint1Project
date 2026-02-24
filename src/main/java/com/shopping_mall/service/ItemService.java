package com.shopping_mall.service;

import java.util.List;

import com.shopping_mall.entity.Item;

public interface ItemService {

    Item createItem(Item item);

    Item updateItem(Long id, Item item);

    Item getItemById(Long id);

    List<Item> getAllItems();

    void deleteItem(Long id);
}
