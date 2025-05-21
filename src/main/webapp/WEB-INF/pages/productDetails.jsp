<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Product Details | BestCure</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/products.css" />
</head>
<body>
<!-- Product Details Section -->
<section class="product-details-section">
    <c:choose>
        <c:when test="${not empty product}">
            <div class="product-details-container">
                <div class="product-image">
                    <img src="${pageContext.request.contextPath}/resources/products/${product.image}"
                         alt="${product.productName}"
                         onerror="this.src='${pageContext.request.contextPath}/resources/default-product.png'" />
                </div>
                <div class="product-info">
                    <h1>${product.productName}</h1>
                    <div class="stars">★★★★★</div>
                    <div class="price">Rs ${product.productPrice}</div>
                    <div class="original-price">Rs ${product.productPrice + 10}</div>
                    <div class="product-description">${product.productDescription != null ? product.productDescription : 'No description available'}</div>
                    <div class="stock-info">In Stock: ${product.productQuantity} items</div>
                    <form action="${pageContext.request.contextPath}/cart" method="post" class="add-cart-form">
                        <div class="qty-label">Qty:</div>
                        <input type="number" name="quantity" value="1" min="1" max="${product.productQuantity}" class="qty-input" />
                        <div class="unit-label">/Item</div>
                        <input type="hidden" name="action" value="add" />
                        <input type="hidden" name="productId" value="${product.productId}" />
                        <button type="submit" class="add-to-cart-btn">Add to Cart</button>
                    </form>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="error-message">Product not found.</div>
        </c:otherwise>
    </c:choose>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>