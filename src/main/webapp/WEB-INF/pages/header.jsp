<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home | BestCure</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat&family=Roboto+Condensed&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
</head>
<body>

<header id="header">
    <a href="${pageContext.request.contextPath}/home" class="logo">
        <img src="${pageContext.request.contextPath}/resources/logo.png"
             class="first" alt="BestCure Logo">
    </a>
    
    <div class="search-container">
        <form action="${pageContext.request.contextPath}/search" method="get">
            <input type="text" placeholder="Search products..." name="query" class="search-input">
            <button type="submit" class="search-button">
                <i class="fas fa-search"></i>
            </button>
        </form>
    </div>
    
    <button class="mobile-menu-btn" id="mobileMenuBtn">
        <i class="fas fa-bars"></i>
    </button>
    
    <ul class="navbar" id="navbar">
        <li><a href="${pageContext.request.contextPath}/home" class="active">HOME</a></li>
        <li><a href="${pageContext.request.contextPath}/products">SHOP</a></li>
        <li><a href="${pageContext.request.contextPath}/cart">CART</a></li>
        <li><a href="${pageContext.request.contextPath}/contact">CONTACT</a></li>
        <li><a href="${pageContext.request.contextPath}/about">ABOUT</a></li>
        <c:choose>
            <c:when test="${not empty sessionScope.email}">
                <li><a href="${pageContext.request.contextPath}/profile">PROFILE</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">LOGOUT</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/login">LOGIN</a></li>
                <li><a href="${pageContext.request.contextPath}/register">SIGN UP</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</header>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const mobileMenuBtn = document.getElementById('mobileMenuBtn');
    const navbar = document.getElementById('navbar');
    
    mobileMenuBtn.addEventListener('click', function() {
        navbar.classList.toggle('active');
        this.innerHTML = navbar.classList.contains('active') 
            ? '<i class="fas fa-times"></i>' 
            : '<i class="fas fa-bars"></i>';
    });
    
    // Close menu when clicking on a link
    document.querySelectorAll('.navbar a').forEach(link => {
        link.addEventListener('click', function() {
            if (window.innerWidth <= 992) {
                navbar.classList.remove('active');
                mobileMenuBtn.innerHTML = '<i class="fas fa-bars"></i>';
            }
        });
    });
    
    // Close menu when window is resized above breakpoint
    window.addEventListener('resize', function() {
        if (window.innerWidth > 992) {
            navbar.classList.remove('active');
            mobileMenuBtn.innerHTML = '<i class="fas fa-bars"></i>';
        }
    });
});
</script>

</body>
</html>