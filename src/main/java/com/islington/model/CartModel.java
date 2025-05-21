package com.islington.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartModel {
    private int cartId;
    private UserModel user;
    private int totalQuantity;
    private double totalPrice;
    private List<ProductModel> products;

    public CartModel() {
        this.products = new ArrayList<>();
        this.totalQuantity = 0;
        this.totalPrice = 0.0;
    }

    public void addProduct(ProductModel product, int quantity) {
        if (product == null || quantity <= 0) return;
        
        // Check if product already exists in cart
        for (ProductModel existing : products) {
            if (existing.getProductId() == product.getProductId()) {
                existing.setProductQuantity(existing.getProductQuantity() + quantity);
                calculateTotals();
                return;
            }
        }
        
        // Add new product with quantity
        ProductModel newProduct = new ProductModel(
            product.getProductId(),
            product.getProductName(),
            product.getProductCategory(),
            product.getProductDescription(),
            product.getProductPrice(),
            quantity,
            product.getImage()
        );
        products.add(newProduct);
        calculateTotals();
    }

    public void updateQuantity(int productId, int quantity) {
        products.stream()
                .filter(p -> p.getProductId() == productId)
                .findFirst()
                .ifPresent(p -> {
                    p.setProductQuantity(quantity);
                    calculateTotals();
                });
    }

    public void removeProduct(int productId) {
        products.removeIf(p -> p.getProductId() == productId);
        calculateTotals();
    }

    public void clearCart() {
        products.clear();
        totalQuantity = 0;
        totalPrice = 0.0;
    }

    public void calculateTotals() {
        this.totalQuantity = products.stream()
                .mapToInt(ProductModel::getProductQuantity)
                .sum();
        this.totalPrice = products.stream()
                .filter(Objects::nonNull)
                .mapToDouble(p -> p.getProductPrice() * p.getProductQuantity())
                .sum();
    }

    // Getters and setters
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ProductModel> getProducts() {
        return new ArrayList<>(products);
    }

    public void setProducts(List<ProductModel> products) {
        this.products = new ArrayList<>(products);
        calculateTotals();
    }
}