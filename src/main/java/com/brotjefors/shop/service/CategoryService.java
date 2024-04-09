package com.brotjefors.shop.service;

import com.brotjefors.shop.model.Category;
import com.brotjefors.shop.repository.CategoryRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return categoryRepository.findByName(category.getName())
                .orElseGet(() -> categoryRepository.save(category));
    }

    public Iterable<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(categoryDetails.getName());
                    return categoryRepository.save(category);
                }).orElse(null);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}