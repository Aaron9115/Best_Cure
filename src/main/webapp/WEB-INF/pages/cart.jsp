<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your Cart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <main class="cart-container">
        <h1>Your Shopping Cart</h1>
        
        <c:if test="${not empty error}">
            <div class="alert error">${error}</div>
        </c:if>
        
        <c:if test="${not empty success}">
            <div class="alert success">${success}</div>
        </c:if>
        
        <c:choose>
            <c:when test="${empty cart or empty cart.products}">
                <div class="empty-cart">
                    <p>Your cart is empty</p>
                    <a href="${pageContext.request.contextPath}/home" class="btn">Continue Shopping</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="cart-items">
                    <table>
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Total</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${cart.products}" var="product">
                                <tr>
                                    <td class="product-info">
                                        <img src="${pageContext.request.contextPath}/resources/products/${product.image}" 
                                             alt="${product.productName}" class="product-image">
                                        <div>
                                            <h3>${product.productName}</h3>
                                            <p>${product.productCategory}</p>
                                        </div>
                                    </td>
                                    <td class="price">Rs ${product.productPrice}</td>
                                    <td class="quantity">
                                        <form action="${pageContext.request.contextPath}/cart" method="post">
                                            <input type="hidden" name="productId" value="${product.productId}">
                                            <input type="number" name="quantity" 
                                                   value="${product.productQuantity}" 
                                                   min="1" max="100" onchange="this.form.submit()">
                                        </form>
                                    </td>
                                    <td class="total">Rs ${product.productPrice * product.productQuantity}</td>
                                    <td class="action">
                                        <form action="${pageContext.request.contextPath}/cart" method="post">
										    <input type="hidden" name="action" value="remove">
										    <input type="hidden" name="productId" value="${product.productId}">
										    <button type="submit">Remove</button>
										</form>
								     </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                
                <div class="cart-summary">
                    <h2>Order Summary</h2>
                    <div class="summary-row">
                        <span>Items (${cart.totalQuantity})</span>
                        <span>Rs ${cart.totalPrice}</span>
                    </div>
                    <div class="summary-row">
                        <span>Shipping</span>
                        <span>FREE</span>
                    </div>
                    <div class="summary-row total">
                        <span>Total</span>
                        <span>Rs ${cart.totalPrice}</span>
                    </div>
                    
                    <div class="action-buttons">
                        <a href="${pageContext.request.contextPath}/home" class="continue-btn">Continue Shopping</a>
                        <form action="${pageContext.request.contextPath}/place-order" method="post">
                            <button type="submit" class="checkout-btn">Proceed to Checkout</button>
                        </form>
                        <a href="${pageContext.request.contextPath}/place-order" class="continue-btn">order history</a>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </main>
    
    <%@ include file="footer.jsp" %>
</body>
</html>