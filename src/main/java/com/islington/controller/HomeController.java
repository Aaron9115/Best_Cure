package com.islington.controller;

import com.islington.model.ProductModel;
import com.islington.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(asyncSupported = true, urlPatterns = { "/home", "/" })
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get all products or products by category if filter is applied
            String category = request.getParameter("category");
            List<ProductModel> products;
            
            if (category != null && !category.isEmpty()) {
                products = productService.getProductsByCategory(category);
            } else {
                products = productService.getAllProducts();
            }
            
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading products");
            request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
        }
    }
}