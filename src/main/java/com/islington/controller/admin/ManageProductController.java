package com.islington.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.islington.model.ProductModel;
import com.islington.service.ProductService;
import com.islington.util.ImageUtil;

@WebServlet("/admin/manageProduct")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class ManageProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService productService = new ProductService();
    private final ImageUtil imageUtil = new ImageUtil();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Entering ManageProductController.doGet()");
        
        HttpSession session = request.getSession(false);
        
        // Debug session info
        if (session == null) {
            System.out.println("[DEBUG] No session found - redirecting to admin login");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        
        System.out.println("[DEBUG] Session ID: " + session.getId());
        System.out.println("[DEBUG] Session attributes:");
        java.util.Enumeration<String> attrNames = session.getAttributeNames();
        while (attrNames.hasMoreElements()) {
            String name = attrNames.nextElement();
            System.out.println("  " + name + " = " + session.getAttribute(name));
        }

        // Check admin role
        String role = (String) session.getAttribute("user_role");
        System.out.println("[DEBUG] User role from session: " + role);
        
        if (!"admin".equalsIgnoreCase(role)) {
            System.out.println("[DEBUG] User doesn't have admin privileges (role: " + role + ") - redirecting to home");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // Debug request parameters
        System.out.println("[DEBUG] Request parameters:");
        java.util.Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            System.out.println("  " + name + " = " + request.getParameter(name));
        }

        // Check for success/error messages in session
        String success = (String) session.getAttribute("success");
        String error = (String) session.getAttribute("error");
        System.out.println("[DEBUG] Session messages - success: " + success + ", error: " + error);
        
        if (success != null) {
            request.setAttribute("success", success);
            session.removeAttribute("success");
        }
        if (error != null) {
            request.setAttribute("error", error);
            session.removeAttribute("error");
        }

        String action = request.getParameter("action");
        System.out.println("[DEBUG] Action parameter: " + action);

        if ("edit".equals(action)) {
            System.out.println("[DEBUG] Handling edit action");
            String productIdStr = request.getParameter("id");
            if (productIdStr != null && !productIdStr.trim().isEmpty()) {
                try {
                    int productId = Integer.parseInt(productIdStr);
                    System.out.println("[DEBUG] Editing product ID: " + productId);
                    ProductModel product = productService.getProductById(productId);
                    if (product != null) {
                        System.out.println("[DEBUG] Found product: " + product.getProductName());
                        request.setAttribute("product", product);
                    } else {
                        System.out.println("[DEBUG] Product not found with ID: " + productId);
                        request.setAttribute("error", "Product not found");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("[DEBUG] Invalid product ID format: " + productIdStr);
                    request.setAttribute("error", "Invalid product ID");
                }
            }
        } else if ("delete".equals(action)) {
            System.out.println("[DEBUG] Handling delete action");
            try {
                int productId = Integer.parseInt(request.getParameter("id"));
                System.out.println("[DEBUG] Deleting product ID: " + productId);
                boolean deleted = productService.deleteProduct(productId);
                if (deleted) {
                    System.out.println("[DEBUG] Product deleted successfully");
                    session.setAttribute("success", "Product deleted successfully");
                } else {
                    System.out.println("[DEBUG] Failed to delete product");
                    session.setAttribute("error", "Failed to delete product");
                }
            } catch (Exception e) {
                System.out.println("[DEBUG] Error deleting product: " + e.getMessage());
                session.setAttribute("error", "Error deleting product: " + e.getMessage());
            }
            response.sendRedirect(request.getContextPath() + "/admin/manageProduct");
            return;
        }

        System.out.println("[DEBUG] Fetching all products");
        List<ProductModel> productList = productService.getAllProducts();
        System.out.println("[DEBUG] Found " + (productList != null ? productList.size() : 0) + " products");
        
        request.setAttribute("productList", productList);
        System.out.println("[DEBUG] Forwarding to manageProduct.jsp");
        request.getRequestDispatcher("/WEB-INF/pages/admin/manageProduct.jsp").forward(request, response);
        
        System.out.println("==== Exiting ManageProductController.doGet() ====\n");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("\n==== Entering ManageProductController.doPost() ====");
        
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equalsIgnoreCase((String) session.getAttribute("user_role"))) {
            System.out.println("[DEBUG] Unauthorized access in doPost");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        try {
            String action = request.getParameter("action");
            System.out.println("[DEBUG] POST Action: " + action);
            
            int productId = 0;
            if (request.getParameter("productId") != null && !request.getParameter("productId").isEmpty()) {
                productId = Integer.parseInt(request.getParameter("productId"));
                System.out.println("[DEBUG] Product ID: " + productId);
            }

            // Handle file upload
            String imagePath = null;
            try {
                Part image = request.getPart("image");
                if (image != null && image.getSize() > 0) {
                    System.out.println("[DEBUG] Processing image upload");
                    String uploadPath = getServletContext().getRealPath("") + "/resources/products";
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        System.out.println("[DEBUG] Creating upload directory: " + uploadPath);
                        uploadDir.mkdirs();
                    }
                    
                    String fileName = System.currentTimeMillis() + "_" + image.getSubmittedFileName();
                    System.out.println("[DEBUG] Saving image as: " + fileName);
                    image.write(uploadPath + File.separator + fileName);
                    imagePath = fileName;
                } else if ("update".equals(action)) {
                    imagePath = request.getParameter("existingImage");
                    System.out.println("[DEBUG] Keeping existing image: " + imagePath);
                }
            } catch (Exception e) {
                System.out.println("[DEBUG] Image upload error: " + e.getMessage());
                session.setAttribute("error", "Error uploading image: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/admin/manageProduct");
                return;
            }

            ProductModel product = new ProductModel(
                productId,
                request.getParameter("productName"),
                request.getParameter("productCategory"),
                request.getParameter("productDescription"),
                Double.parseDouble(request.getParameter("productPrice")),
                Integer.parseInt(request.getParameter("productQuantity")),
                imagePath
            );
            
            System.out.println("[DEBUG] Product details:");
            System.out.println("  Name: " + product.getProductName());
            System.out.println("  Category: " + product.getProductCategory());
            System.out.println("  Price: " + product.getProductPrice());
            System.out.println("  Quantity: " + product.getProductQuantity());
            System.out.println("  Image: " + product.getImage());

            boolean success = false;
            String message = "";
            
            if ("add".equals(action)) {
                System.out.println("[DEBUG] Adding new product");
                success = productService.addProduct(product);
                message = success ? "Product added successfully!" : "Failed to add product";
            } else if ("update".equals(action)) {
                System.out.println("[DEBUG] Updating product");
                success = productService.updateProduct(product);
                message = success ? "Product updated successfully!" : "Failed to update product";
            }

            System.out.println("[DEBUG] Operation result: " + (success ? "Success" : "Failure"));
            if (success) {
                session.setAttribute("success", message);
            } else {
                session.setAttribute("error", message);
            }
            
            response.sendRedirect(request.getContextPath() + "/admin/manageProduct");
            
        } catch (Exception e) {
            System.out.println("[DEBUG] Exception in doPost: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("error", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/manageProduct");
        }
        
        System.out.println("==== Exiting ManageProductController.doPost() ====\n");
    }
}