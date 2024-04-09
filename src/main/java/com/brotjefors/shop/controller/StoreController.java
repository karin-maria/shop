package com.brotjefors.shop.controller;

import com.brotjefors.shop.model.Store;
import com.brotjefors.shop.model.StoreCategory;
import com.brotjefors.shop.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping("/add")
    public Store addStore(@RequestBody Store store) {
        return storeService.addStore(store);
    }

    @GetMapping("/all")
    public List<Store> getAllStores() {
        return storeService.findAllStores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        return storeService.findStoreById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/categories")
    public List<StoreCategory> getStoreCategories(@PathVariable("id") Long storeId) {
        return storeService.getStoreCategories(storeId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable Long id, @RequestBody Store storeDetails) {
        Store updatedStore = storeService.updateStore(id, storeDetails);
        if (updatedStore != null) {
            return ResponseEntity.ok(updatedStore);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        try {
            storeService.deleteStore(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}