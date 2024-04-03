package com.brotjefors.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brotjefors.shop.model.StoreCategory;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Long>{

}
