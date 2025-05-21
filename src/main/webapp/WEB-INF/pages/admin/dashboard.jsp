<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
  <title>Admin Dashboard</title>
</head>
<body>
  <div class="dashboard-container">
    <aside class="sidebar">
      <h2>Admin</h2>
      <ul>
        <li class="active"><a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fas fa-home" style="color: black;"></i> Dashboard</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/user"><i class="fas fa-user" style="color: black;"></i> Users</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/manageProduct"><i class="fas fa-box" style="color: black;"></i> Products</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/orderList"><i class="fas fa-list" style="color: black;"></i> Order lists</a></li>
        <li><a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt" style="color: black;"></i> Logout</a></li>
      </ul>
    </aside>
    <main class="main-content">
      <div class="top-bar"><h1>Dashboard</h1></div>
      <section class="cards">
        <div class="card">
          <h3>Total Orders</h3>
          <p>${totalOrders}</p>
        </div>
        <div class="card">
          <h3>Pending Orders</h3>
          <p>${pendingOrders}</p>
        </div>
        <div class="card">
          <h3>Completed Orders</h3>
          <p>${completedOrders}</p>
        </div>
      </section>

      <!-- Order List Section -->
      <section class="order-list">
        <h2>Recent Orders</h2>
        <table>
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Customer ID</th>
              <th>Date</th>
              <th>Total</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${recentOrders}" var="order">
              <tr>
                <td>${order.orderId}</td>
                <td>${order.userId}</td>
                <td>${order.orderDate}</td>
                <td>$${order.totalAmount}</td>
                <td>
                  <span class="status ${order.status.toLowerCase()}">${order.status}</span>
                </td>
                <td>
                  <a href="${pageContext.request.contextPath}/admin/orderDetails?id=${order.orderId}" class="action-btn view">
                    <i class="fas fa-eye"></i>
                  </a>
                  <c:if test="${order.status == 'PENDING'}">
                    <a href="${pageContext.request.contextPath}/admin/updateOrderStatus?id=${order.orderId}&status=COMPLETED" class="action-btn complete">
                      <i class="fas fa-check"></i>
                    </a>
                  </c:if>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </section>
    </main>
  </div>
</body>
</html>
