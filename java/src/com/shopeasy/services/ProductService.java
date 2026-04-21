package com.shopeasy.services;

import com.shopeasy.models.Product;
import com.shopeasy.utils.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductService handles all product-related operations.
 * Uses FileManager for persistent storage via file I/O.
 */
public class ProductService {
    
    /**
     * Get all products from storage
     * @return List of all products
     */
    @SuppressWarnings("unchecked")
    public static List<Product> getAllProducts() {
        List<?> allData = FileManager.loadFromFile(FileManager.getProductsFile());
        return new ArrayList<>((List<Product>) (List<?>) allData);
    }
    
    /**
     * Add a new product
     * @param product The product to add
     * @throws IOException If file operation fails
     */
    public static void addProduct(Product product) throws IOException {
        List<Product> products = getAllProducts();
        products.add(product);
        FileManager.saveToFile(FileManager.getProductsFile(), products);
    }
    
    /**
     * Get a product by ID
     * @param productId The product ID to search for
     * @return The product if found, null otherwise
     */
    public static Product getProductById(int productId) {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }
    
    /**
     * Get products by category
     * @param category The category to filter by
     * @return List of products in the category
     */
    public static List<Product> getProductsByCategory(String category) {
        List<Product> products = getAllProducts();
        List<Product> filtered = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                filtered.add(product);
            }
        }
        return filtered;
    }
    
    /**
     * Update a product
     * @param product The product to update
     * @throws IOException If file operation fails
     */
    public static void updateProduct(Product product) throws IOException {
        List<Product> products = getAllProducts();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId() == product.getProductId()) {
                products.set(i, product);
                FileManager.saveToFile(FileManager.getProductsFile(), products);
                return;
            }
        }
    }
    
    /**
     * Delete a product by ID
     * @param productId The product ID to delete
     * @throws IOException If file operation fails
     */
    public static void deleteProduct(int productId) throws IOException {
        List<Product> products = getAllProducts();
        products.removeIf(p -> p.getProductId() == productId);
        FileManager.saveToFile(FileManager.getProductsFile(), products);
    }
    
    /**
     * Search products by name
     * @param keyword The search keyword
     * @return List of products matching the keyword
     */
    public static List<Product> searchProducts(String keyword) {
        List<Product> products = getAllProducts();
        List<Product> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(lowerKeyword) ||
                product.getDescription().toLowerCase().contains(lowerKeyword)) {
                results.add(product);
            }
        }
        return results;
    }
    
    /**
     * Initialize default products (call once)
     * @throws IOException If file operation fails
     */
    public static void initializeDefaultProducts() throws IOException {
        List<Product> products = new ArrayList<>();
        
        products.add(new Product(1, "Wireless Headphones", "Premium quality headphones", 89.99, "Electronics", 50, 4.5));
        products.add(new Product(2, "Smart Watch Series 5", "Latest smart watch", 199.99, "Electronics", 30, 4.7));
        products.add(new Product(3, "Premium Cotton T-Shirt", "High quality T-shirt", 24.99, "Clothing", 100, 4.3));
        products.add(new Product(4, "Portable Bluetooth Speaker", "Portable speaker", 59.99, "Electronics", 45, 4.6));
        products.add(new Product(5, "Casual Jeans", "Comfortable jeans", 49.99, "Clothing", 60, 4.4));
        products.add(new Product(6, "Running Shoes", "Sports shoes", 79.99, "Footwear", 40, 4.5));
        
        FileManager.saveToFile(FileManager.getProductsFile(), products);
    }
}
