package com.brotjefors.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brotjefors.shop.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    Optional<Category> findByName(String name);

}
