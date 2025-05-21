package com.islington.model;

public class ProductModel {
    private int productId;
    private String productName;
    private String productCategory;
    private String productDescription;
    private double productPrice;
    private int productQuantity;
    private String image;
    public static final int MAX_QUANTITY = 100;

    public ProductModel() {}

    public ProductModel(int productId, String productName, String productCategory,
                      String productDescription, double productPrice, int productQuantity, String image) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productQuantity = Math.min(productQuantity, MAX_QUANTITY);
        this.image = image;
    }

    // Getters and setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductCategory() { return productCategory; }
    public void setProductCategory(String productCategory) { this.productCategory = productCategory; }

    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }

    public double getProductPrice() { return productPrice; }
    public void setProductPrice(double productPrice) { this.productPrice = productPrice; }

    public int getProductQuantity() { return productQuantity; }
    public void setProductQuantity(int productQuantity) { 
        this.productQuantity = Math.min(productQuantity, MAX_QUANTITY); 
    }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    @Override
    public String toString() {
        return "ProductModel{" +
               "productId=" + productId +
               ", productName='" + productName + '\'' +
               ", productCategory='" + productCategory + '\'' +
               ", productDescription='" + productDescription + '\'' +
               ", productPrice=" + productPrice +
               ", productQuantity=" + productQuantity +
               ", image='" + image + '\'' +
               '}';
    }
}