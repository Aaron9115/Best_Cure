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
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        // Check for success/error messages in session and transfer to request
        String success = (String) session.getAttribute("success");
        String error = (String) session.getAttribute("error");
        
        if (success != null) {
            request.setAttribute("success", success);
            session.removeAttribute("success");
        }
        if (error != null) {
            request.setAttribute("error", error);
            session.removeAttribute("error");
        }

        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            String productIdStr = request.getParameter("id");
            if (productIdStr != null && !productIdStr.trim().isEmpty()) {
                try {
                    int productId = Integer.parseInt(productIdStr);
                    ProductModel product = productService.getProductById(productId);
                    request.setAttribute("product", product);
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid product ID");
                }
            }
        } else if ("delete".equals(action)) {
            try {
                int productId = Integer.parseInt(request.getParameter("id"));
                boolean deleted = productService.deleteProduct(productId);
                if (deleted) {
                    session.setAttribute("success", "Product deleted successfully");
                } else {
                    session.setAttribute("error", "Failed to delete product");
                }
            } catch (Exception e) {
                session.setAttribute("error", "Error deleting product: " + e.getMessage());
            }
            response.sendRedirect(request.getContextPath() + "/admin/manageProduct");
            return;
        }

        List<ProductModel> productList = productService.getAllProducts();
        request.setAttribute("productList", productList);
        request.getRequestDispatcher("/WEB-INF/pages/admin/manageProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        try {
            String action = request.getParameter("action");
            int productId = 0;
            
            if (request.getParameter("productId") != null && !request.getParameter("productId").isEmpty()) {
                productId = Integer.parseInt(request.getParameter("productId"));
            }

            // Handle file upload
            String imagePath = null;
            try {
                Part image = request.getPart("image");
                if (image != null && image.getSize() > 0) {
                    String uploadPath = getServletContext().getRealPath("") + "/resources/products";
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) uploadDir.mkdirs();
                    
                    String fileName = System.currentTimeMillis() + "_" + image.getSubmittedFileName();
                    image.write(uploadPath + File.separator + fileName);
                    imagePath = fileName;
                } else if ("update".equals(action)) {
                    imagePath = request.getParameter("existingImage");
                }
            } catch (Exception e) {
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

            boolean success = false;
            String message = "";
            
            if ("add".equals(action)) {
                success = productService.addProduct(product);
                message = success ? "Product added successfully!" : "Failed to add product";
            } else if ("update".equals(action)) {
                success = productService.updateProduct(product);
                message = success ? "Product updated successfully!" : "Failed to update product";
            }

            if (success) {
                session.setAttribute("success", message);
            } else {
                session.setAttribute("error", message);
            }
            
            response.sendRedirect(request.getContextPath() + "/admin/manageProduct");
            
        } catch (Exception e) {
            session.setAttribute("error", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/manageProduct");
        }
    }
}