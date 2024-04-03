package com.brotjefors.shop.service;

import com.brotjefors.shop.model.Store;
import com.brotjefors.shop.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }

    public List<Store> findAllStores() {
        return storeRepository.findAll();
    }

    public Optional<Store> findStoreById(Long id) {
        return storeRepository.findById(id);
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