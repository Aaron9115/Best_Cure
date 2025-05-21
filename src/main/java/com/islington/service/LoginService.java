package com.islington.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.islington.config.DbConfig;
import com.islington.model.UserModel;

public class LoginService {
    private Connection dbConn;

    public LoginService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed", ex);
        }
    }

    public Map<String, Object> loginUser(UserModel userModel) {
        String query = "SELECT user_id, user_email, user_password, user_role FROM user WHERE user_email = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, userModel.getEmail());
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String dbEmail = result.getString("user_email");
                String dbPassword = result.getString("user_password");
                
                Map<String, Object> loginResult = new HashMap<>();
                loginResult.put("status", dbEmail.equals(userModel.getEmail())); // Add password check if needed
                loginResult.put("user_id", result.getInt("user_id"));
                loginResult.put("role", result.getString("user_role"));
                
                return loginResult;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}