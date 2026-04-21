package com.shopeasy.services;

import com.shopeasy.models.User;
import com.shopeasy.utils.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * UserService handles all user-related operations.
 * Uses FileManager for persistent storage of users.
 */
public class UserService {
    
    /**
     * Get all users from storage
     * @return List of all users
     */
    @SuppressWarnings("unchecked")
    public static List<User> getAllUsers() {
        List<?> allData = FileManager.loadFromFile(FileManager.getUsersFile());
        return new ArrayList<>((List<User>) (List<?>) allData);
    }
    
    /**
     * Register a new user
     * @param user The user to register
     * @throws IOException If file operation fails
     */
    public static void registerUser(User user) throws IOException {
        List<User> users = getAllUsers();
        
        // Check if user already exists
        for (User existingUser : users) {
            if (existingUser.getEmail().equals(user.getEmail())) {
                throw new IOException("User with this email already exists");
            }
        }
        
        users.add(user);
        FileManager.saveToFile(FileManager.getUsersFile(), users);
    }
    
    /**
     * Login a user
     * @param email The user's email
     * @param password The user's password
     * @return The user if login successful, null otherwise
     */
    public static User loginUser(String email, String password) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Get user by ID
     * @param userId The user ID
     * @return The user if found, null otherwise
     */
    public static User getUserById(String userId) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Get user by email
     * @param email The user's email
     * @return The user if found, null otherwise
     */
    public static User getUserByEmail(String email) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Update user information
     * @param user The updated user
     * @throws IOException If file operation fails
     */
    public static void updateUser(User user) throws IOException {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(user.getUserId())) {
                users.set(i, user);
                FileManager.saveToFile(FileManager.getUsersFile(), users);
                return;
            }
        }
    }
    
    /**
     * Delete a user
     * @param userId The user ID to delete
     * @throws IOException If file operation fails
     */
    public static void deleteUser(String userId) throws IOException {
        List<User> users = getAllUsers();
        users.removeIf(u -> u.getUserId().equals(userId));
        FileManager.saveToFile(FileManager.getUsersFile(), users);
    }
    
    /**
     * Get total user count
     * @return Number of users
     */
    public static int getTotalUserCount() {
        return getAllUsers().size();
    }
}
