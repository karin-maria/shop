package com.brotjefors.shop.service;

import com.brotjefors.shop.model.Category;
import com.brotjefors.shop.model.Store;
import com.brotjefors.shop.model.StoreCategory;
import com.brotjefors.shop.dto.StoreCategoryDto;
import com.brotjefors.shop.repository.StoreCategoryRepository;
import com.brotjefors.shop.repository.CategoryRepository;
import com.brotjefors.shop.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class StoreCategoryService {

    private StoreCategoryRepository storeCategoryRepository;
    private CategoryRepository categoryRepository;
    private StoreRepository storeRepository;

    public StoreCategoryService(StoreCategoryRepository storeCategoryRepository, CategoryRepository categoryRepository, StoreRepository storeRepository){
        this.storeCategoryRepository = storeCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.storeRepository = storeRepository;
    }

    public List<StoreCategory> findAllStoreCategories() {
        return storeCategoryRepository.findAll();
    }


    public StoreCategory addStoreCategory(StoreCategoryDto storeCategoryDto) {

        StoreCategory storeCategory = new StoreCategory();
        Category category = categoryRepository.findById(storeCategoryDto.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Category not found"));
        Store store = storeRepository.findById(storeCategoryDto.getStoreId())
            .orElseThrow(() -> new RuntimeException("Store not found"));
        storeCategory.setCategory(category);
        storeCategory.setStore(store);
        storeCategory.setCategoryOrder(storeCategoryDto.getOrder());
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
}