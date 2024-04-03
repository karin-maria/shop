package com.brotjefors.shop.service;

import com.brotjefors.shop.dto.ShoppingListDto;
import com.brotjefors.shop.model.Item;
import com.brotjefors.shop.model.ListItem;
import com.brotjefors.shop.model.ShoppingList;
import com.brotjefors.shop.repository.ItemRepository;
import com.brotjefors.shop.repository.ShoppingListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ItemRepository itemRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, ItemRepository itemRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.itemRepository = itemRepository;
    }

    public ShoppingList saveShoppingList(ShoppingListDto shoppingListDto) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(shoppingListDto.getName());

        List<ListItem> listItems = shoppingListDto.getItems().stream()
            .map(itemDetail -> {
                Item item = itemRepository.findById(itemDetail.getItemId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));
                ListItem listItem = new ListItem();
                listItem.setItem(item);
                listItem.setQuantity(itemDetail.getQuantity());
                listItem.setShoppingList(shoppingList);
                return listItem;
            })
            .collect(Collectors.toList());

        shoppingList.setItems(listItems.stream().collect(Collectors.toSet()));
        return shoppingListRepository.save(shoppingList);
    }


    public List<ShoppingList> findAllLists() {
        return shoppingListRepository.findAll();
    }

    public ShoppingList findShoppingListById(Long id) {
        Optional<ShoppingList> shoppingList = shoppingListRepository.findById(id);
        if (shoppingList.isPresent()) {
            return shoppingList.get();
        } else {
            return null;
        }
    }

    public ShoppingList updateShoppingList(Long id, ShoppingList shoppingListDetails) {
        ShoppingList shoppingList = findShoppingListById(id);
        if (shoppingList != null) {
            shoppingList.setName(shoppingListDetails.getName());
            return shoppingListRepository.save(shoppingList);
        } else {
            return null;
        }
    }

    public boolean deleteShoppingList(Long id) {
        if (shoppingListRepository.existsById(id)) {
            shoppingListRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}