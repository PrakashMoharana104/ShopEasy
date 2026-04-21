// Search functionality
const API_URL = 'http://localhost:8080/api';

document.addEventListener('DOMContentLoaded', function() {
    initializeSearch();
});

function initializeSearch() {
    const searchIcon = document.querySelector('.search-icon');
    const searchModal = document.getElementById('searchModal');
    const closeSearchBtn = document.getElementById('closeSearch');
    const searchInput = document.getElementById('searchInput');
    const searchForm = document.getElementById('searchForm');

    if (!searchIcon || !searchModal) return;

    // Open search modal
    searchIcon.addEventListener('click', function(e) {
        e.preventDefault();
        searchModal.classList.add('active');
        searchInput.focus();
    });

    // Close search modal
    closeSearchBtn.addEventListener('click', function() {
        searchModal.classList.remove('active');
    });

    // Close when clicking outside modal content
    searchModal.addEventListener('click', function(e) {
        if (e.target === searchModal) {
            searchModal.classList.remove('active');
        }
    });

    // Handle search form submission
    searchForm.addEventListener('submit', function(e) {
        e.preventDefault();
        performSearch();
    });

    // Live search as user types
    searchInput.addEventListener('input', function() {
        if (this.value.trim().length > 0) {
            performSearch();
        } else {
            document.getElementById('searchResults').innerHTML = '';
        }
    });

    // Close modal on Escape key
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape' && searchModal.classList.contains('active')) {
            searchModal.classList.remove('active');
        }
    });
}

function performSearch() {
    const searchInput = document.getElementById('searchInput');
    const query = searchInput.value.toLowerCase().trim();
    const resultsContainer = document.getElementById('searchResults');

    if (query.length === 0) {
        resultsContainer.innerHTML = '';
        return;
    }

    // Search from backend API
    fetch(API_URL + '/search?q=' + encodeURIComponent(query))
        .then(response => response.json())
        .then(products => {
            if (!Array.isArray(products) || products.length === 0) {
                resultsContainer.innerHTML = '<div class="no-results">No products found. Try a different search.</div>';
            } else {
                resultsContainer.innerHTML = products.map(product => `
                    <div class="search-result-item">
                        <h4>${product.name}</h4>
                        <p class="price">$${parseFloat(product.price).toFixed(2)}</p>
                        <p class="category">${product.category}</p>
                    </div>
                `).join('');
            }
        })
        .catch(error => {
            console.log('Backend unavailable, using fallback products');
            performSearchFallback(query, resultsContainer);
        });
}

// Fallback: Search with hardcoded products if backend is offline
function performSearchFallback(query, resultsContainer) {
    const products = [
        { name: 'Wireless Headphones', price: '$89.99', category: 'Electronics' },
        { name: 'Smart Watch Series 5', price: '$199.99', category: 'Electronics' },
        { name: 'Premium Cotton T-Shirt', price: '$24.99', category: 'Clothing' },
        { name: 'Portable Bluetooth Speaker', price: '$59.99', category: 'Electronics' },
        { name: 'Wireless Mouse', price: '$29.99', category: 'Electronics' },
        { name: 'USB-C Charging Cable', price: '$12.99', category: 'Electronics' },
        { name: 'Laptop Stand', price: '$39.99', category: 'Electronics' },
        { name: 'Mechanical Keyboard', price: '$79.99', category: 'Electronics' },
        { name: 'Phone Case', price: '$19.99', category: 'Accessories' },
        { name: 'Screen Protector', price: '$9.99', category: 'Accessories' },
        { name: 'Running Shoes', price: '$89.99', category: 'Clothing' },
        { name: 'Winter Jacket', price: '$129.99', category: 'Clothing' },
        { name: 'Coffee Maker', price: '$49.99', category: 'Home & Kitchen' },
        { name: 'Blender', price: '$69.99', category: 'Home & Kitchen' },
        { name: 'Desk Lamp', price: '$34.99', category: 'Home & Kitchen' },
        { name: 'Notebook Set', price: '$14.99', category: 'Student\'s Corner' },
        { name: 'Pen Pack', price: '$9.99', category: 'Student\'s Corner' },
        { name: 'Calculator', price: '$19.99', category: 'Student\'s Corner' }
    ];

    const results = products.filter(product => 
        product.name.toLowerCase().includes(query) || 
        product.category.toLowerCase().includes(query)
    );

    if (results.length === 0) {
        resultsContainer.innerHTML = '<div class="no-results">No products found. Try a different search.</div>';
    } else {
        resultsContainer.innerHTML = results.map(product => `
            <div class="search-result-item">
                <h4>${product.name}</h4>
                <p class="price">${product.price}</p>
                <p class="category">${product.category}</p>
            </div>
        `).join('');
    }
}
