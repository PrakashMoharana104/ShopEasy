package com.shopeasy.models;

import java.io.Serializable;

/**
 * Category class represents a product category.
 * Implements Serializable for file I/O operations.
 */
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int categoryId;
    private String name;
    private String description;
    private String icon;
    
    // Constructor
    public Category(int categoryId, String name, String description, String icon) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }
    
    // Getters and Setters
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
