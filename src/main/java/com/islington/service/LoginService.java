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
            ex.printStackTrace();  // Log error or use a logger
        }
    }

    /**
     * Authenticates a user based on the provided email and password.
     *
     * @param email    the user's email
     * @param password the user's password
     * @return a UserModel object if the user is found and the password is correct; otherwise, null
     */
    public UserModel loginUser(String email, String password) {
        if (dbConn == null) {
            System.out.println("Database connection error.");
            return null;
        }

        // SQL query to fetch user details based on email
        String query = "SELECT user_id, first_name, last_name, user_name, user_email, gender, phone_number, user_password, user_role "
                     + "FROM user WHERE user_email = ?";

        // Use try-with-resources to ensure the PreparedStatement and ResultSet are closed automatically
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, email);  // Set email parameter in query

            // Execute the query and get the result set
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    // User found, check the password
                    String dbPasswordHash = result.getString("user_password");

                    // Verify password using PasswordUtil (which compares the hashed passwords)
                    if (PasswordUtil.verifyPassword(password, dbPasswordHash)) {
                        // Password matches, create UserModel and set attributes
                        UserModel user = new UserModel();
                        user.setUserId(result.getInt("user_id"));
                        user.setFirstName(result.getString("first_name"));
                        user.setLastName(result.getString("last_name"));
                        user.setUsername(result.getString("user_name"));
                        user.setEmail(result.getString("user_email"));
                        user.setGender(result.getString("gender"));
                        user.setPhoneNumber(result.getString("phone_number"));
                        user.setRole(result.getString("user_role"));  // Set user role (admin, user, etc.)
                        return user;  // Return the populated UserModel object
                    } else {
                        System.out.println("Password mismatch for email: " + email);
                    }
                } else {
                    System.out.println("No user found with email: " + email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If no matching user or incorrect password, return null
        return null;
    }
}
