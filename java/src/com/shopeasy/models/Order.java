package com.shopeasy.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Order class represents a customer order.
 * Implements Serializable for file I/O operations.
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    private String userId;
    private Cart cart;
    private String status;
    private Date orderDate;
    private double totalAmount;
    
    // Constructor
    public Order(String orderId, String userId, Cart cart, double totalAmount) {
        this.orderId = orderId;
        this.userId = userId;
        this.cart = cart;
        this.totalAmount = totalAmount;
        this.status = "Pending";
        this.orderDate = new Date();
    }
    
    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public Cart getCart() {
        return cart;
    }
    
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", status='" + status + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
