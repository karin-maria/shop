package com.brotjefors.shop.controller;

import com.brotjefors.shop.model.Category;
import com.brotjefors.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @GetMapping("/all")
    public Iterable<Category> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.findCategoryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}