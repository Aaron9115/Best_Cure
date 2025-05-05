package com.islington.controller.admin;

import java.io.IOException;
import java.util.List;

import com.islington.model.ProductModel;
import com.islington.service.DashboardService;
import com.islington.service.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/dashboard")
public class DashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final DashboardService dashboardService = new DashboardService();
    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get statistics for the dashboard
            int totalUsers = dashboardService.getTotalUsers(); // Total number of users
            int totalProducts = dashboardService.getTotalProducts(); // Total number of products
            int totalOrders = orderService.getTotalOrders(); // Total number of orders
            List<ProductModel> recentProducts = dashboardService.getRecentProducts(); // Recent products added

            // Set attributes for the JSP
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("totalProducts", totalProducts);
            request.setAttribute("totalOrders", totalOrders);
            request.setAttribute("recentProducts", recentProducts);

            // Forward to the dashboard JSP page
            request.getRequestDispatcher("/WEB-INF/pages/admin/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while loading the dashboard.");
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }
}
