package com.islington.service;

import com.islington.config.DbConfig;
import com.islington.model.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardService {

    // Get total number of users
    public int getTotalUsers() {
        String query = "SELECT COUNT(*) FROM user";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching total users: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    // Get total number of products
    public int getTotalProducts() {
        String query = "SELECT COUNT(*) FROM product";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching total products: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    // Get recent products (e.g., latest 5)
    public List<ProductModel> getRecentProducts() {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM product ORDER BY product_id DESC LIMIT 5";

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
                        rs.getInt("product_quantity"),
                        rs.getString("image")
                );
                products.add(product);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching recent products: " + e.getMessage());
            e.printStackTrace();
        }

        return products;
    }
    
}
