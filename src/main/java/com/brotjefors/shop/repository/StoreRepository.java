package com.brotjefors.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brotjefors.shop.model.Store;

public interface StoreRepository extends JpaRepository<Store, Long>{

}
