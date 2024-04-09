


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
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to add the category');
        }
        return response.json();
    })
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