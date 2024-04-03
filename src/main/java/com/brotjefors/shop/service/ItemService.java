package com.brotjefors.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.brotjefors.shop.dto.ItemDto;
import com.brotjefors.shop.model.Category;
import com.brotjefors.shop.model.Item;
import com.brotjefors.shop.repository.CategoryRepository;
import com.brotjefors.shop.repository.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    public Iterable<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item findItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item saveItem(ItemDto itemDto) {
        Category category = categoryRepository.findById(itemDto.getCategoryId())
                          .orElseThrow(() -> new RuntimeException("Category not found"));
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setCategory(category);
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, ItemDto itemDetailsDto) {
        return itemRepository.findById(id)
            .map(item -> {
                item.setName(itemDetailsDto.getName());
                Category category = categoryRepository.findById(itemDetailsDto.getCategoryId())
                                      .orElseThrow(() -> new RuntimeException("Category not found"));
                item.setCategory(category);
                return itemRepository.save(item);
            }).orElse(null);
    }

    public void deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
    }
}
