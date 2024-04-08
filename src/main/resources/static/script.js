document.addEventListener('DOMContentLoaded', function() {
    fetchItems();
    fetchShoppingLists();
    fetchCategories();
    fetchStores();
});

function fetchItems() {
    fetch('/api/items/all')
        .then(response => response.json())
        .then(data => {
            const itemSelect = document.getElementById('itemSelect');
            itemSelect.innerHTML = '';
            data.forEach(item => {
                let option = new Option(item.name, item.id);
                itemSelect.add(option);
            });
            const itemSelect2 = document.getElementById('itemSelect2');
            itemSelect2.innerHTML = '';
            data.forEach(item => {
                let option = new Option(item.name, item.id);
                itemSelect2.add(option);
            });
        })
        .catch(error => console.error('Error fetching items:', error));
}

function fetchShoppingLists() {
    fetch('/api/shoppingLists/all')
        .then(response => response.json())
        .then(data => {
            const select = document.getElementById('shoppingListsSelect');
            shoppingListsSelect.innerHTML = '';
            data.forEach(list => {
                let option = new Option(list.name, list.id);
                select.add(option);
            });
            fetchAndDisplayShoppingList()
        })
        .catch(error => console.error('Failed to load shopping lists:', error));
}

function fetchCategories() {
    fetch('/api/categories/all')
        .then(response => response.json())
        .then(data => {
            const categorySelect = document.getElementById('categorySelect');
            categorySelect.innerHTML = '';
            data.forEach(category => {
                let option = new Option(category.name, category.id);
                categorySelect.add(option);
            });
            const categoryForStore = document.getElementById('categoryForStore');
            categoryForStore.innerHTML = '';
            data.forEach(category => {
                let option = new Option(category.name, category.id);
                categoryForStore.add(option);
            });
        })
        .catch(error => console.error('Error fetching categories:', error));
}

function fetchStores() {
    fetch('/api/stores/all')
        .then(response => response.json())
        .then(data => {
            const storeSelect = document.getElementById('storeSelect');
            storeSelect.innerHTML = '';
            data.forEach(store => {
                let option = new Option(store.name, store.id);
                storeSelect.add(option);
            });
        })
        .catch(error => console.error('Error fetching items:', error));
}

// ********************* CATEGORIES ********************************
function submitCategoryForm(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    const jsonData = Object.fromEntries(formData.entries());

    fetch('/api/categories/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonData),
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        event.target.reset(); // Reset form after submission
        fetchCategories();
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to add the category.');
    });
}

// ************************** ITEMS *******************************

function submitItemForm(event) {
    event.preventDefault();

    const categorySelect = document.getElementById('categorySelect');
    const categoryId = categorySelect.value;

    const formData = new FormData(event.target);
    const jsonData = {};
    jsonData['name'] = formData.get('name');
    jsonData['categoryId'] = parseInt(categorySelect.value, 10); // Convert to integer

    fetch('/api/items/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonData),
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        console.log(data);
        fetchItems();
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// ******************** SHOPPING LISTS *************************

let shoppingListItems = [];

function addItemToList() {
    const itemSelect = document.getElementById('itemSelect');
    const quantityInput = document.getElementById('quantity');
    const itemName = itemSelect.options[itemSelect.selectedIndex].text;
    const itemId = itemSelect.value;
    const quantity = quantityInput.value;

    if (!itemId || quantity <= 0) {
        alert('Please select an item and enter a positive quantity.');
        return;
    }

    shoppingListItems.push({
        itemId,
        quantity
    });

    const itemList = document.getElementById('itemList');
    itemList.innerHTML += `<div class="item">${itemName}</div>`;
    itemList.innerHTML += `<div class="item">${quantity}</div>`;

    itemSelect.selectedIndex = 0;
    quantityInput.value = '';
}

function addItemToExistingList(event) {
    event.preventDefault();
    const listId = document.getElementById('shoppingListsSelect').value;
    const itemSelect = document.getElementById('itemSelect2');
    const quantityInput = document.getElementById('quantity2');
    const itemId = itemSelect.value;
    const quantity = quantityInput.value;

    if (!itemId || quantity <= 0) {
        alert('Please select an item and enter a positive quantity.');
        return;
    }

    fetch(`/api/shoppingLists/${listId}/addItem`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            listId: listId,
            itemId: itemId,
            quantity: quantity
        }),
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        document.getElementById('shoppingListDetails').innerHTML = '';
        fetchShoppingLists();
    })
    .catch(error => {
        console.error('Error:', error);
        console.log(response)
        console.log(response.body)
        alert('Failed to create the shopping list.');
    });
}

function createShoppingList(event) {
    event.preventDefault();
    const listNameInput = document.getElementById('listName');
    const listName = listNameInput.value;

    if (!listName.trim() || shoppingListItems.length === 0) {
        alert('Please enter a list name and add at least one item.');
        return;
    }

    fetch('/api/shoppingLists/add', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            name: listName,
            items: shoppingListItems
        }),
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        // Reset UI
        listNameInput.value = '';
        document.getElementById('itemList').innerHTML = '';
        shoppingListItems = []; // Clear the in-memory list
        fetchShoppingLists();
    })
    .catch(error => {
        console.error('Error:', error);
        console.log(response)
        console.log(response.body)
        alert('Failed to create the shopping list.');
    });
}

// VIEW
function fetchAndDisplayShoppingList() {
    const id = document.getElementById('shoppingListsSelect').value;
    fetch(`/api/shoppingLists/${id}`)
        .then(response => response.json())
        .then(data => {
            const detailsDiv = document.getElementById('shoppingListDetails');
            detailsDiv.innerHTML = "";
            data.items.forEach(item => {
                detailsDiv.innerHTML += `<div class="item">${item.item.name}</div>`;
                detailsDiv.innerHTML += `<div class="item">${item.quantity}</div>`;
            });
        })
        .catch(error => console.error('Failed to load shopping list details:', error));
}

// STORES
function addStore() {
    const storeName = document.getElementById('storeName').value;

    if (!storeName.trim()) {
        alert('Please enter a store name.');
        return;
    }

    fetch('/api/stores/add', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ name: storeName }),
    })
    .then(response => response.json())
    .then(data => {
        console.log('Store added:', data);
        // Reset the form or additional UI actions
        document.getElementById('addStoreForm').reset();
        fetchStores();
    })
    .catch(error => {
        console.error('Error adding store:', error);
    });
}

function deleteStore(){
    const storeId = document.getElementById('storeSelect').value;

    if (!storeId) {
        alert('Please select a store to delete.');
        return;
    }
    fetch(`/api/stores/${storeId}`, {
        method: 'DELETE',
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        if (response.status === 204 || response.headers.get("Content-Length") === "0") {
            console.log('Store deleted successfully');
            return null; // Return null or a custom message since there's no JSON to parse
        } else {
            return response.json();
        }
    })
    .then(data => {
        if (data) {
            console.log('Response data:', data);
        } else {
            console.log('Store deleted successfully, no content returned');
        }
    })
    .then(() => {
        fetchStores();
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function addCategoryToStore() {
    const storeId = document.getElementById('storeSelect').value;
    const categoryId = document.getElementById('categoryForStore').value;
    const categoryOrder = document.getElementById('categoryOrder').value;

    if (!storeId.trim() || !categoryId.trim() || !categoryOrder.trim()) {
        alert('Please fill in all fields.');
        return;
    }

    fetch('/api/storeCategories/add', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            storeId: parseInt(storeId, 10),
            categoryId: parseInt(categoryId, 10),
            order: parseInt(categoryOrder, 10)
        }),
    })
    .then(response => response.json())
    .then(data => {
        console.log('Category added to store:', data);
        // Reset the form or additional UI actions
        document.getElementById('addCategoryToStoreForm').reset();
    })
    .catch(error => {
        console.error('Error adding category to store:', error);
        alert('Failed to add category to store.');
    });
}

function fetchAndDisplayStoreCategories() {
    const storeId = document.getElementById('storeSelect').value;
    if (!storeId) {
        document.getElementById('storeCategoriesDiv').innerHTML = 'Please select a store.';
        return;
    }

    fetch(`/api/stores/${storeId}/categories`)
        .then(response => response.json())
        .then(data => {
            const displayDiv = document.getElementById('storeCategoriesDiv');
            // Clear previous content
            displayDiv.innerHTML = '';
            if (data.length === 0) {
                displayDiv.innerHTML = 'No categories found for this store.';
                return;
            }
            data.forEach(storeCategory => {
                displayDiv.innerHTML += `<div class="item">${storeCategory.category.name}</div>`;
                displayDiv.innerHTML += `<div class="item">${storeCategory.categoryOrder}</div>`;
            });
        })
        .catch(error => {
            console.error('Failed to load store categories:', error);
            document.getElementById('storeCategoriesDiv').innerHTML = 'Failed to load categories.';
        });
}

function sortShoppingList() {
    const id = document.getElementById('shoppingListsSelect').value;
    fetch(`/api/shoppingLists/${id}`)
        .then(response => response.json())
        .then(data => {

            data.items.sort((a, b) => b.item.category.order - a.item.category.order);

            const sortedDiv = document.getElementById('sortedListDiv');
            sortedDiv.innerHTML = '';

            data.items.forEach(item => {
                sortedDiv.innerHTML += `<div class="item">${item.item.name}</div>`;
                sortedDiv.innerHTML += `<div class="item">${item.quantity}</div>`;
            });
        })
        .catch(error => console.error('Failed to load shopping list details:', error));
}