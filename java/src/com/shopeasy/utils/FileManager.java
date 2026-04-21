package com.shopeasy.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileManager class handles all file I/O operations for the ShopEasy application.
 * Uses Object serialization for storing and retrieving data from files.
 */
public class FileManager {
    private static final String DATA_DIRECTORY = "java/data/";
    private static final String PRODUCTS_FILE = "products.dat";
    private static final String CATEGORIES_FILE = "categories.dat";
    private static final String USERS_FILE = "users.dat";
    private static final String CARTS_FILE = "carts.dat";
    private static final String ORDERS_FILE = "orders.dat";
    
    /**
     * Initialization - Create data directory if it doesn't exist
     */
    static {
        File dataDir = new File(DATA_DIRECTORY);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
    
    /**
     * Save a list of objects to a file
     * @param filename The name of the file to save to
     * @param data The list of objects to save
     * @throws IOException If an I/O error occurs
     */
    public static void saveToFile(String filename, List<?> data) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_DIRECTORY + filename))) {
            oos.writeObject(data);
        }
    }
    
    /**
     * Load a list of objects from a file
     * @param filename The name of the file to load from
     * @return The list of objects, or an empty list if file doesn't exist
     */
    public static List<?> loadFromFile(String filename) {
        File file = new File(DATA_DIRECTORY + filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            return (List<?>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading file: " + filename + " - " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Save a single object to a file
     * @param filename The name of the file to save to
     * @param object The object to save
     * @throws IOException If an I/O error occurs
     */
    public static void saveSingleObject(String filename, Object object) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_DIRECTORY + filename))) {
            oos.writeObject(object);
        }
    }
    
    /**
     * Load a single object from a file
     * @param filename The name of the file to load from
     * @return The object, or null if file doesn't exist
     */
    public static Object loadSingleObject(String filename) {
        File file = new File(DATA_DIRECTORY + filename);
        if (!file.exists()) {
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading file: " + filename + " - " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get products file path
     */
    public static String getProductsFile() {
        return PRODUCTS_FILE;
    }
    
    /**
     * Get categories file path
     */
    public static String getCategoriesFile() {
        return CATEGORIES_FILE;
    }
    
    /**
     * Get users file path
     */
    public static String getUsersFile() {
        return USERS_FILE;
    }
    
    /**
     * Get carts file path
     */
    public static String getCartsFile() {
        return CARTS_FILE;
    }
    
    /**
     * Get orders file path
     */
    public static String getOrdersFile() {
        return ORDERS_FILE;
    }
    
    /**
     * Get full data directory path
     */
    public static String getDataDirectory() {
        return DATA_DIRECTORY;
    }
}
