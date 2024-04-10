package com.brotjefors.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.brotjefors.shop.dto.ListItemDto;
import com.brotjefors.shop.dto.ShoppingListDto;
import com.brotjefors.shop.model.Category;
import com.brotjefors.shop.model.Item;
import com.brotjefors.shop.model.ListItem;
import com.brotjefors.shop.model.ShoppingList;
import com.brotjefors.shop.model.Store;
import com.brotjefors.shop.model.StoreCategory;
import com.brotjefors.shop.repository.ItemRepository;
import com.brotjefors.shop.repository.ListItemRepository;
import com.brotjefors.shop.repository.ShoppingListRepository;
import com.brotjefors.shop.repository.StoreCategoryRepository;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingListServiceTest {

    @Mock
    private ShoppingListRepository shoppingListRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private StoreCategoryRepository storeCategoryRepository;
    @Mock
    private ListItemRepository listItemRepository;
    @Mock
    private ItemService itemService;

    @InjectMocks
    private ShoppingListService shoppingListService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenAddShoppingList_thenShoppingListIsSaved() {
        ShoppingListDto shoppingListDto = new ShoppingListDto();
        shoppingListDto.setName("Test List");
        shoppingListDto.setItems(new ArrayList<>());

        ShoppingList expectedShoppingList = new ShoppingList();
        expectedShoppingList.setName("Test List");

        when(itemRepository.findById(any())).thenReturn(Optional.of(new Item()));
        when(shoppingListRepository.save(any())).thenReturn(expectedShoppingList);

        ShoppingList result = shoppingListService.addShoppingList(shoppingListDto);

        assertNotNull(result);
        assertEquals(expectedShoppingList.getName(), result.getName());
        verify(shoppingListRepository).save(any(ShoppingList.class));
    }

    @Test
    void whenFindAllLists_thenAllListsReturned() {
        List<ShoppingList> expectedLists = List.of(new ShoppingList(), new ShoppingList());
        when(shoppingListRepository.findAll()).thenReturn(expectedLists);

        List<ShoppingList> result = shoppingListService.findAllLists();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(shoppingListRepository).findAll();
    }

    @Test
    void whenDeleteShoppingList_thenListIsDeleted() {
        Long id = 1L;
        when(shoppingListRepository.existsById(id)).thenReturn(true);

        boolean result = shoppingListService.deleteShoppingList(id);

        assertTrue(result);
        verify(shoppingListRepository).deleteById(id);
    }

    @Test
    void whenAddItemToShoppingList_thenItemIsAdded() {
        Long listId = 1L;
        Long itemId = 1L;
        Long quantity = 2L;

        // Use a real ShoppingList for spying to verify interactions with it
        ShoppingList shoppingList = new ShoppingList();
        ShoppingList spyShoppingList = spy(shoppingList);

        Item mockItem = new Item();

        ListItemDto listItemDto = new ListItemDto();
        listItemDto.setListId(listId);
        listItemDto.setItemId(itemId);
        listItemDto.setQuantity(quantity);

        // Setup mocks
        when(shoppingListRepository.findById(listId)).thenReturn(Optional.of(spyShoppingList));
        when(itemService.findItemById(itemId)).thenReturn(mockItem);
        when(shoppingListRepository.save(any(ShoppingList.class))).thenReturn(spyShoppingList);

        ShoppingList result = shoppingListService.addItemToShoppingList(listItemDto);

        assertNotNull(result);
        verify(spyShoppingList).addItem(any(ListItem.class));
        verify(shoppingListRepository).save(spyShoppingList);
    }

    @Test
    void whenSortShoppingList_thenItemsAreSortedByCategoryOrder() {
        Long shoppingListId = 1L;
        Long storeId = 1L;

        Store mockStore = new Store();
        mockStore.setId(storeId);
        Category fruits = new Category();
        fruits.setId(1L);
        fruits.setName("Fruits");
        Category vegetables = new Category();
        vegetables.setId(2L);
        vegetables.setName("Vegetables");

        StoreCategory fruitCategory = new StoreCategory();
        fruitCategory.setCategory(fruits);
        fruitCategory.setStore(mockStore);
        fruitCategory.setCategoryOrder(1L);

        StoreCategory vegetableCategory = new StoreCategory();
        vegetableCategory.setCategory(vegetables);
        vegetableCategory.setStore(mockStore);
        vegetableCategory.setCategoryOrder(2L);

        List<StoreCategory> storeCategories = List.of(fruitCategory, vegetableCategory);

        // Mock list items with categories assigned.
        Item appleItem = new Item();
        appleItem.setName("Apple");
        appleItem.setCategory(fruits);

        Item carrotItem = new Item();
        carrotItem.setName("Carrot");
        carrotItem.setCategory(vegetables);

        ListItem appleListItem = new ListItem();
        appleListItem.setItem(appleItem);
        appleListItem.setQuantity(2L);

        ListItem carrotListItem = new ListItem();
        carrotListItem.setItem(carrotItem);
        carrotListItem.setQuantity(3L);

        List<ListItem> listItems = List.of(appleListItem, carrotListItem);

        // Mocking repository responses.
        when(storeCategoryRepository.findByStoreId(storeId)).thenReturn(storeCategories);
        when(listItemRepository.findByShoppingListId(shoppingListId)).thenReturn(listItems);

        // Execute the method under test.
        List<ListItem> sortedItems = shoppingListService.sortShoppingList(shoppingListId, storeId);

        assertNotNull(sortedItems, "Sorted items should not be null");
        assertEquals(2, sortedItems.size(), "Sorted items list should contain two elements");
        assertEquals("Apple", sortedItems.get(0).getItem().getName(), "The first item should be 'Apple'");
        assertEquals("Carrot", sortedItems.get(1).getItem().getName(), "The second item should be 'Carrot'");

        // Verify sorting logic by category order.
        assertTrue(sortedItems.get(0).getItem().getCategory().getId().equals(fruits.getId()), "Apple should come before Carrot based on category order");
        assertTrue(sortedItems.get(1).getItem().getCategory().getId().equals(vegetables.getId()), "Carrot should come after Apple based on category order");
    }


}