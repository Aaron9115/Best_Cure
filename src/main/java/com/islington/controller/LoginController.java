package com.islington.controller;

import com.islington.model.UserModel;
import com.islington.service.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private LoginService loginService;

    @Override
    public void init() throws ServletException {
        loginService = new LoginService(); // Create the service instance once during servlet initialization
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Forward to the login page
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Validate input
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            req.setAttribute("errorMessage", "Email and password are required.");
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
            return;
        }

        // Authenticate user via login service
        UserModel user = loginService.loginUser(email, password);

        if (user != null) {
            // Create a session and store user data
            HttpSession session = req.getSession();
            session.setAttribute("user_id", user.getUserId());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("role", user.getRole());

            // Redirect based on user role
            if ("admin".equals(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/dashboard"); // Admin dashboard
            } else {
                resp.sendRedirect(req.getContextPath() + "/home"); // User home page
            }
        } else {
            // Login failed: Invalid credentials
            req.setAttribute("errorMessage", "Invalid email or password.");
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
        }
    }
}
