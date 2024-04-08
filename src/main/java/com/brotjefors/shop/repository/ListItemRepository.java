package com.brotjefors.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brotjefors.shop.model.ListItem;

import java.util.List;

public interface ListItemRepository extends JpaRepository<ListItem, Long>{

    List<ListItem> findByShoppingListId(Long shoppingListId);

}
