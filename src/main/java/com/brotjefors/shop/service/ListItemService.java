package com.brotjefors.shop.service;

import org.springframework.stereotype.Service;

import com.brotjefors.shop.model.ListItem;
import com.brotjefors.shop.repository.ListItemRepository;


@Service
public class ListItemService {

    private ListItemRepository listItemRepository;

    public ListItem addListItem(ListItem listItem) {
        return listItemRepository.save(listItem);
    }

    public ListItem findListItemById(Long id) {
        return listItemRepository.findById(id).orElse(null);
    }

    public void deleteListItem(Long id) {
        listItemRepository.deleteById(id);
    }
}