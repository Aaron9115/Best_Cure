package com.islington.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.islington.config.DbConfig;
import com.islington.model.UserModel;
import com.islington.util.PasswordUtil;

public class LoginService {

    private Connection dbConn;

    public LoginService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();  // Use proper logging in production
        }
    }

    /**
     * Authenticates a user using email and password.
     *
     * @param email    the user's email
     * @param password the user's raw password
     * @return UserModel if authenticated, null otherwise
     */
    public UserModel loginUser(String email, String password) {
        if (dbConn == null) {
            System.out.println("Database connection not initialized.");
            return null;
        }

        String query = "SELECT user_id, first_name, last_name, user_name, user_email, gender, phone_number, user_password, user_role "
                     + "FROM user WHERE user_email = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, email);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    String dbPasswordHash = result.getString("user_password");

                    if (PasswordUtil.verifyPassword(password, dbPasswordHash)) {
                        // Valid password, create and return UserModel
                        UserModel user = new UserModel();
                        user.setUserId(result.getInt("user_id"));
                        user.setFirstName(result.getString("first_name"));
                        user.setLastName(result.getString("last_name"));
                        user.setUsername(result.getString("user_name"));
                        user.setEmail(result.getString("user_email"));
                        user.setGender(result.getString("gender"));
                        user.setPhoneNumber(result.getString("phone_number"));
                        user.setRole(result.getString("user_role"));  // Role: "admin" or "user"
                        return user;
                    } else {
                        System.out.println("Incorrect password for: " + email);
                    }
                } else {
                    System.out.println("User not found with email: " + email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Replace with proper logging in production
        }

        return null;  // Authentication failed
    }
}
