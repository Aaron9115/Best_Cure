package com.islington.controller;

import com.islington.service.ProductService;
import com.islington.model.ProductModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = { "/products" })
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("email") == null) {
            req.getSession(true).setAttribute("redirectAfterLogin", "/products");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<ProductModel> products = productService.getAllProducts();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/pages/products.jsp").forward(req, resp);
    }
}
