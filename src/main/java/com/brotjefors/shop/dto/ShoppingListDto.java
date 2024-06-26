package com.brotjefors.shop.dto;

import java.util.List;

public class ShoppingListDto {
    private String name;
    private List<ItemDetailDto> items;

    public ShoppingListDto() {
    }

    public ShoppingListDto(String name, List<ItemDetailDto> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public List<ItemDetailDto> getItems() {
        return items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<ItemDetailDto> items) {
        this.items = items;
    }

    public static class ItemDetailDto {
        private Long itemId;
        private Long quantity;

        public ItemDetailDto() {
        }

        public ItemDetailDto(Long itemId, Long quantity) {
            this.itemId = itemId;
            this.quantity = quantity;
        }

        public Long getItemId() {
            return itemId;
        }

        public Long getQuantity() {
            return quantity;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        public void setQuantity(Long quantity) {
            this.quantity = quantity;
        }
    }
}