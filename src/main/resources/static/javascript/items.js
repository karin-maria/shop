

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