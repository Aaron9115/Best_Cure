package com.islington.controller;

import com.islington.model.CartModel;
import com.islington.model.ProductModel;
import com.islington.service.CartService;
import com.islington.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    private ProductService productService = new ProductService();
    private CartService cartService = new CartService();
    private static final long serialVersionUID = 1L;
    
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");
        
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Always get fresh cart from database
        CartModel cart = cartService.getCartByUserId(userId);
        session.setAttribute("cart", cart);
        
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");
        
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Get or create cart
        CartModel cart = (CartModel) session.getAttribute("cart");
        if (cart == null) {
            cart = cartService.getCartByUserId(userId);
            session.setAttribute("cart", cart);
        }
        
        try {
            String action = request.getParameter("action");
            if (action != null) {
                // Handle form submissions with action parameter
                handleFormAction(request, cart);
            } else {
                // Handle REST-style paths
                String path = request.getServletPath();
                if (path.endsWith("/add")) {
                    handleAddToCart(request, cart);
                } else if (path.endsWith("/update")) {
                    handleUpdateCart(request, cart);
                } else if (path.endsWith("/remove")) {
                    handleRemoveFromCart(request, cart);
                } else if (path.endsWith("/clear")) {
                    cart.clearCart();
                    session.setAttribute("success", "Cart cleared successfully");
                }
            }
            
            // Sync with database
            if (!cartService.syncCartWithDatabase(cart)) {
                throw new ServletException("Failed to sync cart with database");
            }
            
            session.setAttribute("cart", cart);
            response.sendRedirect(request.getContextPath() + "/cart");
            
        } catch (Exception e) {
            session.setAttribute("error", "Error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }

    private void handleFormAction(HttpServletRequest request, CartModel cart) 
            throws ServletException {
        String action = request.getParameter("action");
        if (action == null) return;
        
        switch (action.toLowerCase()) {
            case "add":
                handleAddToCart(request, cart);
                break;
            case "update":
                handleUpdateCart(request, cart);
                break;
            case "remove":
                handleRemoveFromCart(request, cart);
                break;
            default:
                throw new ServletException("Invalid action: " + action);
        }
    }

    private void handleAddToCart(HttpServletRequest request, CartModel cart) 
            throws ServletException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = request.getParameter("quantity") != null ? 
            Integer.parseInt(request.getParameter("quantity")) : 1;
        
        ProductModel product = productService.getProductById(productId);
        if (product == null) {
            throw new ServletException("Product not found");
        }
        
        cart.addProduct(product, quantity);
        request.getSession().setAttribute("success", "Product added to cart");
    }

    private void handleUpdateCart(HttpServletRequest request, CartModel cart) 
            throws ServletException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        
        if (quantity <= 0) {
            cart.removeProduct(productId);
            request.getSession().setAttribute("success", "Product removed from cart");
        } else {
            cart.updateQuantity(productId, quantity);
            request.getSession().setAttribute("success", "Cart updated successfully");
        }
    }

    private void handleRemoveFromCart(HttpServletRequest request, CartModel cart) {
        int productId = Integer.parseInt(request.getParameter("productId"));
        cart.removeProduct(productId);
        request.getSession().setAttribute("success", "Product removed from cart");
    }
}