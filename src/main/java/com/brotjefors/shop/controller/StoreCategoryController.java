package com.brotjefors.shop.controller;

import com.brotjefors.shop.model.StoreCategory;
import com.brotjefors.shop.dto.StoreCategoryDto;
import com.brotjefors.shop.service.StoreCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/storeCategories")
public class StoreCategoryController {

    @Autowired
    private StoreCategoryService storeCategoryService;

    @PostMapping("/add")
    public StoreCategory addStoreCategory(@RequestBody StoreCategoryDto storeCategoryDto) {
        return storeCategoryService.saveStoreCategory(storeCategoryDto);
    }

    @GetMapping("/store/{storeId}")
    public StoreCategory getStoreCategoriesByStoreId(@PathVariable Long storeId) {
        return storeCategoryService.findByStoreId(storeId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStoreCategory(@PathVariable Long id) {
        storeCategoryService.deleteStoreCategory(id);
        return ResponseEntity.ok().build();
    }
}