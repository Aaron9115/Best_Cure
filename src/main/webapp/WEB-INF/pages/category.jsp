<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Skin Care Products</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/category.css">
</head>

<body>
<!--------------------------category------section--------starts--->
<section class="category-section">
    <h1>Skin Care</h1>
    <div class="product-grid">

        <div class="product-card">
            <img src="${pageContext.request.contextPath}/images/homep1.png" alt="Cetaphil Moisturiser" />
            <h4>Product 1</h4>
            <div class="stars">★★★★★</div>
            <div class="product-title">Cetaphil Moisturiser</div>
            <div class="price">$150.50</div>
            <div class="original-price">$160</div>
            <div class="qty-label">Qty:</div>
            <input type="number" class="qty-input" value="1" />
            <div class="unit-label">/Item</div>
            <button class="add-to-cart-btn">Add To Cart</button>
            <div class="product-description">Gentle moisturizer for soft skin.</div>
        </div>

        <div class="product-card">
            <img src="${pageContext.request.contextPath}/images/homep2.png" alt="Derma Cleanser" />
            <h4>Product 2</h4>
            <div class="stars">★★★★☆</div>
            <div class="product-title">Derma Cleanser</div>
            <div class="price">$120.00</div>
            <div class="original-price">$130</div>
            <div class="qty-label">Qty:</div>
            <input type="number" class="qty-input" value="1" />
            <div class="unit-label">/Item</div>
            <button class="add-to-cart-btn">Add To Cart</button>
            <div class="product-description">Gentle cleanser for all skin types.</div>
        </div>

        <!-- Add more product cards as needed -->

    </div>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>