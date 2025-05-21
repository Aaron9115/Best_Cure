<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order History</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/orderHistory.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <main class="order-history-container">
        <h1>Order History</h1>
        
        <c:if test="${not empty orderSuccess}">
            <div class="alert success">${orderSuccess}</div>
        </c:if>
        
        <c:if test="${not empty orderError}">
            <div class="alert error">${orderError}</div>
        </c:if>
        
        <c:choose>
            <c:when test="${empty orders}">
                <div class="empty-orders">
                    <p>You haven't placed any orders yet.</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn">Browse Products</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="orders-list">
                    <c:forEach items="${orders}" var="order">
                        <div class="order-card">
                            <div class="order-header">
                                <div>
                                    <h3>Order #${order.orderId}</h3>
                                    <p class="order-date">
                                        <fmt:formatDate value="${order.orderDate}" pattern="MMMM dd, yyyy 'at' hh:mm a"/>
                                    </p>
                                </div>
                                <div class="order-status ${order.status.toLowerCase()}">
                                    ${order.status}
                                </div>
                            </div>
                            
                            <div class="order-details">
                                <div class="order-items">
                                    <c:forEach items="${order.products}" var="product">
                                        <div class="order-item">
                                            <img src="${pageContext.request.contextPath}/resources/product/${product.image}" 
                                                 alt="${product.productName}" class="product-image">
                                            <div class="item-info">
                                                <h4>${product.productName}</h4>
                                                <p>${product.productCategory}</p>
                                                <p>Quantity: ${product.productQuantity}</p>
                                            </div>
                                            <div class="item-price">
                                                Rs ${product.productPrice * product.productQuantity}
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                                
                                <div class="order-summary">
                                    <div class="summary-row">
                                        <span>Subtotal</span>
                                        <span>Rs ${order.totalAmount}</span>
                                    </div>
                                    <div class="summary-row">
                                        <span>Shipping</span>
                                        <span>FREE</span>
                                    </div>
                                    <div class="summary-row total">
                                        <span>Total</span>
                                        <span>Rs ${order.totalAmount}</span>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="order-footer">
                                <div class="shipping-info">
                                    <h4>Shipping Address</h4>
                                    <p>${order.shippingAddress}</p>
                                </div>
                                <div class="payment-info">
                                    <h4>Payment Method</h4>
                                    <p>${order.paymentMethod}</p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </main>
    
    <%@ include file="footer.jsp" %>
</body>
</html>