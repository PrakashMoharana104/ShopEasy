package com.shopeasy.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Cart class represents a shopping cart containing multiple items.
 * Implements Serializable for file I/O operations.
 */
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String cartId;
    @SuppressWarnings("serial")
    private List<CartItem> items;
    private String userId;
    
    // Constructor
    public Cart(String cartId, String userId) {
        this.cartId = cartId;
        this.userId = userId;
        this.items = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getCartId() {
        return cartId;
    }
    
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public List<CartItem> getItems() {
        return items;
    }
    
    public void setItems(List<CartItem> items) {
        this.items = items;
    }
    
    /**
     * Add an item to the cart
     */
    public void addItem(CartItem item) {
        for (CartItem existingItem : items) {
            if (existingItem.getProduct().getProductId() == item.getProduct().getProductId()) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }
    
    /**
     * Remove an item from the cart
     */
    public void removeItem(int productId) {
        items.removeIf(item -> item.getProduct().getProductId() == productId);
    }
    
    /**
     * Calculate total price of all items in cart
     */
    public double getCartTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }
    
    /**
     * Calculate tax (10% of total)
     */
    public double getTax() {
        return getCartTotal() * 0.10;
    }
    
    /**
     * Get final total including tax
     */
    public double getFinalTotal() {
        return getCartTotal() + getTax();
    }
    
    /**
     * Clear the cart
     */
    public void clearCart() {
        items.clear();
    }
    
    /**
     * Get total number of items in cart
     */
    public int getItemCount() {
        return items.size();
    }
    
    @Override
    public String toString() {
        return "Cart{" +
                "cartId='" + cartId + '\'' +
                ", items=" + items.size() +
                ", total=" + getCartTotal() +
                '}';
    }
}
