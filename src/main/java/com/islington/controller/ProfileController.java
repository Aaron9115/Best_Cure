package com.islington.controller;

import com.islington.model.UserModel;
import com.islington.service.ProfileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet("/profile")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5MB max file size
public class ProfileController extends HttpServlet {

    private ProfileService profileService = new ProfileService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");

        System.out.println("User ID in session (doGet): " + userId);

        if (userId == null) {
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        UserModel user = profileService.getUserById(userId);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");

        if (userId == null) {
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        String firstName = getFormFieldValue(request.getPart("firstName"));
        String lastName = getFormFieldValue(request.getPart("lastName"));
        String username = getFormFieldValue(request.getPart("username"));
        String phoneNumber = getFormFieldValue(request.getPart("phone"));
        String gender = getFormFieldValue(request.getPart("gender"));

        Part profilePicPart = request.getPart("profilePicture");
        String profilePicture = null;
        if (profilePicPart != null && profilePicPart.getSize() > 0) {
            profilePicture = saveProfilePicture(profilePicPart);  // Save the uploaded profile picture
        }

        // Create user object
        UserModel user = new UserModel();
        user.setUserId(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPhoneNumber(phoneNumber);
        user.setGender(gender);
        user.setProfilePicture(profilePicture);  // Set profile picture URL

        // Update profile
        boolean success = profileService.updateUserProfile(user);

        if (success) {
            request.setAttribute("message", "Profile updated successfully.");
        } else {
            request.setAttribute("error", "Profile update failed.");
        }

        // Fetch updated user details
        UserModel updatedUser = profileService.getUserById(userId);
        request.setAttribute("user", updatedUser);
        request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
    }


    // Helper method to extract plain text from multipart form field
    private String getFormFieldValue(Part part) throws IOException {
        if (part == null) return null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"))) {
            StringBuilder value = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                value.append(line);
            }
            return value.toString().trim();
        }
    }

    // Helper method to save uploaded file to disk (or return a path string)
    private String saveProfilePicture(Part filePart) throws IOException {
        String fileName = getFileName(filePart);
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }

        // You can change this path to your project’s upload directory
        String uploadDir = getServletContext().getRealPath("/uploads");

        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) uploadFolder.mkdirs();

        File file = new File(uploadFolder, fileName);
        try (InputStream input = filePart.getInputStream(); FileOutputStream output = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        }

        // Return relative path or full path as needed
        return "uploads/" + fileName;
    }

    // Extracts original filename from file part
    private String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
