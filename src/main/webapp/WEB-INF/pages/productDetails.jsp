<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.productName} | BestCure</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/productDetails.css">
</head>
<body>

<section class="product-detail-section">
    <div class="container">
        <div class="product-detail">
            <div class="product-images">
                <div class="main-image">
                    <img src="${pageContext.request.contextPath}/resources/products/${product.image}" 
                         alt="${product.productName}">
                </div>
            </div>
            
            <div class="product-info">
                <h1>${product.productName}</h1>
                <div class="product-meta">
                    <span class="category">${product.productCategory}</span>
                    <span class="price">$${product.productPrice}</span>
                </div>
                
                <div class="product-description">
                    <h3>Description</h3>
                    <p>${product.productDescription}</p>
                </div>
                
                <form action="${pageContext.request.contextPath}/cart" method="post" class="add-to-cart">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="productId" value="${product.productId}">
                    
                    <div class="quantity-selector">
                        <label for="quantity">Quantity:</label>
                        <input type="number" id="quantity" name="quantity" value="1" min="1" max="${product.productQuantity}">
                    </div>
                    
                    <button type="submit" class="add-to-cart-btn">
                        <i class="fas fa-cart-plus"></i> Add to Cart
                    </button>
                </form>
            </div>
        </div>
    </div>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>