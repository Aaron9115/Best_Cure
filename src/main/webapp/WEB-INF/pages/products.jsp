<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Products</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/products.css">
</head>

<body style="padding-top: 80px;"> <!-- Added padding to prevent header overlap -->

<!-- Banner -->
<section class="home" id="home">
    <div class="image">
        <div class="slider" id="ig">
            <img src="${pageContext.request.contextPath}/resources/slider.png" style="border-radius: 20px;">
        </div>
        <div class="down">
            <p>Scroll To <span id="one">Shop</span></p>
            <div class="mouse">
                <div class="scroll"></div>
            </div>
        </div>
    </div>
</section>

<!-- Category Section -->
<section class="cat" id="cat">
    <h1>Shop By <span id="h" style="color: #FFAE42;">Category</span></h1>
    <div class="box-container">
        <div class="box">
            <img src="${pageContext.request.contextPath}/resources/baby.png" />
            <p>Baby Products</p>
            <form action="products" method="get">
                <input type="hidden" name="category" value="baby">
                <button type="submit" class="pbtn">Buy Now</button>
            </form>
        </div>
        <div class="box">
            <img src="${pageContext.request.contextPath}/resources/skin.png" />
            <p>Skin Care</p>
            <form action="products" method="get">
                <input type="hidden" name="category" value="skin">
                <button type="submit" class="pbtn">Buy Now</button>
            </form>
        </div>
        <div class="box">
            <img src="${pageContext.request.contextPath}/resources/equipment.png" />
            <p>Equipment</p>
            <form action="products" method="get">
                <input type="hidden" name="category" value="equipment">
                <button type="submit" class="pbtn">Buy Now</button>
            </form>
        </div>
        <div class="box">
            <img src="${pageContext.request.contextPath}/resources/med.png" />
            <p>Medicines</p>
            <form action="products" method="get">
                <input type="hidden" name="category" value="medicine">
                <button type="submit" class="pbtn">Buy Now</button>
            </form>
        </div>
    </div>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>