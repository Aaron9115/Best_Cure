package com.islington.model;

import java.util.Date;
import java.util.List;

public class OrderModel {
    private int orderId;
    private int userId;
    private List<ProductModel> products;
    private double totalAmount;
    private Date orderDate;
    private String status; // "PROCESSING", "SHIPPED", "COMPLETED"
    private String shippingAddress;
    private String paymentMethod;

    // Constructors
    public OrderModel() {}

    public OrderModel(int orderId, int userId, List<ProductModel> products, 
                     double totalAmount, Date orderDate, String status, 
                     String shippingAddress, String paymentMethod) {
        this.orderId = orderId;
        this.userId = userId;
        this.products = products;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public List<ProductModel> getProducts() { return products; }
    public void setProducts(List<ProductModel> products) { this.products = products; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}