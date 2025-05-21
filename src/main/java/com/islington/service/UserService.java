package com.islington.service;
import com.islington.config.DbConfig;
import com.islington.model.UserModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private Connection dbConn;

    public UserService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize database connection", e);
        }
    }

    // Create a new user
    public boolean createUser(UserModel user) {
        String sql = "INSERT INTO user (first_name, last_name, user_name, user_email, gender, phone_number, user_password, user_role, profile_picture) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getPhoneNumber());
            stmt.setString(7, user.getPassword());
            stmt.setString(8, user.getRole());
            stmt.setString(9, user.getProfilePicture());

            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating user", e);
        }
        return false;
    }

    // Get user by ID
    public UserModel getUserById(int userId) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting user by ID", e);
        }
        return null;
    }

    // Get user by email
    public UserModel getUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE user_email = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting user by email", e);
        }
        return null;
    }

    // Get user by username
    public UserModel getUserByUsername(String username) {
        String sql = "SELECT * FROM user WHERE user_name = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting user by username", e);
        }
        return null;
    }

    // Update user information
    public boolean updateUser(UserModel user) {
        String sql = "UPDATE user SET first_name = ?, last_name = ?, user_name = ?, user_email = ?, " +
                     "gender = ?, phone_number = ?, profile_picture = ? WHERE user_id = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getPhoneNumber());
            stmt.setString(7, user.getProfilePicture());
            stmt.setInt(8, user.getUserId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating user", e);
        }
        return false;
    }

    // Update user password
    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE user SET user_password = ? WHERE user_id = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating user_password", e);
        }
        return false;
    }

    // Delete user
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM user WHERE user_id = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting user", e);
        }
        return false;
    }

    // Authenticate user
    public UserModel authenticate(String email, String password) {
        String sql = "SELECT * FROM user WHERE user_email = ? AND user_password = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error authenticating user", e);
        }
        return null;
    }

    // Get all user (for admin)
    public List<UserModel> getAllUsers() {
        List<UserModel> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        
        try (Statement stmt = dbConn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all user", e);
        }
        return users;
    }

    // Helper method to extract user from ResultSet
    private UserModel extractUserFromResultSet(ResultSet rs) throws SQLException {
        UserModel user = new UserModel();
        user.setUserId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setUsername(rs.getString("user_name"));
        user.setEmail(rs.getString("user_email"));
        user.setGender(rs.getString("gender"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setPassword(rs.getString("user_password"));
        user.setRole(rs.getString("user_role"));
        user.setProfilePicture(rs.getString("profile_picture"));
        return user;
    }

    // Check if email exists
    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM user WHERE user_email = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking email existence", e);
        }
        return false;
    }

    // Check if username exists
    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM user WHERE user_name = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking username existence", e);
        }
        return false;
    }
}