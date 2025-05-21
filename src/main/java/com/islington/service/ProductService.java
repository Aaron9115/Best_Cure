package com.islington.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.islington.config.DbConfig;
import com.islington.model.ProductModel;

public class ProductService {
    private Connection dbConn;
    private boolean isConnectionError = false;

    public ProductService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    public List<ProductModel> getAllProducts() {
        if (isConnectionError) {
            System.out.println("Connection Error!");
            return null;
        }

        String query = "SELECT product_id, product_name, product_category, product_description, " +
                      "product_price, product_quantity, image FROM product ORDER BY product_id DESC";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            List<ProductModel> productList = new ArrayList<>();

            while (result.next()) {
                productList.add(new ProductModel(
                    result.getInt("product_id"),
                    result.getString("product_name"),
                    result.getString("product_category"),
                    result.getString("product_description"),
                    result.getDouble("product_price"),
                    result.getInt("product_quantity"),
                    result.getString("image")
                ));
            }
            return productList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ProductModel getProductById(int productId) {
        if (isConnectionError) {
            System.out.println("Connection Error!");
            return null;
        }

        String query = "SELECT product_id, product_name, product_category, product_description, " +
                      "product_price, product_quantity, image FROM product WHERE product_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return new ProductModel(
                    result.getInt("product_id"),
                    result.getString("product_name"),
                    result.getString("product_category"),
                    result.getString("product_description"),
                    result.getDouble("product_price"),
                    result.getInt("product_quantity"),
                    result.getString("image")
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addProduct(ProductModel product) {
        if (isConnectionError) {
            return false;
        }

        String query = "INSERT INTO product (product_name, product_category, product_description, " +
                      "product_price, product_quantity, image) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductCategory());
            stmt.setString(3, product.getProductDescription());
            stmt.setDouble(4, product.getProductPrice());
            stmt.setInt(5, product.getProductQuantity());
            stmt.setString(6, product.getImage());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProduct(ProductModel product) {
        if (isConnectionError) {
            return false;
        }

        String query = "UPDATE product SET product_name = ?, product_category = ?, product_description = ?, " +
                      "product_price = ?, product_quantity = ?, image = ? WHERE product_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductCategory());
            stmt.setString(3, product.getProductDescription());
            stmt.setDouble(4, product.getProductPrice());
            stmt.setInt(5, product.getProductQuantity());
            stmt.setString(6, product.getImage());
            stmt.setInt(7, product.getProductId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(int productId) throws SQLException {
        try {
            dbConn.setAutoCommit(false);

            // Delete from cart_prod first
            String deleteCartItems = "DELETE FROM cart_prod WHERE product_id = ?";
            try (PreparedStatement stmt = dbConn.prepareStatement(deleteCartItems)) {
                stmt.setInt(1, productId);
                stmt.executeUpdate();
            }

            // Delete from order_prod next
            String deleteOrderItems = "DELETE FROM order_prod WHERE product_id = ?";
            try (PreparedStatement stmt = dbConn.prepareStatement(deleteOrderItems)) {
                stmt.setInt(1, productId);
                stmt.executeUpdate();
            }

            // Now delete the product itself
            String deleteProduct = "DELETE FROM product WHERE product_id = ?";
            int affectedRows;
            try (PreparedStatement stmt = dbConn.prepareStatement(deleteProduct)) {
                stmt.setInt(1, productId);
                affectedRows = stmt.executeUpdate();
            }

            dbConn.commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            dbConn.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            dbConn.setAutoCommit(true);
        }
    }

    public List<ProductModel> getProductsByCategory(String category) {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE product_category = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

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
            System.err.println("Error fetching products by category: " + e.getMessage());
            e.printStackTrace();
        }

        return products;
    }
    
    public List<ProductModel> searchProducts(String query) {
        List<ProductModel> results = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE " +
                     "product_name LIKE ? OR " +
                     "product_category LIKE ? OR " +
                     "product_description LIKE ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchTerm = "%" + query + "%";
            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);
            stmt.setString(3, searchTerm);

            try (ResultSet rs = stmt.executeQuery()) {
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
                    results.add(product);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
