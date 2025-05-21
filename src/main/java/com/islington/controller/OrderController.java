package com.islington.controller;

import com.islington.model.CartModel;
import com.islington.model.OrderModel;
import com.islington.model.UserModel;
import com.islington.service.OrderService;
import com.islington.service.CartService;
import com.islington.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "OrderController", urlPatterns = {"/place-order", "/order-history"})
public class OrderController extends HttpServlet {
    private OrderService orderService = new OrderService();
    private CartService cartService = new CartService();
    private UserService userService = new UserService();
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Show order history
        List<OrderModel> order = orderService.getOrdersByUserId(user.getUserId());
        request.setAttribute("orders", order);
        request.getRequestDispatcher("/WEB-INF/pages/orderHistory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Debug session contents
        System.out.println("=== Starting Order Processing ===");
        System.out.println("Session attributes: ");
        java.util.Enumeration<String> attrNames = session.getAttributeNames();
        while (attrNames.hasMoreElements()) {
            String name = attrNames.nextElement();
            System.out.println(name + ": " + session.getAttribute(name));
        }

        // Get user - handle both UserModel and user_id cases
        UserModel user = (UserModel) session.getAttribute("user");
        if (user == null) {
            Integer userId = (Integer) session.getAttribute("user_id");
            if (userId != null) {
                // You'll need to implement getUserById in your UserService
                user = userService.getUserById(userId);
                if (user != null) {
                    session.setAttribute("user", user); // Store for future use
                }
            }
        }

        if (user == null) {
            System.out.println("No user found - redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        CartModel cart = (CartModel) session.getAttribute("cart");
        if (cart == null || cart.getProducts().isEmpty()) {
            System.out.println("Empty cart - redirecting to cart page");
            session.setAttribute("orderError", "Your cart is empty");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        System.out.println("Cart contains " + cart.getProducts().size() + " items");
        
        // Create order
        OrderModel order = new OrderModel();
        order.setUserId(user.getUserId());
        order.setProducts(new ArrayList<>(cart.getProducts()));
        order.setTotalAmount(cart.getTotalPrice());
        order.setShippingAddress("Default Address");
        order.setPaymentMethod("Cash on Delivery");
        order.setStatus("PROCESSING");
        order.setOrderDate(new Date());

        System.out.println("Attempting to create order...");
        boolean orderCreated = orderService.createOrder(order);
        System.out.println("Order creation result: " + orderCreated);

        if (orderCreated) {
            System.out.println("Order created - clearing cart");
            cart.clearCart();
            cartService.syncCartWithDatabase(cart);
            
            session.setAttribute("orderSuccess", "Order placed successfully!");
            System.out.println("Redirecting to order-history");
            response.sendRedirect(request.getContextPath() + "/order-history");
        } else {
            System.out.println("Order creation failed");
            session.setAttribute("orderError", "Failed to place order. Please try again.");
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }
}