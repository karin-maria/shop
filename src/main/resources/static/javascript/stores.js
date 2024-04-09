

function submitStoreForm(event) {
    event.preventDefault();
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

function deleteStoreCategory(){
    const storeCategoryId = document.getElementById('storeCategorySelect').value;

    if (!storeCategoryId) {
        alert('Please select a store category to delete.');
        return;
    }
    fetch(`/api/storeCategories/${storeCategoryId}`, {
        method: 'DELETE',
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        if (response.status === 204 || response.headers.get("Content-Length") === "0") {
            console.log('Store Category deleted successfully');
            return null; // Return null or a custom message since there's no JSON to parse
        } else {
            return response.json();
        }
    })
    .then(data => {
        if (data) {
            console.log('Response data:', data);
        } else {
            console.log('Store Category deleted successfully, no content returned');
        }
    })
    .then(() => {
        fetchStoreCategories();
        fetchAndDisplayStoreCategories();
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
