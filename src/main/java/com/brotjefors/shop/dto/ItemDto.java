package com.brotjefors.shop.dto;

public class ItemDto {
    private String name;
    private Long categoryId;

    public ItemDto() {
    }

    public ItemDto(String name, Long categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}