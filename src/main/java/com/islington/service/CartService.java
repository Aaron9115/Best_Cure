package com.islington.service;

import com.islington.config.DbConfig;
import com.islington.model.CartModel;
import com.islington.model.ProductModel;
import com.islington.model.UserModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartService {
    private static final Logger LOGGER = Logger.getLogger(CartService.class.getName());
    private Connection dbConn;

    public CartService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize database connection", e);
        }
    }

    public CartModel getCartByUserId(int userId) {
        CartModel cart = new CartModel();
        cart.setUser(new UserModel(userId));
        
        try {
            // Get cart header
            String cartQuery = "SELECT * FROM cart WHERE user_id = ?";
            try (PreparedStatement stmt = dbConn.prepareStatement(cartQuery)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    cart.setCartId(rs.getInt("cart_id"));
                    cart.setTotalQuantity(rs.getInt("total_quantity"));
                    cart.setTotalPrice(rs.getDouble("total_price"));
                }
            }
            
            // If no cart exists, create one
            if (cart.getCartId() == 0) {
                cart.setCartId(createNewCart(userId));
            }
            
            // Get cart items
            if (cart.getCartId() > 0) {
                String productsQuery = "SELECT p.*, p.product_quantity FROM product p " +
                                     "JOIN cart_prod cp ON p.product_id = cp.product_id " +
                                     "WHERE cp.cart_id = ?";
                try (PreparedStatement stmt = dbConn.prepareStatement(productsQuery)) {
                    stmt.setInt(1, cart.getCartId());
                    ResultSet rs = stmt.executeQuery();
                    
                    List<ProductModel> products = new ArrayList<>();
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
                    cart.setProducts(products);
                    cart.calculateTotals(); // This will recalculate totals
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting cart for user: " + userId, e);
        }
        return cart;
    }

    private int createNewCart(int userId) throws SQLException {
        String query = "INSERT INTO cart (user_id, total_quantity, total_price) VALUES (?, 0, 0)";
        try (PreparedStatement stmt = dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Failed to create new cart");
    }

    public boolean syncCartWithDatabase(CartModel cart) {
        if (cart.getCartId() <= 0) {
            LOGGER.warning("Invalid cart ID during sync: " + cart.getCartId());
            return false;
        }

        try {
            dbConn.setAutoCommit(false);
            
            // Debug log
            LOGGER.info("Syncing cart ID: " + cart.getCartId() + 
                       " with " + cart.getProducts().size() + " products");
            
            // Update cart totals
            String updateCart = "UPDATE cart SET total_quantity=?, total_price=? WHERE cart_id=?";
            try (PreparedStatement stmt = dbConn.prepareStatement(updateCart)) {
                stmt.setInt(1, cart.getTotalQuantity());
                stmt.setDouble(2, cart.getTotalPrice());
                stmt.setInt(3, cart.getCartId());
                int rows = stmt.executeUpdate();
                LOGGER.info("Updated cart header, rows affected: " + rows);
            }
            
            // Update bridge table
            updateBridgeTable(cart);
            
            dbConn.commit();
            LOGGER.info("Cart sync successful");
            return true;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Error during cart sync", e);
            try {
                dbConn.rollback();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Rollback failed", ex);
            }
            return false;
        } finally {
            try {
                dbConn.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Failed to reset auto-commit", e);
            }
        }
    }

    private void updateBridgeTable(CartModel cart) throws SQLException {
        // Clear existing items
        String deleteSql = "DELETE FROM cart_prod WHERE cart_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(deleteSql)) {
            stmt.setInt(1, cart.getCartId());
            stmt.executeUpdate();
        }
        
        // Add current items
        String insertSql = "INSERT INTO cart_prod (cart_id, product_id) VALUES (?, ?)";
        try (PreparedStatement stmt = dbConn.prepareStatement(insertSql)) {
            for (ProductModel product : cart.getProducts()) {
                stmt.setInt(1, cart.getCartId());
                stmt.setInt(2, product.getProductId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
}