package com.shopeasy.services;

import com.shopeasy.models.Cart;
import com.shopeasy.models.CartItem;
import com.shopeasy.utils.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CartService handles all shopping cart operations.
 * Uses FileManager for persistent storage of carts.
 */
public class CartService {
    
    /**
     * Get all carts from storage
     * @return List of all carts
     */
    @SuppressWarnings("unchecked")
    public static List<Cart> getAllCarts() {
        List<?> allData = FileManager.loadFromFile(FileManager.getCartsFile());
        return new ArrayList<>((List<Cart>) (List<?>) allData);
    }
    
    /**
     * Get cart by ID
     * @param cartId The cart ID
     * @return The cart if found, null otherwise
     */
    public static Cart getCartById(String cartId) {
        List<Cart> carts = getAllCarts();
        for (Cart cart : carts) {
            if (cart.getCartId().equals(cartId)) {
                return cart;
            }
        }
        return null;
    }
    
    /**
     * Get cart by user ID
     * @param userId The user ID
     * @return The user's cart, or create a new one if not exists
     */
    public static Cart getCartByUserId(String userId) {
        List<Cart> carts = getAllCarts();
        for (Cart cart : carts) {
            if (cart.getUserId().equals(userId)) {
                return cart;
            }
        }
        // Create new cart if not exists
        return new Cart("cart_" + userId, userId);
    }
    
    /**
     * Save cart to storage
     * @param cart The cart to save
     * @throws IOException If file operation fails
     */
    public static void saveCart(Cart cart) throws IOException {
        List<Cart> carts = getAllCarts();
        boolean exists = false;
        
        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getCartId().equals(cart.getCartId())) {
                carts.set(i, cart);
                exists = true;
                break;
            }
        }
        
        if (!exists) {
            carts.add(cart);
        }
        
        FileManager.saveToFile(FileManager.getCartsFile(), carts);
    }
    
    /**
     * Add item to cart
     * @param userId The user ID
     * @param item The cart item to add
     * @throws IOException If file operation fails
     */
    public static void addItemToCart(String userId, CartItem item) throws IOException {
        Cart cart = getCartByUserId(userId);
        cart.addItem(item);
        saveCart(cart);
    }
    
    /**
     * Remove item from cart
     * @param userId The user ID
     * @param productId The product ID to remove
     * @throws IOException If file operation fails
     */
    public static void removeItemFromCart(String userId, int productId) throws IOException {
        Cart cart = getCartByUserId(userId);
        cart.removeItem(productId);
        saveCart(cart);
    }
    
    /**
     * Clear user's cart
     * @param userId The user ID
     * @throws IOException If file operation fails
     */
    public static void clearCart(String userId) throws IOException {
        Cart cart = getCartByUserId(userId);
        cart.clearCart();
        saveCart(cart);
    }
    
    /**
     * Get cart total for user
     * @param userId The user ID
     * @return The cart total
     */
    public static double getCartTotal(String userId) {
        Cart cart = getCartByUserId(userId);
        return cart.getCartTotal();
    }
}
