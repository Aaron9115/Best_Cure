package com.islington.service;

import com.islington.model.UserModel;
import java.sql.*;

public class ProfileService {

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/pharma";
        String dbUser = "root";
        String dbPassword = ""; // Use your actual DB password
        return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
    }

    public UserModel getUserById(int userId) {
        String query = "SELECT * FROM user WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new UserModel(
                    rs.getInt("user_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("user_name"),
                    rs.getString("user_email"),
                    rs.getString("gender"),
                    rs.getString("phone_number"),
                    rs.getString("user_password"),
                    rs.getString("user_role"),
                    rs.getString("profile_picture")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUserProfile(UserModel user) {
        String sql = "UPDATE user SET user_name = ?, first_name = ?, last_name = ?, phone_number = ?, gender = ?, profile_picture = ? WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getProfilePicture());
            stmt.setInt(7, user.getUserId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error updating profile: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    }
