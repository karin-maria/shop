document.addEventListener('DOMContentLoaded', async function() {
    try {
        await Promise.all([
            fetchItems(),
            fetchShoppingLists(),
            fetchCategories(),
            fetchStores(),
            fetchStoreCategories()
        ]);
        fetchAndDisplayShoppingList();
        fetchAndDisplayStoreCategories();
        fetchAndDisplaySortedList();
    } catch (error) {
        console.error("Error during initial data fetch: ", error);
    }

    const addItemToListButton = document.getElementById('addItemToListButton');
    if (addItemToListButton) {
        addItemToListButton.addEventListener('click', async (event) => {
            await addItemToExistingList(event);
            await fetchAndDisplayShoppingList();
            fetchAndDisplaySortedList(); // Adjust based on async behavior
        });
    } else {
        console.error('addItemToListButton not found');
    }

    const addShoppingListButton = document.getElementById('addShoppingListButton');
    if(addShoppingListButton){
        addShoppingListButton.addEventListener('click', async (event) => {
            await createShoppingList(event);
            await fetchShoppingLists();
            fetchAndDisplayShoppingList();
            fetchAndDisplaySortedList();
    });
    } else {
        console.error('addShoppingListButton not found');
    }

});

async function fetchCategories() {
    try {
        const response = await fetch('/api/categories/all');
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
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
    } catch (error) {
        console.error('Error fetching categories:', error);
    }
}


async function fetchItems() {
    try {
        const response = await fetch('/api/items/all');
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
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
    } catch (error) {
        console.error('Error fetching items:', error);
    }
}

async function fetchShoppingLists() {
    try {
        const response = await fetch('/api/shoppingLists/all');
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
        const shoppingListsSelect = document.getElementById('shoppingListsSelect');
        shoppingListsSelect.innerHTML = '';
        data.forEach(list => {
            let option = new Option(list.name, list.id);
            shoppingListsSelect.add(option);
        });
    } catch (error) {
        console.error('Error fetching shopping lists:', error);
    }
}

async function fetchStores() {
    try {
        const response = await fetch('/api/stores/all');
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
        const storeSelect = document.getElementById('storeSelect');
        storeSelect.innerHTML = '';
        data.forEach(store => {
            let option = new Option(store.name, store.id);
            storeSelect.add(option);
        });
    } catch (error) {
        console.error('Error fetching stores:', error);
    }
}

async function fetchStoreCategories() {
    try {
        const response = await fetch('/api/storeCategories/all');
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
        const storeCategorySelect = document.getElementById('storeCategorySelect');
        storeCategorySelect.innerHTML = '';
        data.forEach(storeCategory => {
            let option = new Option(storeCategory.category.name, storeCategory.id);
            storeCategorySelect.add(option);
        });
    } catch (error) {
        console.error('Error fetching store categories:', error);
    }
}

function fetchAndDisplayShoppingList() {
    const id = document.getElementById('shoppingListsSelect').value;
    if (!id) {
        console.error('No ID selected');
        return;
    }
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

function fetchAndDisplaySortedList() {
    const shoppingListId = document.getElementById('shoppingListsSelect').value;
    const storeId = document.getElementById('storeSelect').value;
    const sortedDiv = document.getElementById('sortedListDiv');

    if (!shoppingListId || !storeId) {
        alert('Please select both a shopping list and a store.');
        return;
    }

    fetch(`/api/shoppingLists/${shoppingListId}/sort?storeId=${storeId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            // Clear previous content
            sortedDiv.innerHTML = '';

            data.forEach(item => {
                sortedDiv.innerHTML += `<div class="item">${item.item.name}</div>`;
                sortedDiv.innerHTML += `<div class="item">${item.quantity}</div>`;
            });
        })
        .catch(error => {
            console.error('Fetch Error:', error);
            sortedDiv.innerHTML = 'Failed to load the sorted shopping list.';
        });
}