<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home | BestCure</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>
   <!-- Hero Section -->
  <section class="hero">
    <div class="hero-text">
      <h2>Your Health, <span>Our First Priority</span></h2>
      <p>Discover trusted products for wellness, skincare, baby care, and medical essentials.</p>
      <a href="#products" class="btn">Shop Now</a>
    </div>
    <div class="hero-img">
      <img src="${pageContext.request.contextPath}/resources/first.jpg" alt="Medicines">
    </div>
  </section>

  <!-- Search Section -->
  <section class="search-section">
    <h3>What are you looking for?</h3>
    <input type="text" id="searchInput" placeholder="Search for products..." />
  </section>

  <section class="services">
    <h3>Our Services</h3>
    <div class="hover-service-grid">
      <div class="hover-service-card">
        <img src="resources/health.png" alt="Health Icon" />
        <h4>Wide range of Health products</h4>
        <p>Browse thousands of certified health and wellness items — from essential medicines to daily care products.</p>
      </div>
      <div class="hover-service-card">
        <img src="resources/icon-quality.svg.png" alt="Quality Icon" />
        <h4>Trusted Quality, Guarantee</h4>
        <p>All our products come from verified brands and manufacturers, ensuring safety and effectiveness.</p>
      </div>
      <div class="hover-service-card">
        <img src="resources/icon-shopping.svg.png" alt="Shopping Icon" />
        <h4>Seamless Shopping Experience</h4>
        <p>Order quickly and easily with a user-friendly interface, secure payments, and reliable delivery options.</p>
      </div>
      <div class="hover-service-card">
        <img src="resources/care.png" alt="Care Icon" />
        <h4>Personalized Care and Support</h4>
        <p>Get expert help, product recommendations, and customer service every step of the way — your health is our mission.</p>
      </div>
    </div>
  </section>
  

  <!-- Products Grid -->
  <section class="products" id="products">
    <h3>Our Products</h3>
    
    <c:if test="${not empty error}">
      <div class="error-message">${error}</div>
    </c:if>
    
    <c:choose>
      <c:when test="${not empty products}">
        <div class="product-grid">
          <c:forEach var="product" items="${products}">
            <div class="product-card">
              <img src="${pageContext.request.contextPath}/resources/products/${product.image}" 
                   alt="${product.productName}" onerror="this.src='${pageContext.request.contextPath}/resources/default-product.png'"/>
              <p class="product-title">${product.productName}</p>
              <p class="product-price">Rs ${product.productPrice}</p>
              <form action="${pageContext.request.contextPath}/cart" method="post" class="add-cart-form">
                <input type="hidden" name="action" value="add" />
                <input type="hidden" name="productId" value="${product.productId}" />
                <input type="number" name="quantity" value="1" min="1" class="qty-input" />
                <button type="submit" class="add-cart-btn">Add to Cart</button>
              </form>
            </div>
          </c:forEach>
        </div>
      </c:when>
      <c:otherwise>
        <div class="no-products">No products found. Please try a different search or category.</div>
      </c:otherwise>
    </c:choose>
  </section>

  <%@ include file="footer.jsp" %>
</body>
</html>