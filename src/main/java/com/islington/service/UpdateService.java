package com.islington.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.islington.config.DbConfig;
import com.islington.model.UserModel;

/**
 * Service class for updating user information in the database.
 */
public class UpdateService {
    private Connection dbConn;
    private boolean isConnectionError = false;

    /**
     * Constructor initializes the database connection.
     */
    public UpdateService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    /**
     * Updates user information in the database.
     *
     * @param user The UserModel object containing updated user data.
     * @return Boolean indicating the success of the update operation.
     */
    public Boolean updateUserInfo(UserModel user) {
        if (isConnectionError) {
            return null;
        }

        String updateSQL = "UPDATE user SET first_name = ?, last_name = ?, email = ?, "
                         + "gender = ?, username = ?, password = ? WHERE user_id = ?";

        try (PreparedStatement preparedStatement = dbConn.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getGender());
            preparedStatement.setString(5, user.getUsername());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setInt(7, user.getUserId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
