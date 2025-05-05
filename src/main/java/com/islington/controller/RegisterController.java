package com.islington.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.islington.model.UserModel;
import com.islington.service.RegisterService;
import com.islington.util.ValidationUtil;
import com.islington.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final RegisterService registerService = new RegisterService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String validationMessage = validateRegistrationForm(req);
            if (validationMessage != null) {
                forwardWithError(req, resp, validationMessage);
                return;
            }

            UserModel userModel = extractUserModel(req);
            userModel.setPassword(PasswordUtil.hashPassword(userModel.getPassword()));  // Hash the password

            Boolean isAdded = registerService.addUser(userModel);

            if (isAdded == null) {
                forwardWithError(req, resp, "Our server is under maintenance. Please try again later.");
            } else if (isAdded) {
                HttpSession session = req.getSession();
                session.setAttribute("username", userModel.getUsername());

                Cookie cookie = new Cookie("username", URLEncoder.encode(userModel.getUsername(), StandardCharsets.UTF_8));
                cookie.setMaxAge(60 * 60 * 24); // 1 day
                resp.addCookie(cookie);

                forwardWithSuccess(req, resp, "Your account is successfully created!", "/WEB-INF/pages/login.jsp");
            } else {
                forwardWithError(req, resp, "Could not register your account. Please try again later.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            forwardWithError(req, resp, "An unexpected error occurred. Please try again later.");
        }
    }

    private String validateRegistrationForm(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String phone = req.getParameter("phoneNumber");
        String password = req.getParameter("password");
        String retypePassword = req.getParameter("retypePassword");
        String gender = req.getParameter("gender");

        if (ValidationUtil.isNullOrEmpty(firstName)) return "First name is required.";
        if (ValidationUtil.isNullOrEmpty(lastName)) return "Last name is required.";
        if (ValidationUtil.isNullOrEmpty(username)) return "Username is required.";
        if (ValidationUtil.isNullOrEmpty(email)) return "Email is required.";
        if (ValidationUtil.isNullOrEmpty(phone)) return "Phone number is required.";
        if (ValidationUtil.isNullOrEmpty(password)) return "Password is required.";
        if (ValidationUtil.isNullOrEmpty(retypePassword)) return "Please retype the password.";
        if (ValidationUtil.isNullOrEmpty(gender)) return "Gender is required.";

        if (!ValidationUtil.isAlphanumericStartingWithLetter(username)) return "Username must start with a letter and contain only letters and numbers.";
        if (!ValidationUtil.isValidEmail(email)) return "Invalid email format.";
        if (!ValidationUtil.isValidPhoneNumber(phone)) return "Phone number must be 10 digits and start with 98.";
        if (!ValidationUtil.isValidPassword(password)) return "Password must be at least 8 characters long, with 1 uppercase letter, 1 number, and 1 symbol.";
        if (!ValidationUtil.doPasswordsMatch(password, retypePassword)) return "Passwords do not match.";
        if (!ValidationUtil.isValidGender(gender)) return "Gender must be 'male' or 'female'.";

        return null;
    }

    /**
     * Extracts user data from request and returns a UserModel with DB-aligned column field names.
     */
    private UserModel extractUserModel(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");           // maps to first_name
        String lastName = req.getParameter("lastName");             // maps to last_name
        String username = req.getParameter("username");             // maps to username
        String email = req.getParameter("email");                   // maps to email
        String gender = req.getParameter("gender");                 // maps to gender
        String phone = req.getParameter("phoneNumber").replaceAll("[^\\d]", ""); // maps to phone_number
        String password = req.getParameter("password");             // maps to password (hashed later)

        UserModel userModel = new UserModel(0, firstName, lastName, username, email, gender, phone, password);

        // Set the default role
        userModel.setRole("user");  // Set role to 'user'

        return userModel;
    }

    private void forwardWithSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String page)
            throws ServletException, IOException {
        req.setAttribute("success", message);
        req.getRequestDispatcher(page).forward(req, resp);
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String errorMessage)
            throws ServletException, IOException {
        req.setAttribute("error", errorMessage);
        req.setAttribute("firstName", req.getParameter("firstName"));
        req.setAttribute("lastName", req.getParameter("lastName"));
        req.setAttribute("username", req.getParameter("username"));
        req.setAttribute("email", req.getParameter("email"));
        req.setAttribute("phoneNumber", req.getParameter("phoneNumber"));
        req.setAttribute("gender", req.getParameter("gender"));
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }
}
