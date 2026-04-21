package com.shopeasy.models;

import java.io.Serializable;

/**
 * CartItem class represents an item in the shopping cart.
 * Implements Serializable for file I/O operations.
 */
public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Product product;
    private int quantity;
    
    // Constructor
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
    // Getters and Setters
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    /**
     * Calculate total price for this cart item
     */
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
    
    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product.getName() +
                ", quantity=" + quantity +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}
