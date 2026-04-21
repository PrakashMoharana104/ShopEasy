# ShopEasy 🛒

Welcome to **ShopEasy**, a Java-based E-commerce application. This project was collaboratively developed as a group assignment for the **Java Programming** course by 1st-year, 2nd-semester students (Section F) at **Centurion University of Technology and Management (CUTM)**.

## 📖 Project Overview

ShopEasy provides a comprehensive backend implementation for a standard E-commerce platform. It handles essential tasks like product management, user authentication, shopping cart operations, order processing, and persistent data storage using a file management utility.

## 🗂️ Project Structure

Based on standard software development practices, the ShopEasy codebase is organized systematically into models, services, and utilities as shown below:

- **`models/`**: Contains the data structures and business entities.
  - `Cart.java`
  - `CartItem.java`
  - `Category.java`
  - `Order.java`
  - `Product.java`
  - `User.java`
- **`services/`**: Contains the core business logic and application operations.
  - `CartService.java`
  - `OrderService.java`
  - `ProductService.java`
  - `UserService.java`
- **`utils/`**: Contains utility classes and the main application entry points.
  - `FileManager.java`
  - `ShopEasyApp.java`
  - `ShopEasyServer.java`

## 🚀 Features

- **User Management**: Account registration and profile handling.
- **Product Catalog**: Seamlessly browse available products and categories.
- **Shopping Cart**: Add, manage, and update quantities of items.
- **Order Processing**: Integrated checkout mechanism for placing orders.
- **Data Persistence**: Uses `FileManager` for saving application state to files.

## 🛠️ Built With

* Language: **Java**
* Institute: **Centurion University of Technology and Management (CUTM)**
* Subject: **Java Programming**

## 👥 Meet the Team

Details regarding the development team and individual contributions can be found in the [TEAM.md](TEAM.md) file.
