// Main initialization script
document.addEventListener('DOMContentLoaded', function() {
    initializeNavigation();
    initializeCart();
});

function initializeNavigation() {
    const navLinks = document.querySelectorAll('nav a');
    
    navLinks.forEach(link => {
        // Set active link based on current page
        if (link.href === window.location.href) {
            link.classList.add('active');
        } else {
            link.classList.remove('active');
        }
        
        // Add click listener for smooth interaction
        link.addEventListener('click', function(e) {
            if (this.getAttribute('href') === '#') {
                e.preventDefault();
            }
        });
    });
}

function initializeCart() {
    const addToCartButtons = document.querySelectorAll('.add-to-cart');
    
    addToCartButtons.forEach(button => {
        button.addEventListener('click', addToCart);
    });
}
