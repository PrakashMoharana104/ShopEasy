// Cart functionality
const CART_KEY = 'shopeasy_cart';

// Get current user ID (from localStorage or session)
function getUserId() {
    return localStorage.getItem('userId') || 'guest_' + Date.now();
}

// Add item to cart
function addToCart(e) {
    const button = e.target;
    const productCard = button.closest('.product-card');
    const productName = productCard.querySelector('.product-title').textContent;
    const productPrice = productCard.querySelector('.product-price').textContent.replace('$', '');
    const userId = getUserId();

    // Try to add via API, fallback to localStorage if offline
    fetch('/api/cart/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            userId: userId,
            productName: productName,
            price: parseFloat(productPrice)
        })
    })
    .then(response => response.json())
    .then(data => {
        if (data.status === 'success') {
            showFeedback(button, 'Added!', true);
            updateCartCount();
        } else {
            // Fallback to localStorage
            addToCartLocal(productName, productPrice);
        }
    })
    .catch(error => {
        console.log('Backend offline, using local storage');
        addToCartLocal(productName, productPrice);
    });
}

// Fallback: Add to localStorage
function addToCartLocal(productName, productPrice) {
    let cart = JSON.parse(localStorage.getItem(CART_KEY)) || [];
    
    const existingItem = cart.find(item => item.name === productName);
    if (existingItem) {
        existingItem.quantity += 1;
    } else {
        cart.push({
            name: productName,
            price: productPrice,
            quantity: 1
        });
    }
    
    localStorage.setItem(CART_KEY, JSON.stringify(cart));
    updateCartCount();
}

// Show feedback animation
function showFeedback(button, text, success) {
    const originalText = button.textContent;
    button.textContent = text;
    button.style.backgroundColor = success ? '#28a745' : '#dc3545';
    
    setTimeout(() => {
        button.textContent = originalText;
        button.style.backgroundColor = '';
    }, 1500);
}

// Update cart count display
function updateCartCount() {
    const cartCount = document.querySelector('.cart-count');
    if (cartCount) {
        const cart = JSON.parse(localStorage.getItem(CART_KEY)) || [];
        const totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);
        cartCount.textContent = totalItems;
    }
}

// Get cart items
function getCart() {
    return JSON.parse(localStorage.getItem(CART_KEY)) || [];
}

// Clear cart
function clearCart() {
    localStorage.removeItem(CART_KEY);
    updateCartCount();
}

// Initialize cart display on page load
document.addEventListener('DOMContentLoaded', function() {
    updateCartCount();
    
    // Add event listeners to add-to-cart buttons
    const addToCartButtons = document.querySelectorAll('.add-to-cart');
    addToCartButtons.forEach(button => {
        button.addEventListener('click', addToCart);
    });
});
