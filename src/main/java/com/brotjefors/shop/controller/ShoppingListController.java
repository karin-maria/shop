package com.brotjefors.shop.controller;

import com.brotjefors.shop.dto.ListItemDto;
import com.brotjefors.shop.dto.ShoppingListDto;
import com.brotjefors.shop.model.ListItem;
import com.brotjefors.shop.model.ShoppingList;
import com.brotjefors.shop.service.ShoppingListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/shoppingLists")
public class ShoppingListController {

    private ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @PostMapping("/add")
    public ShoppingList addShoppingList(@RequestBody ShoppingListDto shoppingListDto) {
        return shoppingListService.addShoppingList(shoppingListDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShoppingList>> getAllShoppingLists() {
        List<ShoppingList> lists = shoppingListService.findAllLists();
        return ResponseEntity.ok(lists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingList> getShoppingListById(@PathVariable Long id) {
        ShoppingList shoppingList = shoppingListService.findShoppingListById(id);
        if(shoppingList != null) {
            return ResponseEntity.ok(shoppingList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingList> updateShoppingList(
            @PathVariable Long id,
            @RequestBody ShoppingList shoppingListDetails) {

        ShoppingList updatedShoppingList = shoppingListService.updateShoppingList(id, shoppingListDetails);
        if(updatedShoppingList != null) {
            return ResponseEntity.ok(updatedShoppingList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingList(@PathVariable Long id) {
        if(shoppingListService.deleteShoppingList(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/addItem")
    public ShoppingList addItemToShoppingList(@RequestBody ListItemDto listItemDto) {
            return shoppingListService.addItemToShoppingList(listItemDto);
    }

    @GetMapping("/{id}/sort")
    public ResponseEntity<List<ListItem>> getSortedShoppingListItems(
            @PathVariable Long id,
            @RequestParam("storeId") Long storeId) {

        try {
            List<ListItem> sortedItems = shoppingListService.sortShoppingList(id, storeId);
            return ResponseEntity.ok(sortedItems);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}