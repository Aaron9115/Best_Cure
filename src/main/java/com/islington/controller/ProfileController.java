package com.islington.controller;

import com.islington.model.UserModel;
import com.islington.service.ProfileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/profile")
public class ProfileController extends HttpServlet {
    private ProfileService profileService = new ProfileService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println(" Profile Access Start");
        
        HttpSession session = request.getSession(false);
        
        // 1. Session Check
        if (session == null) {
            System.out.println(" No session exists - redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 2. Print ALL session attributes for debugging
        System.out.println(" Session ID: " + session.getId());
        System.out.println(" Session Attributes:");
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            System.out.println("  " + name + " = " + session.getAttribute(name));
        }

        // 3. User ID Check (multiple possible attribute names)
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            System.out.println(" user_id not found, checking alternative names...");
            Object idObj = session.getAttribute("id");
            if (idObj instanceof Integer) {
                userId = (Integer) idObj;
                System.out.println(" Found user ID in 'id' attribute: " + userId);
            } else {
                System.out.println(" No user ID found in any session attribute");
            }
        } else {
            System.out.println(" Found user_id in session: " + userId);
        }

        if (userId == null) {
            System.out.println(" No valid user ID - redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 4. Database Fetch
        System.out.println(" Attempting to fetch user from database...");
        try {
            UserModel user = profileService.getUserById(userId);
            if (user == null) {
                System.out.println(" User not found in database for ID: " + userId);
                System.out.println(" Invalidating session...");
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            System.out.println(" User found: " + user.getEmail());
            
            // 5. Forward to Profile Page
            request.setAttribute("user", user);
            System.out.println(" Forwarding to profile.jsp");
            request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.out.println(" Error fetching profile: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error loading profile data");
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
        
        System.out.println("Profile Access End ");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Profile Update Start");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Integer userId = (Integer) session.getAttribute("user_id");
        System.out.println("Updating profile for user ID: " + userId);

        try {
            // 1. First get the existing user to ensure we have all required fields
            UserModel existingUser = profileService.getUserById(userId);
            if (existingUser == null) {
                throw new Exception("User not found in database");
            }

            // 2. Extract and validate updated form values
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String phoneNumber = request.getParameter("phoneNumber");
            String gender = request.getParameter("gender");

            // 3. Use existing username if not provided in form
            String username = request.getParameter("user_name");
            if (username == null || username.isEmpty()) {
                username = existingUser.getUsername();
                System.out.println("Using existing username: " + username);
            }

            // 4. Build updated user object
            UserModel updatedUser = new UserModel();
            updatedUser.setUserId(userId);
            updatedUser.setFirstName(firstName != null ? firstName : existingUser.getFirstName());
            updatedUser.setLastName(lastName != null ? lastName : existingUser.getLastName());
            updatedUser.setUsername(username); 
            updatedUser.setPhoneNumber(phoneNumber != null ? phoneNumber : existingUser.getPhoneNumber());
            updatedUser.setGender(gender != null ? gender : existingUser.getGender());
            updatedUser.setProfilePicture(existingUser.getProfilePicture()); // Preserve existing

            // 5. Save updates
            boolean success = profileService.updateUserProfile(updatedUser);

            if (success) {
                request.setAttribute("message", "Profile updated successfully!");
            } else {
                request.setAttribute("error", "Failed to update profile. Please try again.");
            }

            // 6. Refetch and show updated user
            UserModel refreshedUser = profileService.getUserById(userId);
            request.setAttribute("user", refreshedUser);
            request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("Error updating profile: " + e.getMessage());
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }}