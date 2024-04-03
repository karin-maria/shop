package com.brotjefors.shop.controller;

import com.brotjefors.shop.model.ListItem;
import com.brotjefors.shop.service.ListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/listItems")
public class ListItemController {

    @Autowired
    private ListItemService listItemService;

    @PostMapping("/add")
    public ListItem addListItem(@RequestBody ListItem listItem) {
        return listItemService.saveListItem(listItem);
    }

    @GetMapping("/get/{listId}")
    public ListItem getListItemsByListId(@PathVariable Long listId) {
        return listItemService.findListItemById(listId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteListItem(@PathVariable Long id) {
        listItemService.deleteListItem(id);
        return ResponseEntity.ok().build();
    }
}