package com.shopping_mall.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping_mall.entity.Item;
import com.shopping_mall.repo.ItemRepository;
import com.shopping_mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    // ✅ CREATE ITEM
    @Override
    public Item createItem(Item item) {

        // Validate expiry date
        if (item.getExpiryDate().isBefore(item.getManufactureDate())) {
            throw new RuntimeException("Expiry date must be after Manufacture date");
        }

        return itemRepository.save(item);
    }

    // ✅ UPDATE ITEM
    @Override
    public Item updateItem(Long id, Item item) {

        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        existingItem.setName(item.getName());
        existingItem.setPrice(item.getPrice());
        existingItem.setCategory(item.getCategory());
        existingItem.setManufactureDate(item.getManufactureDate());
        existingItem.setExpiryDate(item.getExpiryDate());

        return itemRepository.save(existingItem);
    }

    // ✅ GET ITEM BY ID
    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    // ✅ GET ALL ITEMS
    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // ✅ DELETE ITEM
    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
