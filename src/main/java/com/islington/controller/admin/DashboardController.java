package com.islington.controller.admin;

import com.islington.service.DashboardService;
import com.islington.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin/dashboard")
public class DashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DashboardService dashboardService = new DashboardService();
    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        System.out.println("DashboardController session: " + session);
        if (session != null) {
            System.out.println("DashboardController role: " + session.getAttribute("role"));
            System.out.println("DashboardController session ID: " + session.getId());
        }

        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            request.setAttribute("totalUsers", dashboardService.getTotalUsers());
            request.setAttribute("totalProducts", dashboardService.getTotalProducts());
            request.setAttribute("totalOrders", orderService.getTotalOrders());
            request.setAttribute("recentOrders", orderService.getRecentOrders(5));

            request.getRequestDispatcher("/WEB-INF/pages/admin/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/admin/error.jsp").forward(request, response);
        }
    }
}
