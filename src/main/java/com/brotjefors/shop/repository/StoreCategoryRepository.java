package com.brotjefors.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.brotjefors.shop.model.StoreCategory;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Long>{

    List<StoreCategory> findByStoreId(Long storeId);
}
