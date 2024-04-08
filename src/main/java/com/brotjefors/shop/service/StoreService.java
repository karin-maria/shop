package com.brotjefors.shop.service;

import com.brotjefors.shop.model.Store;
import com.brotjefors.shop.model.StoreCategory;
import com.brotjefors.shop.repository.StoreCategoryRepository;
import com.brotjefors.shop.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    private StoreRepository storeRepository;
    private final StoreCategoryRepository storeCategoryRepository;

    public StoreService(StoreRepository storeRepository, StoreCategoryRepository storeCategoryRepository) {
        this.storeRepository = storeRepository;
        this.storeCategoryRepository = storeCategoryRepository;
    }

    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }

    public List<Store> findAllStores() {
        return storeRepository.findAll();
    }

    public Optional<Store> findStoreById(Long id) {
        return storeRepository.findById(id);
    }

    public List<StoreCategory> getStoreCategories(Long storeId) {
        return storeCategoryRepository.findByStoreId(storeId);
    }

    public Store updateStore(Long id, Store storeDetails) {
        return storeRepository.findById(id)
                .map(store -> {
                    store.setName(storeDetails.getName());
                    return storeRepository.save(store);
                }).orElse(null);
    }

    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }
}