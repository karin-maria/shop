package com.brotjefors.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brotjefors.shop.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{

}
