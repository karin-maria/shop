

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

async function addItemToExistingList(event) {
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

    try {
        const response = await fetch(`/api/shoppingLists/${listId}/addItem`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({listId, itemId, quantity}),
        });

        if (!response.ok) {
            // This will catch any response that's not a 2xx success status
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();
        console.log(data);
        document.getElementById('shoppingListDetails').innerHTML = '';
        fetchShoppingLists();

    } catch (error) {
        console.error('Error:', error);
        alert('Failed to add the item to the shopping list.');
    }
}

async function createShoppingList(event) {
    event.preventDefault();
    const listNameInput = document.getElementById('listName');
    const listName = listNameInput.value;

    if (!listName.trim() || shoppingListItems.length === 0) {
        alert('Please enter a list name and add at least one item.');
        return;
    }

    try {
        const response = await fetch('/api/shoppingLists/add', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                name: listName,
                items: shoppingListItems
            }),
        });

        if (!response.ok) {
            // If the server responds with a bad status, we throw an error to jump to the catch block
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();
        console.log(data);

        listNameInput.value = '';
        document.getElementById('itemList').innerHTML = '';
        shoppingListItems = [];

    } catch (error) {
        console.error('Error:', error);
        alert('Failed to create the shopping list.');
    }
}