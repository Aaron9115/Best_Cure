package com.islington.controller;

import com.islington.model.ProductModel;
import com.islington.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchController extends HttpServlet {
    private final ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String query = request.getParameter("query");
        
        if (query == null || query.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        List<ProductModel> searchResults = productService.searchProducts(query);
        request.setAttribute("searchResults", searchResults);
        request.setAttribute("searchQuery", query);
        request.getRequestDispatcher("/WEB-INF/pages/search.jsp").forward(request, response);
    }
}