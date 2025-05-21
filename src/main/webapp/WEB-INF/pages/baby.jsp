<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Baby Products</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/baby.css">
</head>

<body>

<section class="category-section">
    <h1>${categoryName} Products</h1>
    <div class="product-grid">
        <c:forEach var="product" items="${products}">
            <div class="product-card">
                <img src="${pageContext.request.contextPath}/images/${product.imageName}" alt="${product.name}" />
                <h4>${product.name}</h4>
                <div class="stars">
                    <c:forEach begin="1" end="5" var="i">
                        <c:choose>
                            <c:when test="${i <= product.rating}">★</c:when>
                            <c:otherwise>☆</c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
                <div class="product-title">${product.name}</div>
                <div class="price">$${product.price}</div>
                <c:if test="${product.originalPrice > product.price}">
                    <div class="original-price">$${product.originalPrice}</div>
                </c:if>
                <div class="qty-label">Qty:</div>
                <input type="number" class="qty-input" value="1" min="1" />
                <div class="unit-label">/Item</div>
                <button class="add-to-cart-btn" onclick="addToCart(${product.id})">Add To Cart</button>
                <div class="product-description">${product.description}</div>
            </div>
        </c:forEach>
    </div>
</section>

<%@ include file="footer.jsp" %>

<script>
function addToCart(productId) {
    const quantity = document.querySelector(`input[data-product="${productId}"]`).value;
    // AJAX call to add to cart
    fetch('${pageContext.request.contextPath}/add-to-cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `productId=${productId}&quantity=${quantity}`
    })
    .then(response => response.json())
    .then(data => {
        if(data.success) {
            alert('Product added to cart!');
            // Update cart count in header
            document.getElementById('cart-count').innerText = data.cartCount;
        } else {
            alert('Failed to add product to cart');
        }
    });
}
</script>

</body>
</html>