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

        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        // Get and remove the update message from session for one-time display
        String updateMessage = (String) session.getAttribute("updateMessage");
        if (updateMessage != null) {
            request.setAttribute("updateMessage", updateMessage);
            session.removeAttribute("updateMessage");
        }

        try {
            List<OrderModel> orders = orderService.getAllOrders();
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/WEB-INF/pages/admin/manageOrder.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error loading orders: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/admin/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderIdStr = request.getParameter("orderId");
        String status = request.getParameter("status");

        HttpSession session = request.getSession();

        try {
            int orderId = Integer.parseInt(orderIdStr);
            orderService.updateOrderStatus(orderId, status);
            session.setAttribute("updateMessage", "Order status updated successfully for Order ID: " + orderId);
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("updateMessage", "Failed to update order status.");
        }

        // Redirect to GET to avoid form resubmission and display updated order list
        response.sendRedirect(request.getContextPath() + "/admin/orderList");
    }
}
