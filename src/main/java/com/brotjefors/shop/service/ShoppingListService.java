package com.brotjefors.shop.service;

import com.brotjefors.shop.dto.ListItemDto;
import com.brotjefors.shop.dto.ShoppingListDto;
import com.brotjefors.shop.model.Item;
import com.brotjefors.shop.model.ListItem;
import com.brotjefors.shop.model.ShoppingList;
import com.brotjefors.shop.model.StoreCategory;
import com.brotjefors.shop.repository.ItemRepository;
import com.brotjefors.shop.repository.ListItemRepository;
import com.brotjefors.shop.repository.ShoppingListRepository;
import com.brotjefors.shop.repository.StoreCategoryRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;


@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ItemRepository itemRepository;
    private final StoreCategoryRepository storeCategoryRepository;
    private final ListItemRepository listItemRepository;
    private ItemService itemService;

    public ShoppingListService(
            ShoppingListRepository shoppingListRepository,
            ItemRepository itemRepository,
            StoreCategoryRepository storeCategoryRepository,
            ListItemRepository listItemRepository,
            ItemService itemService) {
        this.shoppingListRepository = shoppingListRepository;
        this.itemRepository = itemRepository;
        this.storeCategoryRepository = storeCategoryRepository;
        this.listItemRepository = listItemRepository;
        this.itemService = itemService;
    }

    public ShoppingList addShoppingList(ShoppingListDto shoppingListDto) {
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

    public ShoppingList addItemToShoppingList(ListItemDto listItemDto) {
        ShoppingList shoppingList = findShoppingListById(listItemDto.getListId());
        Item item = itemService.findItemById(listItemDto.getItemId());

        ListItem listItem = new ListItem();
        listItem.setShoppingList(shoppingList);
        listItem.setItem(item);
        listItem.setQuantity(listItemDto.getQuantity());

        shoppingList.addItem(listItem);
        return shoppingListRepository.save(shoppingList);
    }

    public List<ListItem> sortShoppingList(Long shoppingListId, Long storeId) {
        List<StoreCategory> storeCategories = storeCategoryRepository.findByStoreId(storeId);
        Map<Long, Integer> categoryIdToOrder = new HashMap<>();
        storeCategories.forEach(sc -> categoryIdToOrder.put(sc.getCategory().getId(), sc.getCategoryOrder().intValue()));

        List<ListItem> listItems = listItemRepository.findByShoppingListId(shoppingListId);


        return listItems.stream()
                .sorted(Comparator.comparingInt(item -> categoryIdToOrder.getOrDefault(item.getItem().getCategory().getId(), Integer.MAX_VALUE)))
                .collect(Collectors.toList());
    }
}