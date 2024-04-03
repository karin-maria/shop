package com.brotjefors.shop.controller;

import com.brotjefors.shop.dto.ItemDto;
import com.brotjefors.shop.model.Item;
import com.brotjefors.shop.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/add")
    public Item createItem(@RequestBody ItemDto itemDto) {
        return itemService.saveItem(itemDto);
    }

    @GetMapping("/all")
    public Iterable<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Item findItemById(@PathVariable Long id) {
        return itemService.findItemById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody ItemDto itemDetailsDto) {
    Item updatedItem = itemService.updateItem(id, itemDetailsDto);
    if(updatedItem != null) {
        return ResponseEntity.ok(updatedItem);
    } else {
        return ResponseEntity.notFound().build();
    }
}

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}