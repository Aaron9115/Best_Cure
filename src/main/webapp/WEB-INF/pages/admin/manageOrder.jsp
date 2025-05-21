<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
  <title>Manage Orders</title>
</head>
<body>
  <div class="dashboard-container">
    <aside class="sidebar">
      <h2>Admin</h2>
      <ul>
        <li><a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fas fa-home"></i> Dashboard</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/user"><i class="fas fa-user"></i> Users</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/manageProduct"><i class="fas fa-box"></i> Products</a></li>
        <li class="active"><a href="${pageContext.request.contextPath}/admin/orderList"><i class="fas fa-list"></i> Order Lists</a></li>
        <li><a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
      </ul>
    </aside>

    <main class="main-content">
      <div class="top-bar"><h1>Manage Orders</h1></div>

      <!-- Success message display -->
      <c:if test="${not empty updateMessage}">
        <div class="alert success" style="margin-bottom: 15px;">
          <i class="fas fa-check-circle"></i> ${updateMessage}
        </div>
      </c:if>

      <section class="order-list">
        <h2>Orders</h2>
        <table>
          <thead>
            <tr>
              <th>Order ID</th>
              <th>User ID</th>
              <th>Date</th>
              <th>Total</th>
              <th>Status</th>
              <th>Update</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="order" items="${orders}">
              <tr>
                <td>${order.orderId}</td>
                <td>${order.userId}</td>
                <td>${order.orderDate}</td>
                <td>$${order.totalAmount}</td>
                <td>
                  <form action="${pageContext.request.contextPath}/admin/orderList" method="post" 
                        style="display: flex; align-items: center; gap: 8px;">
                    <input type="hidden" name="orderId" value="${order.orderId}" />
                    <select name="status" style="padding: 6px;">
                      <option value="PENDING" ${order.status == 'PENDING' ? 'selected' : ''}>PENDING</option>
                      <option value="PROCESSING" ${order.status == 'PROCESSING' ? 'selected' : ''}>PROCESSING</option>
                      <option value="SHIPPED" ${order.status == 'SHIPPED' ? 'selected' : ''}>SHIPPED</option>
                      <option value="COMPLETED" ${order.status == 'COMPLETED' ? 'selected' : ''}>COMPLETED</option>
                      <option value="CANCELLED" ${order.status == 'CANCELLED' ? 'selected' : ''}>CANCELLED</option>
                    </select>
                    <button type="submit" class="action-btn complete" style="padding: 6px 10px; font-size: 14px;">
                      <i class="fas fa-check"></i> Update
                    </button>
                  </form>
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
