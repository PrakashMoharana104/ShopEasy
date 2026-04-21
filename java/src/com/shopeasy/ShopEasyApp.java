package com.shopeasy;

import com.shopeasy.models.*;
import com.shopeasy.services.*;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * ShopEasyApp - Main application for ShopEasy E-Commerce Backend
 * This application demonstrates the use of:
 * - Object-Oriented Programming (Classes, Encapsulation)
 * - Collections Framework (ArrayList, List)
 * - Exception Handling (try-catch, throws)
 * - File I/O (Serialization, ObjectInputStream/ObjectOutputStream)
 * 
 * Topics from Syllabus Covered:
 * Module 1: Basic Java syntax and OOP concepts
 * Module 2: Classes, Objects, Inheritance concepts
 * Module 3: Collections - List, ArrayList usage
 * Module 4: Exception Handling and File I/O operations
 */
public class ShopEasyApp {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Welcome to ShopEasy E-Commerce");
        System.out.println("   Backend Management System");
        System.out.println("========================================\n");
        
        // Initialize default data on first run
        try {
            if (ProductService.getAllProducts().isEmpty()) {
                ProductService.initializeDefaultProducts();
                System.out.println("Default products initialized successfully!\n");
            }
        } catch (IOException e) {
            System.out.println("Error initializing products: " + e.getMessage());
        }
        
        mainMenu();
    }
    
    /**
     * Main menu of the application
     */
    private static void mainMenu() {
        boolean running = true;
        
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Browse Products");
            System.out.println("2. Manage Shopping Cart");
            System.out.println("3. View Orders");
            System.out.println("4. User Management");
            System.out.println("5. Admin Panel");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine().trim();
            
            try {
                switch (choice) {
                    case "1":
                        browseProducts();
                        break;
                    case "2":
                        manageCart();
                        break;
                    case "3":
                        viewOrders();
                        break;
                    case "4":
                        userManagement();
                        break;
                    case "5":
                        adminPanel();
                        break;
                    case "6":
                        running = false;
                        System.out.println("Thank you for using ShopEasy!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
    
    /**
     * Browse and search products
     */
    private static void browseProducts() {
        System.out.println("\n--- Browse Products ---");
        System.out.println("1. View All Products");
        System.out.println("2. Search Products");
        System.out.println("3. Filter by Category");
        System.out.print("Select: ");
        
        String choice = scanner.nextLine().trim();
        
        List<Product> products = null;
        
        if ("1".equals(choice)) {
            products = ProductService.getAllProducts();
            displayProducts(products);
        } else if ("2".equals(choice)) {
            System.out.print("Enter search keyword: ");
            String keyword = scanner.nextLine();
            products = ProductService.searchProducts(keyword);
            if (products.isEmpty()) {
                System.out.println("No products found.");
            } else {
                displayProducts(products);
            }
        } else if ("3".equals(choice)) {
            System.out.print("Enter category (Electronics/Clothing/Footwear): ");
            String category = scanner.nextLine();
            products = ProductService.getProductsByCategory(category);
            if (products.isEmpty()) {
                System.out.println("No products in this category.");
            } else {
                displayProducts(products);
            }
        }
    }
    
    /**
     * Display list of products
     */
    private static void displayProducts(List<Product> products) {
        System.out.println("\n--- Products ---");
        System.out.println(String.format("%-5s %-30s %-20s %-10s %-10s", 
            "ID", "Name", "Category", "Price", "Rating"));
        System.out.println("--------------------------------------------------------------------");
        
        for (Product product : products) {
            System.out.println(String.format("%-5d %-30s %-20s $%-9.2f %-10.1f", 
                product.getProductId(), 
                product.getName(), 
                product.getCategory(), 
                product.getPrice(), 
                product.getRating()));
        }
    }
    
    /**
     * Manage shopping cart
     */
    private static void manageCart() throws IOException {
        if (currentUser == null) {
            System.out.print("Enter User ID (or create new with format: user_123): ");
            String userId = scanner.nextLine().trim();
            currentUser = new User(userId, userId, "user@shopeasy.com", "password", userId);
        }
        
        System.out.println("\n--- Shopping Cart Management ---");
        System.out.println("1. View Cart");
        System.out.println("2. Add Item to Cart");
        System.out.println("3. Remove Item from Cart");
        System.out.println("4. Clear Cart");
        System.out.println("5. Checkout");
        System.out.print("Select: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                viewCart();
                break;
            case "2":
                addToCart();
                break;
            case "3":
                removeFromCart();
                break;
            case "4":
                CartService.clearCart(currentUser.getUserId());
                System.out.println("Cart cleared successfully!");
                break;
            case "5":
                checkout();
                break;
        }
    }
    
    /**
     * View current user's cart
     */
    private static void viewCart() {
        Cart cart = CartService.getCartByUserId(currentUser.getUserId());
        
        if (cart.getItems().isEmpty()) {
            System.out.println("\nCart is empty.");
            return;
        }
        
        System.out.println("\n--- Your Shopping Cart ---");
        System.out.println(String.format("%-30s %-10s %-10s %-15s", 
            "Product", "Qty", "Price", "Total"));
        System.out.println("-----------------------------------------------------------");
        
        for (CartItem item : cart.getItems()) {
            Product p = item.getProduct();
            System.out.println(String.format("%-30s %-10d $%-9.2f $%-14.2f", 
                p.getName(), 
                item.getQuantity(), 
                p.getPrice(), 
                item.getTotalPrice()));
        }
        
        System.out.println("-----------------------------------------------------------");
        System.out.println(String.format("Subtotal: $%.2f", cart.getCartTotal()));
        System.out.println(String.format("Tax (10%%): $%.2f", cart.getTax()));
        System.out.println(String.format("Total: $%.2f", cart.getFinalTotal()));
    }
    
    /**
     * Add item to cart
     */
    private static void addToCart() throws IOException {
        browseProducts();
        System.out.print("\nEnter Product ID to add: ");
        try {
            int productId = Integer.parseInt(scanner.nextLine());
            Product product = ProductService.getProductById(productId);
            
            if (product == null) {
                System.out.println("Product not found!");
                return;
            }
            
            System.out.print("Enter quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            
            CartItem item = new CartItem(product, quantity);
            CartService.addItemToCart(currentUser.getUserId(), item);
            System.out.println("Item added to cart successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }
    }
    
    /**
     * Remove item from cart
     */
    private static void removeFromCart() throws IOException {
        System.out.print("Enter Product ID to remove: ");
        try {
            int productId = Integer.parseInt(scanner.nextLine());
            CartService.removeItemFromCart(currentUser.getUserId(), productId);
            System.out.println("Item removed from cart!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }
    }
    
    /**
     * Checkout - Create order from cart
     */
    private static void checkout() throws IOException {
        Cart cart = CartService.getCartByUserId(currentUser.getUserId());
        
        if (cart.getItems().isEmpty()) {
            System.out.println("Your cart is empty!");
            return;
        }
        
        System.out.println("\n--- Checkout Summary ---");
        viewCart();
        System.out.print("\nConfirm order? (yes/no): ");
        
        if ("yes".equalsIgnoreCase(scanner.nextLine().trim())) {
            Order order = OrderService.createOrder(currentUser.getUserId(), cart);
            CartService.clearCart(currentUser.getUserId());
            
            System.out.println("\nOrder created successfully!");
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Total Amount: $" + order.getTotalAmount());
        }
    }
    
    /**
     * View user's orders
     */
    private static void viewOrders() {
        if (currentUser == null) {
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine().trim();
            currentUser = UserService.getUserById(userId);
            if (currentUser == null) {
                System.out.println("User not found!");
                return;
            }
        }
        
        List<Order> orders = OrderService.getOrdersByUserId(currentUser.getUserId());
        
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        
        System.out.println("\n--- Your Orders ---");
        System.out.println(String.format("%-20s %-15s %-15s", "Order ID", "Status", "Amount"));
        System.out.println("---------------------------------------------");
        
        for (Order order : orders) {
            System.out.println(String.format("%-20s %-15s $%-14.2f", 
                order.getOrderId(), 
                order.getStatus(), 
                order.getTotalAmount()));
        }
    }
    
    /**
     * User management
     */
    private static void userManagement() throws IOException {
        System.out.println("\n--- User Management ---");
        System.out.println("1. Register New User");
        System.out.println("2. Login");
        System.out.println("3. View User Profile");
        System.out.print("Select: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                registerUser();
                break;
            case "2":
                loginUser();
                break;
            case "3":
                viewProfile();
                break;
        }
    }
    
    /**
     * Register a new user
     */
    private static void registerUser() throws IOException {
        System.out.println("\n--- Register New User ---");
        System.out.print("User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();
        
        User user = new User(userId, username, email, password, fullName);
        UserService.registerUser(user);
        System.out.println("User registered successfully!");
        currentUser = user;
    }
    
    /**
     * Login user
     */
    private static void loginUser() {
        System.out.println("\n--- Login ---");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        User user = UserService.loginUser(email, password);
        if (user != null) {
            currentUser = user;
            System.out.println("Login successful! Welcome " + user.getFullName());
        } else {
            System.out.println("Invalid email or password!");
        }
    }
    
    /**
     * View user profile
     */
    private static void viewProfile() {
        if (currentUser == null) {
            System.out.println("No user logged in!");
            return;
        }
        
        System.out.println("\n--- User Profile ---");
        System.out.println("User ID: " + currentUser.getUserId());
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Full Name: " + currentUser.getFullName());
    }
    
    /**
     * Admin panel
     */
    private static void adminPanel() throws IOException {
        System.out.println("\n--- Admin Panel ---");
        System.out.println("Total Users: " + UserService.getTotalUserCount());
        System.out.println("Total Orders: " + OrderService.getTotalOrdersCount());
        System.out.println("Total Revenue: $" + String.format("%.2f", OrderService.getTotalRevenue()));
        System.out.println("Total Products: " + ProductService.getAllProducts().size());
    }
}
