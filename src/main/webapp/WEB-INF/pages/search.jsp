<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Results | BestCure</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search.css">
</head>
<body>

<section class="search-results-section">
    <div class="container">
        <h2>Search Results for: "${searchQuery}"</h2>
        
        <c:choose>
            <c:when test="${not empty searchResults}">
                <div class="product-grid">
                    <c:forEach var="product" items="${searchResults}">
                        <div class="product-card">
                            <a href="${pageContext.request.contextPath}/product?id=${product.productId}">
                                <div class="product-image">
                                    <c:if test="${not empty product.image}">
                                        <img src="${pageContext.request.contextPath}/resources/products/${product.image}" 
                                             alt="${product.productName}">
                                    </c:if>
                                </div>
                                <div class="product-info">
                                    <h3>${product.productName}</h3>
                                    <p class="category">${product.productCategory}</p>
                                    <p class="price">$${product.productPrice}</p>
                                </div>
                            </a>
                            <form action="${pageContext.request.contextPath}/cart" method="post" class="add-to-cart-form">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="productId" value="${product.productId}">
                                <input type="number" name="quantity" value="1" min="1" class="quantity-input">
                                <button type="submit" class="add-to-cart-btn">
                                    <i class="fas fa-cart-plus"></i> Add to Cart
                                </button>
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="no-results">
                    <i class="fas fa-search"></i>
                    <p>No products found matching your search.</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn">Browse All Products</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>