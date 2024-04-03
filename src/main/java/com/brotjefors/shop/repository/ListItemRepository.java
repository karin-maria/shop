package com.brotjefors.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brotjefors.shop.model.ListItem;

public interface ListItemRepository extends JpaRepository<ListItem, Long>{

}
