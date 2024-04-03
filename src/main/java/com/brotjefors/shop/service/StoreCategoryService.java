package com.brotjefors.shop.service;

import com.brotjefors.shop.model.StoreCategory;
import com.brotjefors.shop.repository.StoreCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoreCategoryService {

    @Autowired
    private StoreCategoryRepository storeCategoryRepository;

    public StoreCategory saveStoreCategory(StoreCategory storeCategory) {
        return storeCategoryRepository.save(storeCategory);
    }

    public StoreCategory findByStoreId(Long storeId) {
        return storeCategoryRepository.findById(storeId).orElse(null);
    }

    public Optional<StoreCategory> findById(Long id) {
        return storeCategoryRepository.findById(id);
    }

    public void deleteStoreCategory(Long id) {
        storeCategoryRepository.deleteById(id);
    }

    // Additional methods as needed
}