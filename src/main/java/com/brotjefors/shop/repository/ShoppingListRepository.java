
package com.brotjefors.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brotjefors.shop.model.ShoppingList;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long>{

}
