<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home | BestCure</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat&family=Roboto+Condensed&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
</head>
<body>

<header id="header">
    <a href="${pageContext.request.contextPath}/home">
        <img src="${pageContext.request.contextPath}/images/logo.png"
             class="first" alt="BestCure Logo">
    </a>
    <ul class="navbar">
        <li><a href="${pageContext.request.contextPath}/home" class="active">HOME</a></li>
        <li><a href="${pageContext.request.contextPath}/products">SHOP</a></li>
        <li><a href="${pageContext.request.contextPath}/contact">CONTACT</a></li>
        <li><a href="${pageContext.request.contextPath}/about">ABOUT</a></li>
        <c:choose>
            <c:when test="${not empty sessionScope.email}">
                <li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">LOGOUT</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/login">LOGIN</a></li>
                <li><a href="${pageContext.request.contextPath}/register">SIGN UP</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</header>

</body>
</html>
