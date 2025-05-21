package com.islington.controller;

import com.islington.model.ProductModel;
import com.islington.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/productDetails"})
public class ProductDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String productId = req.getParameter("productId");
            if (productId != null && !productId.trim().isEmpty()) {
                ProductModel product = productService.getProductById(Integer.parseInt(productId));
                if (product != null) {
                    req.setAttribute("product", product);
                } else {
                    req.setAttribute("error", "Product not found");
                }
            } else {
                req.setAttribute("error", "Invalid product ID");
            }
            req.getRequestDispatcher("/WEB-INF/pages/product-details.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error loading product details");
            req.getRequestDispatcher("/WEB-INF/pages/product-details.jsp").forward(req, resp);
        }
    }
}