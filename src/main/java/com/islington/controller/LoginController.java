package com.islington.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.islington.model.UserModel;
import com.islington.service.LoginService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final LoginService loginService;

    public LoginController() {
        this.loginService = new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String email = req.getParameter("user_email");
        String password = req.getParameter("user_password");

        System.out.println("Login attempt for email: " + email);
        
        UserModel userModel = new UserModel(email, password);
        Map<String, Object> loginResult = loginService.loginUser(userModel);
        
        if (loginResult != null && (Boolean)loginResult.get("status")) {
            HttpSession session = req.getSession(true);
            
            // Set all session attributes needed for profile access
            session.setAttribute("user_id", loginResult.get("user_id"));
            session.setAttribute("user_email", email);
            session.setAttribute("user_role", loginResult.get("role"));
            
            System.out.println("Login successful - Session created:");
            System.out.println("Session ID: " + session.getId());
            System.out.println("User ID: " + session.getAttribute("user_id"));
            System.out.println("Email: " + session.getAttribute("user_email"));
            System.out.println("Role: " + session.getAttribute("user_role"));

            if ("admin".equalsIgnoreCase((String)loginResult.get("role"))) {
                System.out.println("Redirecting admin to dashboard");
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                System.out.println("Redirecting user to home");
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } else {
            System.out.println("Login failed for email: " + email);
            req.setAttribute("error", "Invalid email or password");
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
        }
    }
}