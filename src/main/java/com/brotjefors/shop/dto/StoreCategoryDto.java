package com.brotjefors.shop.dto;

public class StoreCategoryDto {
    private Long storeId;
    private Long categoryId;
    private Long order;

    public StoreCategoryDto() {
    }

    public StoreCategoryDto(Long storeId, Long categoryId, Long order) {
        this.storeId = storeId;
        this.categoryId = categoryId;
        this.order = order;
    }

    public Long getStoreId() {
        return storeId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getOrder() {
        return order;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setOrder(Long order) {
        this.order = order;
    }
    
}
