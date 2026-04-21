package com.shopeasy;

import com.shopeasy.models.*;
import com.shopeasy.services.*;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ShopEasyServer {
    private static final int PORT = 8080;
    private static HttpServer server;

    public static void main(String[] args) {
        try {
            if (ProductService.getAllProducts().isEmpty()) {
                ProductService.initializeDefaultProducts();
                System.out.println("✓ Default products initialized");
            }

            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            
            server.createContext("/api/products", new ProductHandler());
            server.createContext("/api/cart", new CartHandler());
            server.createContext("/api/orders", new OrderHandler());
            server.createContext("/api/users", new UserHandler());
            server.createContext("/api/search", new SearchHandler());

            server.setExecutor(null);
            server.start();

            System.out.println("==========================================");
            System.out.println("  ShopEasy REST API Server Started");
            System.out.println("  URL: http://localhost:" + PORT);
            System.out.println("==========================================");
            System.out.println("\nPress Ctrl+C to stop");

        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
            System.exit(1);
        }
    }

    // ==================== PRODUCT HANDLER ====================
    static class ProductHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if (exchange.getRequestMethod().equals("OPTIONS")) {
                exchange.sendResponseHeaders(200, -1);
                return;
            }
            try {
                String query = exchange.getRequestURI().getQuery();
                if (query != null && query.startsWith("id=")) {
                    int productId = Integer.parseInt(query.substring(3));
                    Product product = ProductService.getProductById(productId);
                    if (product != null) {
                        sendJsonResponse(exchange, "[{\"id\":" + product.getProductId() + ",\"name\":\"" + product.getName() + "\"}]");
                    } else {
                        sendError(exchange, 404, "Not found");
                    }
                } else {
                    List<Product> products = ProductService.getAllProducts();
                    StringBuilder jsonArray = new StringBuilder("[");
                    for (int i = 0; i < products.size(); i++) {
                        Product p = products.get(i);
                        jsonArray.append("{\"id\":").append(p.getProductId())
                                 .append(",\"name\":\"").append(p.getName()).append("\"}");
                        if (i < products.size() - 1) jsonArray.append(",");
                    }
                    jsonArray.append("]");
                    sendJsonResponse(exchange, jsonArray.toString());
                }
            } catch (Exception e) {
                sendError(exchange, 500, e.getMessage());
            }
        }
    }

    // ==================== CART HANDLER ====================
    static class CartHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if (exchange.getRequestMethod().equals("OPTIONS")) {
                exchange.sendResponseHeaders(200, -1);
                return;
            }
            try {
                if (exchange.getRequestMethod().equals("GET")) {
                    String query = exchange.getRequestURI().getQuery();
                    if (query != null && query.startsWith("userId=")) {
                        sendJsonResponse(exchange, "{\"status\":\"ok\"}");
                    } else {
                        sendError(exchange, 400, "Missing userId");
                    }
                } else if (exchange.getRequestMethod().equals("POST")) {
                    sendJsonResponse(exchange, "{\"status\":\"success\"}");
                } else {
                    sendError(exchange, 405, "Method not allowed");
                }
            } catch (Exception e) {
                sendError(exchange, 500, e.getMessage());
            }
        }
    }

    // ==================== ORDERS HANDLER ====================
    static class OrderHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if (exchange.getRequestMethod().equals("OPTIONS")) {
                exchange.sendResponseHeaders(200, -1);
                return;
            }
            try {
                if (exchange.getRequestMethod().equals("GET")) {
                    sendJsonResponse(exchange, "[]");
                } else if (exchange.getRequestMethod().equals("POST")) {
                    sendJsonResponse(exchange, "{\"status\":\"success\"}");
                } else {
                    sendError(exchange, 405, "Method not allowed");
                }
            } catch (Exception e) {
                sendError(exchange, 500, e.getMessage());
            }
        }
    }

    // ==================== USER HANDLER ====================
    static class UserHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if (exchange.getRequestMethod().equals("OPTIONS")) {
                exchange.sendResponseHeaders(200, -1);
                return;
            }
            try {
                String path = exchange.getRequestURI().getPath();
                if (path.contains("login")) {
                    sendJsonResponse(exchange, "{\"status\":\"ok\"}");
                } else if (path.contains("register")) {
                    sendJsonResponse(exchange, "{\"status\":\"created\"}");
                } else {
                    sendError(exchange, 400, "Unknown endpoint");
                }
            } catch (Exception e) {
                sendError(exchange, 500, e.getMessage());
            }
        }
    }

    // ==================== SEARCH HANDLER ====================
    static class SearchHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if (exchange.getRequestMethod().equals("OPTIONS")) {
                exchange.sendResponseHeaders(200, -1);
                return;
            }
            try {
                String query = exchange.getRequestURI().getQuery();
                if (query != null && query.startsWith("q=")) {
                    String searchTerm = java.net.URLDecoder.decode(query.substring(2), StandardCharsets.UTF_8);
                    List<Product> results = ProductService.searchProducts(searchTerm);
                    StringBuilder jsonArray = new StringBuilder("[");
                    for (int i = 0; i < results.size(); i++) {
                        Product p = results.get(i);
                        jsonArray.append("{\"id\":").append(p.getProductId())
                                 .append(",\"name\":\"").append(p.getName()).append("\"}");
                        if (i < results.size() - 1) jsonArray.append(",");
                    }
                    jsonArray.append("]");
                    sendJsonResponse(exchange, jsonArray.toString());
                } else {
                    sendError(exchange, 400, "Missing q parameter");
                }
            } catch (Exception e) {
                sendError(exchange, 500, e.getMessage());
            }
        }
    }

    // ==================== UTILITY METHODS ====================
    private static void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
    }

    private static void sendJsonResponse(HttpExchange exchange, String json) throws IOException {
        byte[] response = json.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, response.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(response);
        }
    }

    private static void sendError(HttpExchange exchange, int code, String message) throws IOException {
        String json = "{\"error\":\"" + message + "\"}";
        byte[] response = json.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(code, response.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(response);
        }
    }
}

