package com.islington.controller.admin;

import com.islington.model.OrderModel;
import com.islington.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/orderList")
public class OrderListController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("\n==== Entering OrderListController.doGet() ====");
        
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

        // Check admin role - CHANGED FROM "role" TO "user_role"
        String role = (String) session.getAttribute("user_role");
        System.out.println("[DEBUG] User role from session: " + role);
        
        if (!"admin".equalsIgnoreCase(role)) {
            System.out.println("[DEBUG] User doesn't have admin privileges (role: " + role + ") - redirecting to home");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // Get and remove the update message from session for one-time display
        String updateMessage = (String) session.getAttribute("updateMessage");
        System.out.println("[DEBUG] Update message from session: " + updateMessage);
        if (updateMessage != null) {
            request.setAttribute("updateMessage", updateMessage);
            session.removeAttribute("updateMessage");
        }

        try {
            System.out.println("[DEBUG] Fetching all orders");
            List<OrderModel> orders = orderService.getAllOrders();
            System.out.println("[DEBUG] Found " + (orders != null ? orders.size() : 0) + " orders");
            
            request.setAttribute("orders", orders);
            System.out.println("[DEBUG] Forwarding to manageOrder.jsp");
            request.getRequestDispatcher("/WEB-INF/pages/admin/manageOrder.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("[DEBUG] Error loading orders: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error loading orders: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/admin/error.jsp").forward(request, response);
        }
        
        System.out.println("==== Exiting OrderListController.doGet() ====\n");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("\n==== Entering OrderListController.doPost() ====");
        
        HttpSession session = request.getSession();
        System.out.println("[DEBUG] Session ID in doPost: " + session.getId());
        
        String orderIdStr = request.getParameter("orderId");
        String status = request.getParameter("status");
        System.out.println("[DEBUG] Updating order - ID: " + orderIdStr + ", Status: " + status);

        try {
            int orderId = Integer.parseInt(orderIdStr);
            boolean success = orderService.updateOrderStatus(orderId, status);
            
            if (success) {
                System.out.println("[DEBUG] Order status updated successfully");
                session.setAttribute("updateMessage", "Order status updated successfully for Order ID: " + orderId);
            } else {
                System.out.println("[DEBUG] Failed to update order status");
                session.setAttribute("updateMessage", "Failed to update order status.");
            }
        } catch (Exception e) {
            System.out.println("[DEBUG] Exception updating order: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("updateMessage", "Failed to update order status.");
        }

        System.out.println("[DEBUG] Redirecting to order list");
        response.sendRedirect(request.getContextPath() + "/admin/orderList");
        
        System.out.println("==== Exiting OrderListController.doPost() ====\n");
    }
}