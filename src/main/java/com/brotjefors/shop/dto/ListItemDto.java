package com.brotjefors.shop.dto;

public class ListItemDto {
    private Long listId;
    private Long itemId;
    Long quantity;

    public ListItemDto() {
    }

    public ListItemDto(Long listId, Long itemId, Long quantity) {
        this.listId = listId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public Long getListId() {
        return listId;
    }

    public Long getItemId() {
        return itemId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}