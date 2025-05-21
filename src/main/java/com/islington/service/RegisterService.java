package com.islington.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.islington.config.DbConfig;
import com.islington.model.UserModel;
import com.islington.util.PasswordUtil;

public class RegisterService {
    public Boolean addUser(UserModel userModel) {
        String query = "INSERT INTO user (first_name, last_name, user_name, user_email, phone_number, user_password, gender, user_role) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection dbConn = DbConfig.getDbConnection();
             PreparedStatement stmt = dbConn.prepareStatement(query)) {

//            String hashedPassword = PasswordUtil.encrypt(userModel.getPassword());
//        	String password

            stmt.setString(1, userModel.getFirstName());
            stmt.setString(2, userModel.getLastName());
            stmt.setString(3, userModel.getUsername());
            stmt.setString(4, userModel.getEmail());
            stmt.setString(5, userModel.getPhoneNumber());
            stmt.setString(6, userModel.getPassword());
            stmt.setString(7, userModel.getGender());
            stmt.setString(8, userModel.getRole());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
}
