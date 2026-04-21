package com.shopeasy.services;

import com.shopeasy.models.Order;
import com.shopeasy.models.Cart;
import com.shopeasy.utils.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderService handles all order-related operations.
 * Uses FileManager for persistent storage of orders.
 */
public class OrderService {
    
    /**
     * Get all orders from storage
     * @return List of all orders
     */
    @SuppressWarnings("unchecked")
    public static List<Order> getAllOrders() {
        List<?> allData = FileManager.loadFromFile(FileManager.getOrdersFile());
        return new ArrayList<>((List<Order>) (List<?>) allData);
    }
    
    /**
     * Create a new order from a cart
     * @param userId The user ID
     * @param cart The cart to convert to order
     * @return The created order
     * @throws IOException If file operation fails
     */
    public static Order createOrder(String userId, Cart cart) throws IOException {
        String orderId = "ORD_" + System.currentTimeMillis();
        Order order = new Order(orderId, userId, cart, cart.getFinalTotal());
        order.setStatus("Confirmed");
        
        List<Order> orders = getAllOrders();
        orders.add(order);
        FileManager.saveToFile(FileManager.getOrdersFile(), orders);
        
        return order;
    }
    
    /**
     * Get order by ID
     * @param orderId The order ID
     * @return The order if found, null otherwise
     */
    public static Order getOrderById(String orderId) {
        List<Order> orders = getAllOrders();
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }
    
    /**
     * Get all orders for a user
     * @param userId The user ID
     * @return List of user's orders
     */
    public static List<Order> getOrdersByUserId(String userId) {
        List<Order> orders = getAllOrders();
        List<Order> userOrders = new ArrayList<>();
        
        for (Order order : orders) {
            if (order.getUserId().equals(userId)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }
    
    /**
     * Update order status
     * @param orderId The order ID
     * @param status The new status
     * @throws IOException If file operation fails
     */
    public static void updateOrderStatus(String orderId, String status) throws IOException {
        List<Order> orders = getAllOrders();
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus(status);
                FileManager.saveToFile(FileManager.getOrdersFile(), orders);
                return;
            }
        }
    }
    
    /**
     * Get total orders count
     * @return Number of orders
     */
    public static int getTotalOrdersCount() {
        return getAllOrders().size();
    }
    
    /**
     * Get total revenue
     * @return Total revenue from all orders
     */
    public static double getTotalRevenue() {
        List<Order> orders = getAllOrders();
        double total = 0;
        for (Order order : orders) {
            if ("Confirmed".equalsIgnoreCase(order.getStatus())) {
                total += order.getTotalAmount();
            }
        }
        return total;
    }
}
