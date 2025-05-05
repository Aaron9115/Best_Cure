package com.islington.service;

import com.islington.config.DbConfig;
import com.islington.model.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    // Get all products
    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM product";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProductModel product = new ProductModel(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("product_category"),
                        rs.getString("product_description"),
                        rs.getDouble("product_price"),
                        rs.getInt("product_quantity")
                );
                products.add(product);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching products: " + e.getMessage());
            e.printStackTrace();
        }

        return products;
    }

    // Get product by ID
    public ProductModel getProductById(int id) {
        String query = "SELECT * FROM product WHERE product_id = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ProductModel(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("product_category"),
                        rs.getString("product_description"),
                        rs.getDouble("product_price"),
                        rs.getInt("product_quantity")
                );
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching product by ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // Add product
    public boolean addProduct(ProductModel product) {
        String query = "INSERT INTO product (product_name, product_category, product_description, product_price, product_quantity) "
                     + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductCategory());
            stmt.setString(3, product.getProductDescription());
            stmt.setDouble(4, product.getProductPrice());
            stmt.setInt(5, product.getProductQuantity());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error adding product: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Update product
    public boolean updateProduct(ProductModel product) {
        String query = "UPDATE product SET product_name=?, product_category=?, product_description=?, "
                     + "product_price=?, product_quantity=? WHERE product_id=?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductCategory());
            stmt.setString(3, product.getProductDescription());
            stmt.setDouble(4, product.getProductPrice());
            stmt.setInt(5, product.getProductQuantity());
            stmt.setInt(6, product.getProductId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating product: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Delete product
    public boolean deleteProduct(int productId) {
        String query = "DELETE FROM product WHERE product_id=?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, productId);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
