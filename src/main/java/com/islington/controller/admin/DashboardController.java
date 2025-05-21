package com.islington.controller.admin;

import com.islington.model.OrderModel;
import com.islington.service.DashboardService;
import com.islington.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard")
public class DashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DashboardService dashboardService = new DashboardService();
    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("user_role"))) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        try {
            // Get dashboard statistics
            int totalOrders = orderService.getTotalOrders();
            int pendingOrders = orderService.getOrdersByStatus("PENDING").size();
            int completedOrders = orderService.getOrdersByStatus("COMPLETED").size();
            List<OrderModel> recentOrders = orderService.getRecentOrders(5);

            // Set request attributes
            request.setAttribute("totalOrders", totalOrders);
            request.setAttribute("pendingOrders", pendingOrders);
            request.setAttribute("completedOrders", completedOrders);
            request.setAttribute("recentOrders", recentOrders);

            request.getRequestDispatcher("/WEB-INF/pages/admin/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/admin/error.jsp").forward(request, response);
        }
    }
}