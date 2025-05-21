package com.islington.controller.admin;

import com.islington.model.UserModel;
import com.islington.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/user")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserService();
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<UserModel> userList = userService.getAllUsers();
            request.setAttribute("userList", userList);
            request.getRequestDispatcher("/WEB-INF/pages/admin/manageUser.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load users");
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }
}