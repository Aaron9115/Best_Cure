package com.islington.service;

import com.islington.config.DbConfig;
import com.islington.model.OrderModel;
import com.islington.model.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderService {

    public boolean createOrder(OrderModel order) {
        String orderSql = "INSERT INTO `order` (user_id, total_price, order_date, status) VALUES (?, ?, ?, ?)";
        String orderProdSql = "INSERT INTO order_prod (order_id, product_id) VALUES (?, ?)";

        try (Connection conn = DbConfig.getDbConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, order.getUserId());
                orderStmt.setDouble(2, order.getTotalAmount());
                orderStmt.setLong(3, System.currentTimeMillis() / 1000);
                orderStmt.setString(4, "PROCESSING");
                orderStmt.executeUpdate();

                try (ResultSet rs = orderStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int orderId = rs.getInt(1);
                        try (PreparedStatement itemsStmt = conn.prepareStatement(orderProdSql)) {
                            for (ProductModel product : order.getProducts()) {
                                itemsStmt.setInt(1, orderId);
                                itemsStmt.setInt(2, product.getProductId());
                                itemsStmt.addBatch();
                            }
                            itemsStmt.executeBatch();
                        }
                    }
                }
            }

            conn.commit();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<OrderModel> getOrdersByUserId(int userId) {
        List<OrderModel> orders = new ArrayList<>();
        String orderSql = "SELECT * FROM `order` WHERE user_id = ? ORDER BY order_date DESC";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(orderSql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderModel order = new OrderModel();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_price"));
                order.setOrderDate(new Date(rs.getLong("order_date") * 1000L));
                order.setStatus(rs.getString("status"));

                order.setProducts(getOrderProducts(order.getOrderId(), conn));
                orders.add(order);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private List<ProductModel> getOrderProducts(int orderId, Connection conn) throws SQLException {
        List<ProductModel> products = new ArrayList<>();
        String sql = "SELECT p.* FROM order_prod op JOIN product p ON op.product_id = p.product_id WHERE op.order_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProductModel product = new ProductModel(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("product_category"),
                    rs.getString("product_description"),
                    rs.getDouble("product_price"),
                    1,
                    rs.getString("image")
                );
                products.add(product);
            }
        }
        return products;
    }

    public List<OrderModel> getRecentOrders(int limit) {
        List<OrderModel> orders = new ArrayList<>();
        String sql = "SELECT * FROM `order` ORDER BY order_date DESC LIMIT ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderModel order = new OrderModel();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_price"));
                order.setOrderDate(new Date(rs.getLong("order_date") * 1000L));
                order.setStatus(rs.getString("status"));
                orders.add(order);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE `order` SET status = ? WHERE order_id = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTotalOrders() {
        String sql = "SELECT COUNT(*) AS total FROM `order`";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<OrderModel> getAllOrders() {
        List<OrderModel> orders = new ArrayList<>();
        String sql = "SELECT * FROM `order` ORDER BY order_date DESC";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderModel order = new OrderModel();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_price"));
                order.setOrderDate(new Date(rs.getLong("order_date") * 1000L));
                order.setStatus(rs.getString("status"));
                orders.add(order);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return orders;
    }



public List<OrderModel> getOrdersByStatus(String status) {
    List<OrderModel> orders = new ArrayList<>();
    String query = "SELECT * FROM `order` WHERE status = ?";
    try (Connection conn = DbConfig.getDbConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, status);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            orders.add(mapOrderFromResultSet(rs));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return orders;
}



private OrderModel mapOrderFromResultSet(ResultSet rs) throws SQLException {
    OrderModel order = new OrderModel();
    order.setOrderId(rs.getInt("order_id"));
    order.setUserId(rs.getInt("user_id"));
    order.setTotalAmount(rs.getDouble("total_price"));
    order.setOrderDate(new Date(rs.getLong("order_date") * 1000L));
    order.setStatus(rs.getString("status"));
    return order;
}
}