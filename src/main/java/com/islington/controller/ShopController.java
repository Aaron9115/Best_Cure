package com.islington.controller;

import com.islington.model.ProductModel;
import com.islington.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = { "/products" })
public class ShopController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ❌ Removed login check to allow public access to products

        // Get the category from request
        String category = req.getParameter("category");
        List<ProductModel> products;

        // Filter by category if specified
        if (category != null && !category.trim().isEmpty()) {
            products = productService.getProductsByCategory(category);
            req.setAttribute("category", category);
        } else {
            products = productService.getAllProducts();
        }

        // Set product list and forward to JSP
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/pages/products.jsp").forward(req, resp);
    }
}
